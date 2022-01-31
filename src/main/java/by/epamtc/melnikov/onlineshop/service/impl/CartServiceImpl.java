package by.epamtc.melnikov.onlineshop.service.impl;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.dao.CartDAO;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.CartService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * {@link CartService} interface implementation.
 * 
 * @author nearbyall
 *
 */
public class CartServiceImpl implements CartService {

	private final CartDAO cartDAO;
	
	public CartServiceImpl() {
		cartDAO = DAOProvider.getInstance().getCartDAO();
	}
	
	@Override
	public CartItem addCartItem(CartItem cartItem) throws ServiceException {
		
		try {
			int count = cartDAO.findCartItemCount(cartItem.getUserId(), cartItem.getProduct().getId());
			System.out.println(count);
			if (count < 1) {
				cartDAO.addCartItem(cartItem);
			} else {
				cartItem.setCount(++count);
				cartDAO.updatedCartItemInfo(cartItem);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return cartItem;
		
	}
	
	@Override
	public CartItem deleteCartItem(CartItem cartItem) throws ServiceException {
		
		try {
			int count = cartDAO.findCartItemCount(cartItem.getUserId(), cartItem.getProduct().getId());
			System.out.println(count);
			if (count == 1) {
				cartDAO.deleteCartItem(cartItem);
			} else {
				cartItem.setCount(--count);
				cartDAO.updatedCartItemInfo(cartItem);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return cartItem;
		
	}

	@Override
	public List<CartItem> findAllCartItemsByUserId(int userId) throws ServiceException {
		
		List<CartItem> cartItems;
		
		try {
			cartItems = cartDAO.findAllCartItemsByUserId(userId);
			for (CartItem cartItem : cartItems) {
				cartItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getCount());
			}
			return cartItems;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	
	}
	
}
