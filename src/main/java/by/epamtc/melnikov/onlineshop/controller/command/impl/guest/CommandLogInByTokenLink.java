package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
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
 * for log in by token process.
 * 
 * @author nearbyall
 *
 */
public class CommandLogInByTokenLink implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
        
		String token = request.getParameter(AttributeNameStorage.COOKIE_REMEMBER_USER_TOKEN);
        
		try {
			User user = userService.logInByToken(token);
			request.getSession().setAttribute(AttributeNameStorage.USER_EMAIL, user.getEmail());
			request.getSession().setAttribute(AttributeNameStorage.USER_ROLE, user.getRole().getName());
			request.getSession().setAttribute(AttributeNameStorage.USER_ID, user.getId());
			request.getSession().setAttribute(AttributeNameStorage.USER_DATA, user);
			userService.deleteUserRememberToken(user.getId());
			String redirectURL = getRedirectURL(request, CommandHolder.OPEN_MAIN_PAGE.getCommandName());
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.HOME);
			result.setDirection(Direction.FORWARD);
		}
        
		return result;
        
	}

}
