package by.epamtc.melnikov.onlineshop.controller.filter;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;

/**
 * 
 * 
 * @author nearbyall
 *
 */
public class AbstractFilter {

	protected String getRedirectURL(HttpServletRequest request, String commandName) {
		return request.getContextPath() + "/Controller" + "?" + JSPAttributeStorage.COMMAND + "=" + commandName;
	}
	
}
