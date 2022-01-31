package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
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
	 * Gets a {@link CartItem} as a parameter, validates it and
	 * adds to data source via DAO layer. if there is no this product in the cart yet, then add it.
	 * 
	 * @param cartItem {@link CartItem} which should be added
	 * @return {@link CartItem} which has been added
	 * @throws ServiceException if validation by {@link CartValidator} has not been passed,
	 * or DAO layer throw their {@link DAOException}
	 */
	CartItem addCartItem(CartItem cartItem) throws ServiceException;
	
	/**
	 * Gets a {@link CartItem} as a parameter, validates it and
	 * removes from data source via DAO layer. If the number of items in the cart is not more than 1, then delete it.
	 * 
	 * @param cartItem {@link CartItem} which should be removed
	 * @return {@link CartItem} which has been removed
	 * @throws ServiceException if validation by {@link CartValidator} has not been passed,
	 * or DAO layer throw their {@link DAOException}
	 */
	CartItem deleteCartItem(CartItem cartItem) throws ServiceException;
	
	/**
	 * Finds all {@link CartItem}s by userId via DAO layer
	 * 
	 * @param userId {@link CartItem}'s userId
	 * @return {@link List} of {@link CartItem}s
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<CartItem> findAllCartItemsByUserId(int userId) throws ServiceException;
	
}
