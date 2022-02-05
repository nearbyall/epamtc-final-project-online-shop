package by.epamtc.melnikov.onlineshop.controller.filter.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class XSSProtectionFilter.
 * Needed to protect XSS attacks.
 * 
 * @author nearbyall
 *
 */
public class XSSProtectionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

}
