package by.epamtc.melnikov.onlineshop.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.command.CommandHolder;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * Servlet Filter implementation class UserAutoLogInByTokenFilter.
 * Required to implement the remember me function.
 * 
 * @author nearbyall
 * 
 */
public class UserAutoLogInByTokenFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger(UserAutoLogInByTokenFilter.class);

	UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String userRole = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE);
		if(userRole.equals(UserType.GUEST.getName())) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie: cookies) {
					if (cookie.getName().equals(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN)) {
						String token = cookie.getValue();
						try {
							User user = userService.logInByToken(token);
							request.getSession().setAttribute(JSPAttributeStorage.USER_EMAIL, user.getEmail());
							request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getRole().getName());
							request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
							request.getSession().setAttribute(JSPAttributeStorage.USER_DATA, user);
							response.sendRedirect(getRedirectURL(request, CommandHolder.OPEN_MAIN_PAGE.getCommandName()));
							return;
						} catch (ServiceException e) {
							logger.info(String.format("RememberToken is invalid, %s", token), e);
							cookie.setMaxAge(0);
							cookie.setPath(request.getContextPath());
							cookie.setValue("");
							response.addCookie(cookie);
						}
					}
				}
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String getRedirectURL(HttpServletRequest request, String commandName) {
		return request.getContextPath() + "/Controller" + "?" + JSPAttributeStorage.COMMAND + "=" + commandName;
	}
	
}
