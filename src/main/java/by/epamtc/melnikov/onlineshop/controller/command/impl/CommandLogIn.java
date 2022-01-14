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

public class CommandLogIn implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		String email = request.getParameter(JSPAttributeStorage.USER_EMAIL);
		String password = request.getParameter(JSPAttributeStorage.USER_PASSWORD);
		
		try {
			User user = userService.logInByPassword(email, password);
			request.getSession().setAttribute(JSPAttributeStorage.USER_EMAIL, email);
			request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getRole().getName());
			request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
			request.getSession().setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
            result.setPage("/jsp/main.jsp");
            result.setDirection(Direction.FORWARD);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
            result.setPage("/jsp/login.jsp");
            result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}

}
