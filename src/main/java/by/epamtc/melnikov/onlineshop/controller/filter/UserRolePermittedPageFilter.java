package by.epamtc.melnikov.onlineshop.controller.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;

/**
 * Servlet Filter implementation class UserRolePermittedPageFilter
 */
public class UserRolePermittedPageFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger();

	private static final Set<String> adminPages = new HashSet<>();
	private static final Set<String> userPages = new HashSet<>();
	private static final Set<String> guestPages = new HashSet<>();

	@Override
	public void init(FilterConfig filterConfig) {
		fillPermittedAdminPages();
		fillPermittedUserPages();
		fillPermittedGuestPages();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		UserType userRole = UserType.valueOf(request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE).toString().toUpperCase());
		Set<String> permittedPages;
		switch (userRole) {
			case ADMIN:
				permittedPages = adminPages;
				break;
			case USER:
				permittedPages = userPages;
				break;
			case GUEST:
				permittedPages = guestPages;
				break;
			default:
				permittedPages = Collections.emptySet();
		}

		String requestPage = request.getRequestURI().replace(request.getContextPath(), "");

		if (permittedPages.contains(requestPage)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			logger.info(String.format("Page is not in %s's Role scope: %s", userRole.name(), requestPage));
			request.getRequestDispatcher(PageStorage.HOME).forward(request, response);
		}
	}
    
	private void fillPermittedAdminPages() {
		guestPages.add(PageStorage.ROOT);
		guestPages.add(PageStorage.HOME);
		guestPages.add(PageStorage.LOG_IN);
		guestPages.add(PageStorage.REGISTRATION);
		guestPages.add(PageStorage.CATALOG);
		guestPages.add(PageStorage.PRODUCT);
		guestPages.add(PageStorage.CATALOG_BY_CATEGORY);
		guestPages.add(PageStorage.CART);
		guestPages.add(PageStorage.USER_ORDERS);
		guestPages.add(PageStorage.PROFILE);
		guestPages.add(PageStorage.TOP_UP_BALANCE);
		guestPages.add(PageStorage.ADD_PRODUCT);
		guestPages.add(PageStorage.ADD_PRODUCT_CATEGORY);
		guestPages.add(PageStorage.USERS_LIST);
		guestPages.add(PageStorage.ALL_ORDERS);
		guestPages.add(PageStorage.EDIT_PRODUCT);
	}

	private void fillPermittedUserPages() {
		guestPages.add(PageStorage.ROOT);
		guestPages.add(PageStorage.HOME);
		guestPages.add(PageStorage.LOG_IN);
		guestPages.add(PageStorage.REGISTRATION);
		guestPages.add(PageStorage.CATALOG);
		guestPages.add(PageStorage.PRODUCT);
		guestPages.add(PageStorage.CATALOG_BY_CATEGORY);
		guestPages.add(PageStorage.CART);
		guestPages.add(PageStorage.USER_ORDERS);
		guestPages.add(PageStorage.PROFILE);
		guestPages.add(PageStorage.TOP_UP_BALANCE);
	}

	private void fillPermittedGuestPages() {
		guestPages.add(PageStorage.ROOT);
		guestPages.add(PageStorage.HOME);
		guestPages.add(PageStorage.LOG_IN);
		guestPages.add(PageStorage.REGISTRATION);
		guestPages.add(PageStorage.CATALOG);
		guestPages.add(PageStorage.PRODUCT);
		guestPages.add(PageStorage.CATALOG_BY_CATEGORY);
		guestPages.add(PageStorage.FORGET_PASSWORD);
	}
}