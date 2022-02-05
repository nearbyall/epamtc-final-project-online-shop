package by.epamtc.melnikov.onlineshop.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * Servlet Filter implementation class UserBanFilter.
 * This is necessary in order to check if the user is banned on every user action.
 * 
 * @author nearbyall
 *
 */
public class UserBanFilter implements Filter {

	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute(JSPAttributeStorage.USER_EMAIL);
		if (email != null) {
			UserService userService = ServiceProvider.getInstance().getUserService();
			User user;
			try {
				user = userService.findUserByEmail(email);
				if (user.isBanned()) {
					session.invalidate();
				}
			} catch (ServiceException e) {
				logger.warn(e);
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

}
