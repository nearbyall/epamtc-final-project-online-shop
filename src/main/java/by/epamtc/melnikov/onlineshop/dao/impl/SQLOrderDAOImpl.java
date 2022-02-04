package by.epamtc.melnikov.onlineshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.Order;
import by.epamtc.melnikov.onlineshop.bean.OrderItem;
import by.epamtc.melnikov.onlineshop.dao.OrderDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLBaseDAO;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLQueriesStorage;

/**
 * SQL {@link OrderDAO} interface implementation
 * 
 * @author nearbyall
 *
 */
public class SQLOrderDAOImpl extends SQLBaseDAO implements OrderDAO {

	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public Order addOrder(Order order) throws DAOException {
		
		Connection connection;
		
		try {
			connection = pool.getConnection();
		} catch (ConnectionPoolException e) {
			logger.warn(String.format("Order: %s addition error. get connection exception", order), e);
			throw new DAOException("query.order.addition.error", e);			
		}
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_ORDER)) {
			 connection.setAutoCommit(false);
			preparedStatement.setInt(1, order.getId());
			preparedStatement.setInt(2, order.getUser().getId());
			preparedStatement.setInt(3, order.getStatus().getId());
			preparedStatement.setTimestamp(4, order.getCreatedAt());
			preparedStatement.setTimestamp(5, order.getUpdatedAt());
			preparedStatement.executeUpdate();
			for (OrderItem orderItem : order.getOrderItems()) {
				addOrderItem(orderItem, connection);
			}
			updateProductsCount(order.getOrderItems(), connection);
			updateUserBalance(order.getUser().getBalance(), order.getUser().getId(), connection);
			deleteCartItemsByUserId(order.getUser().getId(), connection);
			connection.commit();
		} catch (SQLException e) {
			connectionsRollback(connection);
			logger.warn(String.format("Order: %s addition error", order), e);
			throw new DAOException("query.order.addition.error", e);
		} finally {
			connectionSetAutoCommit(connection, true);
			closeConnection(connection);
		}
		
		return order;
		
	}
	
	@Override
	public List<Order> findAllOrders() throws DAOException {
		
		Connection connection;
		ResultSet resultSet;
		List<Order> orders = Collections.emptyList();
		
		try {
			connection = pool.getConnection();
		} catch (ConnectionPoolException e) {
			logger.warn("Orders list finding error", e);
			throw new DAOException("query.orders.finding.error", e);			
		}
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_ORDERS)) {
			 connection.setAutoCommit(false);
			 
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Orders is empty");
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				orders = new ArrayList<>(listSize);
				while (resultSet.next()) {
					orders.add(constructOrderByResultSet(resultSet));
				}
			}
			
			for (Order order : orders) {
				order.setOrderItems(findAllOrderItemsByOrderId(order.getId(), connection));
			}
			
			
			System.out.println(orders);
			
			connection.commit();
		} catch (SQLException e) {
			connectionsRollback(connection);
			logger.warn("Orders finding error", e);
			throw new DAOException("query.orders.finding.error", e);
		} finally {
			connectionSetAutoCommit(connection, true);
			closeConnection(connection);
		}
		
		return orders;
		
	}
	

	@Override
	public List<Order> findAllOrdersByUserId(int userId) throws DAOException {

		Connection connection;
		ResultSet resultSet;
		List<Order> orders = Collections.emptyList();
		
		try {
			connection = pool.getConnection();
		} catch (ConnectionPoolException e) {
			logger.warn("Orders list finding error", e);
			throw new DAOException("query.orders.finding.error", e);			
		}
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_ORDERS_BY_USER_ID)) {
			 connection.setAutoCommit(false);
			preparedStatement.setInt(1, userId); 
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Orders is empty");
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				orders = new ArrayList<>(listSize);
				while (resultSet.next()) {
					orders.add(constructOrderByResultSet(resultSet));
				}
			}
			
			for (Order order : orders) {
				order.setOrderItems(findAllOrderItemsByOrderId(order.getId(), connection));
			}
			
			
			System.out.println(orders);
			
			connection.commit();
		} catch (SQLException e) {
			connectionsRollback(connection);
			logger.warn("Orders finding error", e);
			throw new DAOException("query.orders.finding.error", e);
		} finally {
			connectionSetAutoCommit(connection, true);
			closeConnection(connection);
		}
		
		return orders;
		
	}
	

	
	@Override
	public Order updateOrderStatus(Order order) throws DAOException {
		try (Connection connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_ORDER_STATUS)) {
			preparedStatement.setInt(1, order.getStatus().getId());
			preparedStatement.setTimestamp(2, order.getUpdatedAt());
			preparedStatement.setInt(3, order.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException | ConnectionPoolException e) {
			logger.warn(String.format("Order status update error" + order), e);
			throw new DAOException("service.commonError", e);
		}
			
		return order;
	}
	
	/**
	 * Removes all {@link CartItem}s in data source by userId.
	 * The method does not have its own connection to data source, 
	 * so need to put it as a parameter.
	 * Throws DAOException if an error occurs while removing <tt>cartItem</tt>.
	 * 
	 * @param userId the {@link CartItem}'s userId that should be removed
	 * @param connection {@link Connection} to contact with data source
	 * @return userId whose cart has been emptied
	 * @throws DAOException if an error occurs while removing <tt>cartItem</tt>.
	 */
	public int deleteCartItemsByUserId(int userId, Connection connection) throws DAOException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.CLEAR_CART_ITEMS_BY_USER_ID)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn(String.format("CartItems removing by userId %d error", userId), e);
			throw new DAOException("query.cartItem.removing.commonError", e);
		}
				
		return userId;
		
	}

	/**
	 * Finds count of {@link OrderItem} in data source by orderId.
	 * The method does not have its own connection to data source, 
	 * so need to put it as a parameter.
	 * Throws DAOException if an error occurs while getting count of <tt>orderItem</tt>.
	 * 
	 * @param orderId the {@link Order}'s id which includes order items
	 * @param connection {@link Connection} to contact with data source
	 * @return count of {@link OrderItem}s in data source by orderId
	 * @throws DAOException if an error occurs while getting count of <tt>orderItem</tt>.
	 */
	public int findOrderItemsCountByOrderId(int orderId, Connection connection) throws DAOException {
		
		ResultSet resultSet = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ORDER_ITEMS_COUNT_BY_ORDER_ID)) {
			preparedStatement.setInt(1, orderId);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			logger.warn("Order items count finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
	}
	
	/**
	 * Saves the <tt>orderItem</tt> into data source. 
	 * The method does not have its own connection to data source, 
	 * so need to put it as a parameter.
	 * Throws DAOException if an error occurs while writing a <tt>orderItem</tt>.
	 * 
	 * @param orderItem the {@link OrderItem} that should added to data source
	 * @param connection {@link Connection} to contact with data source
	 * @return {@link OrderItem} which has been added
	 * @throws DAOException if an error occurs while writing a <tt>orderItem</tt>
	 */
	public OrderItem addOrderItem(OrderItem orderItem, Connection connection) throws DAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_ORDER_ITEM)) {
			preparedStatement.setInt(1, orderItem.getProduct().getId());
			preparedStatement.setInt(2, orderItem.getOrderId());
			preparedStatement.setInt(3, orderItem.getCount());
			preparedStatement.setDouble(4, orderItem.getTotalPrice());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Order item addition error " + orderItem, e);
			throw new DAOException("query.orderItem.addition.error", e);
		}
		return orderItem;
	}
	
	/**
	 * Retrieves and returns {@link List} of {@link OrderItem}s into data source.
	 * The method does not have its own connection to data source, 
	 * so need to put it as a parameter. 
	 * If no such order items contains into data source returns empty {@link List} collection.
	 * 
	 * @param orderId the {@link OrderItem}'s orderId
	 * @param connection {@link Connection} to contact with data source
	 * @return {@link List} of {@link OrderItem}s
	 * @throws DAOException if an error occurs while getting a <tt>orderItem</tt>
	 */
	public List<OrderItem> findAllOrderItemsByOrderId(int orderId, Connection connection) throws DAOException {
		
		ResultSet resultSet = null;
		List<OrderItem> orderItems = Collections.emptyList();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_ORDER_ITEMS_BY_ORDER_ID)) {
			preparedStatement.setInt(1, orderId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Order items is empty");
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				orderItems = new ArrayList<>(listSize);
				while (resultSet.next()) {
					orderItems.add(constructOrderItemsByResultSet(resultSet));
				}
			}
			
		} catch (SQLException e) {
			logger.warn("Order items List finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
		return orderItems;
		
	}
	
	/**
	 * Updates {@link User}'s balance by id in data source.
	 * The method does not have its own connection to data source, 
	 * so need to put it as a parameter. 
	 * Throws DAOException if an error occurs while writing <tt>newBalance</tt>
	 * 
	 * @param newBalance {@link User}'s balance which should be updated
	 * @param userId {@link User}'s id that balance should be updated
	 * @param connection {@link Connection} to contact with data source
	 * @return new {@link User}'s balance
	 * @throws DAOException if an error occurs while writing <tt>newBalance</tt>
	 */
	public double updateUserBalance(double newBalance, int userId, Connection connection) throws DAOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_BALANCE)) {
			preparedStatement.setDouble(1, newBalance);
			preparedStatement.setInt(2, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Balance updating error " + newBalance, e);
			throw new DAOException("query.balance.updating.error", e);
		}
		return newBalance;
	}
	
	/**
	 * Updates {@link Product}'s count in data source by id, which includes <tt>orderItems</tt>.
	 * The method does not have its own connection to data source, 
	 * so need to put it as a parameter. 
	 * Throws DAOException if an error occurs while writing <tt>product</tt>
	 * 
	 * @param orderItems {@link List} of {@link OrderItem}s
	 * @param connection {@link Connection} to contact with data source
	 */
	public void updateProductsCount(List<OrderItem> orderItems, Connection connection) throws DAOException {
		for (OrderItem item : orderItems) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_PRODUCT_COUNT_BY_PRODUCT_ID)) {
				preparedStatement.setInt(1, item.getProduct().getCount() - item.getCount());
				preparedStatement.setTimestamp(2, item.getUpdatedAt());
				preparedStatement.setInt(3, item.getProduct().getId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				logger.warn("Product count updating error ", e);
				throw new DAOException("query.product.updating.error", e);
			}
		}
	}

}