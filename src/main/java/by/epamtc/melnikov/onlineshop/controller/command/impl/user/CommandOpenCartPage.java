package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.CartService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#CART} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenCartPage implements Command {

	private static final CartService cartService = ServiceProvider.getInstance().getCartService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		int userId = (int) request.getSession().getAttribute(AttributeNameStorage.USER_ID);
		try {
			List<CartItem> cartItems = cartService.findAllCartItemsByUserId(userId);
			request.setAttribute(AttributeNameStorage.CART_ITEMS_LIST, cartItems);
			result.setPage(PageStorage.CART);
			result.setDirection(Direction.FORWARD);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.HOME);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}

}
