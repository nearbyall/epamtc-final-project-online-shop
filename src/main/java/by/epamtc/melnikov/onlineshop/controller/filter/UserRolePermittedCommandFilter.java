package by.epamtc.melnikov.onlineshop.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.CommandHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Servlet Filter implementation class UserRolePermittedCommandFilter.
 * Needed to check if the user has the right to execute the command received in the request.
 * 
 * @author nearbyall
 */
public class UserRolePermittedCommandFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(UserRolePermittedCommandFilter.class);
	
	private static final Set<CommandHolder> guestCommands = EnumSet.of(
		CommandHolder.OPEN_MAIN_PAGE,
		CommandHolder.OPEN_CATALOG_PAGE,
		CommandHolder.SEND_FORGET_PASSWORD_DATA,
		CommandHolder.FORGET_PASSWORD_LOG_IN,
		CommandHolder.OPEN_CATALOG_BY_CATEGORY_PAGE,
		CommandHolder.OPEN_PRODUCT_PAGE,
		CommandHolder.LOG_IN,
		CommandHolder.REGISTRATION,
		CommandHolder.SWITCH_LANGUAGE
	);

	private static final Set<CommandHolder> userCommands = EnumSet.of(
		CommandHolder.OPEN_MAIN_PAGE,
		CommandHolder.OPEN_CATALOG_PAGE,
		CommandHolder.SEND_FORGET_PASSWORD_DATA,
		CommandHolder.FORGET_PASSWORD_LOG_IN,
		CommandHolder.OPEN_CATALOG_BY_CATEGORY_PAGE,
		CommandHolder.OPEN_PRODUCT_PAGE,
		CommandHolder.LOG_IN,
		CommandHolder.REGISTRATION,
		CommandHolder.SWITCH_LANGUAGE,
		CommandHolder.ADD_PRODUCT_TO_CART,
		CommandHolder.CONSTRUCT_ORDER,
		CommandHolder.DELETE_PRODUCT_FROM_CART,
		CommandHolder.LOG_OUT,
		CommandHolder.OPEN_CART_PAGE,
		CommandHolder.OPEN_PROFILE_PAGE,
		CommandHolder.OPEN_TOP_UP_BALANCE_PAGE,
		CommandHolder.OPEN_USER_ORDERS_PAGE,
		CommandHolder.TOP_UP_BALANCE,
		CommandHolder.UPDATE_USER_INFO,
		CommandHolder.WRITE_REVIEW
			
	);

	private static final Set<CommandHolder> adminCommands = EnumSet.of(
		CommandHolder.OPEN_MAIN_PAGE,
		CommandHolder.OPEN_CATALOG_PAGE,
		CommandHolder.SEND_FORGET_PASSWORD_DATA,
		CommandHolder.FORGET_PASSWORD_LOG_IN,
		CommandHolder.OPEN_CATALOG_BY_CATEGORY_PAGE,
		CommandHolder.OPEN_PRODUCT_PAGE,
		CommandHolder.LOG_IN,
		CommandHolder.REGISTRATION,
		CommandHolder.SWITCH_LANGUAGE,
		CommandHolder.ADD_PRODUCT_TO_CART,
		CommandHolder.CONSTRUCT_ORDER,
		CommandHolder.DELETE_PRODUCT_FROM_CART,
		CommandHolder.LOG_OUT,
		CommandHolder.OPEN_CART_PAGE,
		CommandHolder.OPEN_PROFILE_PAGE,
		CommandHolder.OPEN_TOP_UP_BALANCE_PAGE,
		CommandHolder.OPEN_USER_ORDERS_PAGE,
		CommandHolder.TOP_UP_BALANCE,
		CommandHolder.UPDATE_USER_INFO,
		CommandHolder.WRITE_REVIEW,
		CommandHolder.ADD_PRODUCT,
		CommandHolder.ADD_PRODUCT_CATEGORY,
		CommandHolder.BAN_USER,
		CommandHolder.CONFIRM_ORDER,
		CommandHolder.EDIT_PRODUCT,
		CommandHolder.OPEN_ADD_PRODUCT_CATEGORY_PAGE,
		CommandHolder.OPEN_ADD_PRODUCT_PAGE,
		CommandHolder.OPEN_ALL_ORDERS_PAGE,
		CommandHolder.OPEN_ALL_USERS_LIST,
		CommandHolder.OPEN_EDIT_PRODUCT_PAGE
	);
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		UserType userRole = UserType.valueOf(request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE).toString().toUpperCase());
		Set<CommandHolder> permittedCommands;
		switch (userRole) {
			case ADMIN:
				permittedCommands = adminCommands;
				break;
			case USER:
				permittedCommands = userCommands;
				break;
			case GUEST:
				permittedCommands = guestCommands;
				break;
			default:
				permittedCommands = Collections.emptySet();
		}

		String commandName = request.getParameter(JSPAttributeStorage.COMMAND);
		CommandHolder command;
		if (commandName != null) {
			command = CommandHolder.getCommandEnumByName(commandName);
		} else {
			command = CommandHolder.OPEN_MAIN_PAGE;
		}

		if (permittedCommands.contains(command)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			logger.info(String.format("Command is not in %s's Role scope: %s", userRole.name(), commandName));
			request.getRequestDispatcher(PageStorage.HOME).forward(request, response);
		}
	}
}