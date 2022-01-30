package by.epamtc.melnikov.onlineshop.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.bean.Order;
import by.epamtc.melnikov.onlineshop.bean.OrderItem;
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.bean.type.OrderType;
import by.epamtc.melnikov.onlineshop.dao.CartDAO;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.OrderDAO;
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

	private static final Logger logger = LogManager.getLogger();
	
	private final OrderDAO orderDAO;
	private final CartDAO cartDAO;
	
	public OrderServiceImpl() {
		orderDAO = DAOProvider.getInstance().getOrderDAO();
		cartDAO = DAOProvider.getInstance().getCartDAO();
	}
	
	@Override
	public Order addOrder(int userId) throws ServiceException {

		//TODO userId
		
		Order order = new Order();

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
		
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
		
		order.setId(orderId);
		order.setUser(new UserBuilder().withId(userId).build());
		order.setStatus(OrderType.PAID);
		order.setOrderItems(orderItems);
		order.setCreatedAt(currentTimestamp);
		order.setUpdatedAt(currentTimestamp);
		
		try {
			orderDAO.addOrder(order);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return order;
		
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

}
