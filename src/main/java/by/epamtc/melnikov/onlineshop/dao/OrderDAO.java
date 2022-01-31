package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Order;
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
	 * Saves the <tt>order</tt> into data source. Throws DAOException
	 * if an error occurs while writing a <tt>order</tt>.
	 * 
	 * @param order the {@link Order} that should added to data source
	 * @return an {@link Order} which has been added
	 * @throws DAOException if an error occurs while writing a <tt>order</tt>
	 */
	Order addOrder(Order order) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link Order}s into data source.
	 * If no such orders contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link Order}s
	 * @throws DAOException if an error occurs while getting a <tt>order</tt>
	 */
	List<Order> findAllOrders() throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link Order}s into data source by userId
	 * If no such orders contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link Order}s
	 * @param userId {@link Order}'s userId
	 * @throws DAOException if an error occurs while getting a <tt>order</tt>
	 */
	List<Order> findAllOrdersByUserId(int userId) throws DAOException;
	
	/**
	 * Updates status of {@link Order} in a data source.
	 * Throws DAOException if an error occurs while writing a <tt>order</tt>.
	 * 
	 * @param orderId the {@link Order}'s id that ban status is updating
	 * @param statusId the {@link Order}'s statusId that to be updated
	 * @return new statusId
	 * @throws DAOException if an error occurs while writing a <tt>order</tt>
	 */
	int updateOrderStatusByOrderId(int orderId, int statusId) throws DAOException;
	
}