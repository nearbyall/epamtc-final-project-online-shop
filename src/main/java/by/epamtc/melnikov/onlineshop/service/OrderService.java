package by.epamtc.melnikov.onlineshop.service;

import by.epamtc.melnikov.onlineshop.bean.Order;
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
	 * 
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	Order addOrder(int userId) throws ServiceException;
	
}
