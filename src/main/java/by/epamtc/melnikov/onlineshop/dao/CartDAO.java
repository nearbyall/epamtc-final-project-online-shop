package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store
 * and restore {@link CartItem} bean.
 * 
 * @author nearbyall
 *
 */
public interface CartDAO {

	/**
	 * 
	 * 
	 * @param cartItem
	 * @return
	 * @throws DAOException
	 */
	CartItem addCartItem(CartItem cartItem) throws DAOException;
	

	/**
	 * 
	 * @param cartItem
	 * @return
	 * @throws DAOException
	 */
	CartItem deleteCartItem(CartItem cartItem) throws DAOException;
	
	/**
	 * 
	 * @param cartItem
	 * @return
	 * @throws DAOException
	 */
	CartItem updatedCartItemInfo(CartItem cartItem) throws DAOException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	List<CartItem> findAllCartItemsByUserId(int userId) throws DAOException;
	
	/**
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 * @throws DAOException
	 */
	int findCartItemCount(int userId, int productId) throws DAOException;
	
}
