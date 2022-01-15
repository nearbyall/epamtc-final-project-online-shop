package by.epamtc.melnikov.onlineshop.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

public class CommandUpdateUserInfo implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		String email = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_EMAIL);
		User updatedUser;
		
		try {
			updatedUser = userService.findUserByEmail(email);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage("/jsp/profile.jsp");
			result.setDirection(Direction.FORWARD);
			return result;
		}
		
		try {
			updateUserInfo(request, updatedUser);
			userService.updateUserProfileData(updatedUser);
			result.setDirection(Direction.FORWARD);
			result.setPage("/jsp/main.jsp");
		} catch (ServiceException e) {
            setUserInfoToRequest(request, updatedUser);
            setErrorMessage(request, e.getMessage());
            result.setPage("/jsp/profile.jsp");
			result.setDirection(Direction.FORWARD);
			return result;
        }
		
		request.getSession().setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, updatedUser);
		return result;
		
	}
	
    private void updateUserInfo(HttpServletRequest request, User currentUser) {
        String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
        String phoneNumber = request.getParameter(JSPAttributeStorage.USER_MOBILE).trim();
        String name = request.getParameter(JSPAttributeStorage.USER_FIRST_NAME);
        String surname = request.getParameter(JSPAttributeStorage.USER_LAST_NAME);
        currentUser.setEncryptedPassword(password);
        currentUser.setMobile(phoneNumber);
        currentUser.setName(name);
        currentUser.setSurname(surname);
    }

    private void setUserInfoToRequest(HttpServletRequest request, User user) {
        request.setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
    }
    
}