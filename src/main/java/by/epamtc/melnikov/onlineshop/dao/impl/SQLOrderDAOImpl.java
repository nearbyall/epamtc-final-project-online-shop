package by.epamtc.melnikov.onlineshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		ResultSet resultSet;
		
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
	
	@Override
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

}
