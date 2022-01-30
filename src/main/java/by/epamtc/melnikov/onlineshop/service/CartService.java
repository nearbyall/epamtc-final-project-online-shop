package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The interface describes the opportunity of processing {@link CartItem} data
 * received from the client and then sending it to the DAO layer to process
 * the incoming request.
 * 
 * @author nearbyall
 *
 */
public interface CartService {

	/**
	 * 
	 * @param cartItem
	 * @return
	 * @throws ServiceException
	 */
	CartItem addCartItem(CartItem cartItem) throws ServiceException;
	
	/**
	 * 
	 * @param cartItem
	 * @return
	 * @throws ServiceException
	 */
	CartItem deleteCartItem(CartItem cartItem) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	List<CartItem> findAllCartItemsByUserId(int userId) throws ServiceException;
	
}
