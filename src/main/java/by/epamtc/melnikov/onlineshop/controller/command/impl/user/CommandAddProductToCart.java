package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.CartService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for product addition to cart process.
 * 
 * @author nearbyall
 *
 */
public class CommandAddProductToCart implements Command {

	private static final CartService cartService = ServiceProvider.getInstance().getCartService();
	
	private static final int DEFAULT_COUNT = 1;
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandResult result = new CommandResult();
		
		try {
			cartService.addCartItem(constructCartItemForAddition(request));
			String redirectCommand = request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.PRODUCT);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}
	
	private CartItem constructCartItemForAddition(HttpServletRequest request) {

		CartItem cartItem = new CartItem();
		Product product = new Product();
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());

		product.setId(Integer.parseInt(request.getParameter(JSPAttributeStorage.PRODUCT_ID)));
		cartItem.setCreatedAt(currentTimestamp);
		cartItem.setUpdatedAt(currentTimestamp);
		cartItem.setProduct(product);
		cartItem.setUserId(Integer.parseInt(request.getParameter(JSPAttributeStorage.USER_ID)));
		cartItem.setCount(DEFAULT_COUNT);
		
		return cartItem;
		
	}

}
