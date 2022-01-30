package by.epamtc.melnikov.onlineshop.dao;

import java.sql.Connection;

import by.epamtc.melnikov.onlineshop.bean.Order;
import by.epamtc.melnikov.onlineshop.bean.OrderItem;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store
 * and restore {@link Order} bean.
 * 
 * @author nearbyall
 *
 */
public interface OrderDAO {

	/**
	 * 
	 * @param order
	 * @return
	 * @throws DAOException
	 */
	Order addOrder(Order order) throws DAOException;
	
	/**
	 * 
	 * @param orderItem
	 * @return
	 * @throws DAOException
	 */
	OrderItem addOrderItem(OrderItem orderItem, Connection connection) throws DAOException;

	/**
	 * 
	 * @param userId
	 * @param connection
	 * @return
	 * @throws DAOException
	 */
	int deleteCartItemsByUserId(int userId, Connection connection) throws DAOException;
	
}
