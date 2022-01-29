package by.epamtc.melnikov.onlineshop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;

/**
 * Realization of the pattern Command. Interface implementations are in the class {@link CommandProvider}.
 * 
 * @author nearbyall
 *
 */
public interface Command {

	/**
	 * Processes a request, sets parameters and attributes, and returns a result the {@link CommandResult}.
	 * 
	 * @param request the {@link HttpServlerRequest}
	 * @param response the {@link HttpServlerResponse}
	 * @return the {@link CommandResult} which includes page {@link CommandResult#page} and direction {@link CommandResult#direction}
	 * @throws ServletException command can throw when it encounters difficulty
	 * @throws IOException if an input or output error is detected when the command handles the GET request
	 */
	CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;
	
	/**
	 * Sets the error message as a request attribute.
	 * 
	 * @param request the {@link HttpServlerRequest} which should contain an error message
	 * @param message which must be setted
	 */
	default void setErrorMessage(HttpServletRequest request, String message) {
		request.setAttribute(JSPAttributeStorage.EXCEPTION_MESSAGE, message);
	}

	/**
	 * Defines the pagination context.
	 * 
	 * @param request the {@link HttpServlerRequest} which should contain pagination context
	 * @param fullRecordsQuantity number of records
	 * @param currentPage current page number
	 * @param recordsPerPage number of records per page
	 */
	default void definePaginationContext(HttpServletRequest request, int fullRecordsQuantity, int currentPage, int recordsPerPage) {
		int pagesQuantity = fullRecordsQuantity / recordsPerPage;

		if (pagesQuantity % recordsPerPage > 0) {
			pagesQuantity++;
		}
		request.setAttribute(JSPAttributeStorage.PAGINATION_PAGES_QUANTITY, pagesQuantity);
		request.setAttribute(JSPAttributeStorage.PAGINATION_CURRENT_PAGE, currentPage);
		request.setAttribute(JSPAttributeStorage.PAGINATION_RECORDS_PER_PAGE, recordsPerPage);
	}

	/**
	 * Provides protection against repeated requests after a page refresh.
	 * 
	 * @param request the {@link HttpServlerRequest} which includes needed data
	 * @param commandName command name corresponding to the command responsible for moving to another page
	 * @return Redirect URL
	 */
	default String getRedirectURL(HttpServletRequest request, String commandName) {
		return request.getContextPath() + "/Controller" + "?" + JSPAttributeStorage.COMMAND + "=" + commandName;
	}
	
	/**
	 * 
	 * @return
	 */
	default boolean checkRole(HttpServletRequest request, UserType userType) {
		if(request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE).equals(userType.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
}
