package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandHolder;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for send log in token process.
 * 
 * @author nearbyall
 *
 */
public class CommandSendForgetPasswordData implements Command {
	
	private static final UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		CommandResult result = new CommandResult();
        
		String email = request.getParameter(JSPAttributeStorage.USER_EMAIL);
        
		try {
			userService.sendLogInTokenIfForgetPassword(email, request.getRequestURL().toString());
			String redirectURL = getRedirectURL(request, CommandHolder.OPEN_MAIN_PAGE.getCommandName());
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.LOG_IN);
			result.setDirection(Direction.FORWARD);
		}
        
		return result;
        
	}
}
