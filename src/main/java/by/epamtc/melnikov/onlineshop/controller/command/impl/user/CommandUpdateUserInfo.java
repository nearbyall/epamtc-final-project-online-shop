package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
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
 * for updating user info process.
 * 
 * @author nearbyall
 *
 */
public class CommandUpdateUserInfo implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		String email = (String) request.getSession().getAttribute(AttributeNameStorage.USER_EMAIL);
		User updatedUser;
		
		try {
			updatedUser = userService.findUserByEmail(email);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.PROFILE);
			result.setDirection(Direction.FORWARD);
			return result;
		}
		
		try {
			updateUserInfo(request, updatedUser);
			userService.updateUserProfileData(updatedUser);
			setUserInfoToRequest(request, updatedUser);
			String redirectCommand = request.getParameter(AttributeNameStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			//setUserInfoToRequest(request, updatedUser);
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.PROFILE);
			result.setDirection(Direction.FORWARD);
			return result;
        }
		
		request.getSession().setAttribute(AttributeNameStorage.USER_DATA, updatedUser);
		return result;
		
	}
	
	private void updateUserInfo(HttpServletRequest request, User currentUser) {
		String password = request.getParameter(AttributeNameStorage.USER_PASSWORD);
		String phoneNumber = request.getParameter(AttributeNameStorage.USER_MOBILE).trim();
		String name = request.getParameter(AttributeNameStorage.USER_FIRST_NAME);
		String surname = request.getParameter(AttributeNameStorage.USER_LAST_NAME);
		currentUser.setEncryptedPassword(password);
		currentUser.setMobile(phoneNumber);
		currentUser.setName(name);
		currentUser.setSurname(surname);
	}

	private void setUserInfoToRequest(HttpServletRequest request, User user) {
		request.setAttribute(AttributeNameStorage.USER_DATA, user);
	}
    
}
