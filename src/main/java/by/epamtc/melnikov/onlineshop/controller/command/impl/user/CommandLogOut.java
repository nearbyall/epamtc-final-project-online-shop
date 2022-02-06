package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandHolder;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for log out process.
 * 
 * @author nearbyall
 *
 */
public class CommandLogOut implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		int userId = (int) request.getSession().getAttribute(AttributeNameStorage.USER_ID);
		request.getSession().invalidate();
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie: cookies) {
				if (cookie.getName().equals(AttributeNameStorage.COOKIE_REMEMBER_USER_TOKEN)) {
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath());
					cookie.setValue("");
					response.addCookie(cookie);
					try {
						userService.deleteUserRememberToken(userId);
					} catch (ServiceException e) {
						logger.warn(String.format("RememberToken is not deleted for user %d ", userId), e);
					}
				}
			}
		}
		
		result.setPage(getRedirectURL(request, CommandHolder.OPEN_MAIN_PAGE.getCommandName()));
		result.setDirection(Direction.REDIRECT);
		
		return result;
		
	}

}