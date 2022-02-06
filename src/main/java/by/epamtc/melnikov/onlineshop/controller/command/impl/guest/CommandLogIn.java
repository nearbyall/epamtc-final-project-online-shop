package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
 * for log in process.
 * 
 * @author nearbyall
 *
 */
public class CommandLogIn implements Command {

	private static final int COOKIE_MAX_AGE_21_DAY = 60*60*24*21;
	
	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		String email = request.getParameter(AttributeNameStorage.USER_EMAIL);
		String password = request.getParameter(AttributeNameStorage.USER_PASSWORD);
		String rememberToken = request.getParameter(AttributeNameStorage.GENERATE_REMEMBER_USER_TOKEN);
		
		try {
			
			User user = userService.logInByPassword(email, password);
			
			request.getSession().setAttribute(AttributeNameStorage.USER_EMAIL, email);
			request.getSession().setAttribute(AttributeNameStorage.USER_ROLE, user.getRole().getName());
			request.getSession().setAttribute(AttributeNameStorage.USER_ID, user.getId());
			request.getSession().setAttribute(AttributeNameStorage.USER_DATA, user);
			
			if (rememberToken != null) {
				Cookie rememberTokenCookie = new Cookie(
						AttributeNameStorage.COOKIE_REMEMBER_USER_TOKEN, userService.findUpdatedUserRememberToken(user.getId())
				);
				rememberTokenCookie.setPath(request.getContextPath());
				rememberTokenCookie.setMaxAge(COOKIE_MAX_AGE_21_DAY);
				response.addCookie(rememberTokenCookie);
			}
			
			String redirectCommand = request.getParameter(AttributeNameStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
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
