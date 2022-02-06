package by.epamtc.melnikov.onlineshop.controller.filter;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;

/**
 * 
 * 
 * @author nearbyall
 *
 */
public class AbstractFilter {

	protected String getRedirectURL(HttpServletRequest request, String commandName) {
		return request.getContextPath() + PageStorage.CONTROLLER + "?" + AttributeNameStorage.COMMAND + "=" + commandName;
	}
	
}
