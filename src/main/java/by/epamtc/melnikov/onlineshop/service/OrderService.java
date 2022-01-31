package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Order;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The interface describes the opportunity of processing {@link Order} data
 * received from the client and then sending it to the DAO layer to process
 * the incoming request.
 * 
 * @author nearbyall
 *
 */
public interface OrderService {

	/**
	 * Gets a {@link Order}'s userId as a parameter and
	 * adds to data source new {@link Order} via DAO layer.
	 * 
	 * @param userId {@link Order}'s userId
	 * @return {@link Order} which has been added
	 * @throws ServiceException if validation by {@link ProductValidator} has not been passed,
	 * or DAO layer throw their {@link DAOException}
	 */
	Order addOrder(int userId) throws ServiceException;
	
	/**
	 * Finds all {@link Orders} via DAO layer.
	 * 
	 * @return {@link List} of {@link Order}s
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<Order> findAllOrders() throws ServiceException;
	
	/**
	 * Finds all {@link Orders} via DAO layer by userId.
	 * 
	 * @param userId {@link Order}'s userId
	 * @return {@link List} of {@link Order}s
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<Order> findAllOrdersByUserId(int userId) throws ServiceException;
	
	/**
	 * Updates {@link Order}'s status by orderId and statusId via DAO layer.
	 * 
	 * @return statusId the {@link Order}'s statusId that to be updated
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	int updateOrderStatusByOrderId(int orderId, int statusId) throws ServiceException;
	
}
