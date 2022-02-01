package by.epamtc.melnikov.onlineshop.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.bean.Order;
import by.epamtc.melnikov.onlineshop.bean.OrderItem;
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.bean.type.OrderType;
import by.epamtc.melnikov.onlineshop.dao.CartDAO;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.OrderDAO;
import by.epamtc.melnikov.onlineshop.dao.UserDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.OrderService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * {@link OrderService} interface implementation.
 * 
 * @author nearbyall
 *
 */
public class OrderServiceImpl implements OrderService {
	
	private final OrderDAO orderDAO;
	private final CartDAO cartDAO;
	private final UserDAO userDAO;
	
	public OrderServiceImpl() {
		orderDAO = DAOProvider.getInstance().getOrderDAO();
		cartDAO = DAOProvider.getInstance().getCartDAO();
		userDAO = DAOProvider.getInstance().getUserDAO();
	}
	
	@Override
	public Order addOrder(int userId) throws ServiceException {

		//Order items constructing
		List<CartItem> cartItems;
		try {
			cartItems = cartDAO.findAllCartItemsByUserId(userId);
			for (CartItem cartItem : cartItems) {
				cartItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getCount());
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		int orderId = (int) (Math.random() * Integer.MAX_VALUE);
		List<OrderItem> orderItems = constructOrderItemsByCartItems(cartItems, orderId);
		
		//Order constructing
		double totalPrice = calculateTotalPrice(orderItems);
		double userBalance;
		try {
			userBalance = userDAO.findUserBalance(userId);
		} catch (DAOException e1) {
			throw new ServiceException("service.commonError");
		}
		if (checkOpportunityForBuy(userId, totalPrice)) {
			java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
			Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
			Order order = new Order();
			order.setId(orderId);
			order.setUser(new UserBuilder().withId(userId).withBalance(userBalance - totalPrice).build());
			order.setStatus(OrderType.NOTACCEPTED);
			order.setOrderItems(orderItems);
			order.setCreatedAt(currentTimestamp);
			order.setUpdatedAt(currentTimestamp);
			order.setTotalPrice(totalPrice);
			try {
				orderDAO.addOrder(order);
			} catch (DAOException e) {
				throw new ServiceException(e.getMessage(), e);
			}
			return order;
		} else {
			throw new ServiceException("query.orders.addOrder.notEnoughMoney");
		}
		
	}
	
	private List<OrderItem> constructOrderItemsByCartItems(List<CartItem> cartItems, int orderId) {
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for (CartItem cartItem : cartItems) {
			
			OrderItem orderItem = new OrderItem();
			
			orderItem.setOrderId(orderId);
			orderItem.setCount(cartItem.getCount());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			orderItem.setCreatedAt(cartItem.getCreatedAt());
			orderItem.setUpdatedAt(cartItem.getUpdatedAt());
			
			orderItems.add(orderItem);
			
		}
		
		return orderItems;
		
	}
	
	@Override
	public List<Order> findAllOrders() throws ServiceException {
		
		List<Order> orders = new ArrayList<>();
		
		try {
			orders = orderDAO.findAllOrders();
			if (orders.isEmpty()) {
				throw new ServiceException("query.orders.getOrders.ordersNotFound");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return orders;
		
	}
	
	@Override
	public List<Order> findAllOrdersByUserId(int userId) throws ServiceException {
		
		List<Order> orders = new ArrayList<>();
		
		try {
			orders = orderDAO.findAllOrdersByUserId(userId);
			if (orders.isEmpty()) {
				throw new ServiceException("query.ordets.getOrders.ordersNotFound");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return orders;
		
	}

	@Override
	public int updateOrderStatus(int orderId, int statusId) throws ServiceException {
		
		Order order = new Order();
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
		order.setId(orderId);
		order.setStatus(OrderType.getTypeById(statusId));
		order.setUpdatedAt(currentTimestamp);
		
		try {
			orderDAO.updateOrderStatus(order);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return statusId;
		
	}
	
	/**
	 * 
	 * @param orderItems
	 * @return
	 */
	private double calculateTotalPrice(List<OrderItem> orderItems) {
		
		double price = 0;
		
		for(OrderItem orderItem : orderItems) {
			price += orderItem.getTotalPrice();
		}
		
		return price;
		
	}
	
	/**
	 * 
	 * @param userId
	 * @param totalPrice
	 * @return
	 * @throws ServiceException
	 */
	private boolean checkOpportunityForBuy(int userId, double totalPrice) throws ServiceException {
		
		double userBalance;
		try {
			userBalance = userDAO.findUserBalance(userId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		if (userBalance > totalPrice) {
			return true;
		} else {
			return false;
		}
		
	}

}