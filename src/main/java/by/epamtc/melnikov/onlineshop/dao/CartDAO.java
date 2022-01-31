package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store
 * and restore {@link Cart} bean.
 * 
 * @author nearbyall
 *
 */
public interface CartDAO {

	/**
	 * Saves the <tt>cartItem</tt> into data source. Throws DAOException
	 * if an error occurs while writing a <tt>cartItem</tt>.
	 * 
	 * @param cartItem the {@link CartItem} that should added to data source
	 * @return an {@link CartItem} which has been added
	 * @throws DAOException if an error occurs while writing a <tt>cartItem</tt>
	 */
	CartItem addCartItem(CartItem cartItem) throws DAOException;
	

	/**
	 * Removes the <tt>cartItem</tt> from data source. Throws DAOException
	 * if an error occurs while removing a <tt>cartItem</tt>.
	 * 
	 * @param cartItem the {@link CartItem} that should removed from data source
	 * @return an {@link CartItem} which has been removed
	 * @throws DAOException if an error occurs while removing a <tt>cartItem</tt>
	 */
	CartItem deleteCartItem(CartItem cartItem) throws DAOException;
	
	/**
	 * Updates the <tt>cartItem</tt> info in data source. Throws DAOException
	 * if an error occurs while writing a <tt>cartItem</tt>.
	 * 
	 * @param cartItem the {@link CartItem} that should updated in data source
	 * @return an {@link CartItem} which has been updated
	 * @throws DAOException if an error occurs while updating a <tt>cartItem</tt>
	 */
	CartItem updatedCartItemInfo(CartItem cartItem) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link CartItem}s into data source by userId
	 * If no such cart items contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link CartItem}s
	 * @param userId {@link CartItem}'s userId
	 * @throws DAOException if an error occurs while getting a <tt>cartItem</tt>
	 */
	List<CartItem> findAllCartItemsByUserId(int userId) throws DAOException;
	
	/**
	 * Retrieves and returns total count of {@link CartItem}s into data source by userId and productId.
	 * 
	 * @param userId the {@link CartItem#}' userId
	 * @param productId the {@link CartItem#}' productId
	 * @return count of {@link CartItem}s into data source
	 * @throws DAOException if an error occurs while getting a <tt>cartItem</tt> count
	 */
	int findCartItemCount(int userId, int productId) throws DAOException;
	
}
