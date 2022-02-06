package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for top up balance process.
 * 
 * @author nearbyall
 *
 */
public class CommandTopUpBalance implements Command {

	public static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		User user = (User) request.getSession().getAttribute(AttributeNameStorage.USER_DATA);
		String cardNumber = request.getParameter(AttributeNameStorage.CARD_NUMBER);
		String cardDate = request.getParameter(AttributeNameStorage.CARD_DATE);
		String cardCVV = request.getParameter(AttributeNameStorage.CARD_CVV);
		String cardSumm = request.getParameter(AttributeNameStorage.CARD_SUMM);
		
		/*
		 * TODO Check card validity
		 */
		
		user.setBalance(Double.parseDouble(cardSumm) + user.getBalance());
		
		try {
			user = userService.updateUserBalance(user);
			request.getSession().setAttribute(AttributeNameStorage.USER_DATA, user);
			String redirectCommand = request.getParameter(AttributeNameStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.TOP_UP_BALANCE);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
	}

}
