package by.epamtc.melnikov.onlineshop.controller.command.impl;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

public class CommandRegistration implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		result.setDirection(Direction.FORWARD);

		User user = constructUser(request);
		
		try {
			userService.registration(user);
			//set attribute user.getRole
			request.getSession().setAttribute("role", "user");
			request.getSession().setAttribute(JSPAttributeStorage.USER_EMAIL, user.getEmail());
			request.getSession().setAttribute(JSPAttributeStorage.USER_REGISTRATION_DATA, user);
			result.setPage("/jsp/main.jsp");
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage("/jsp/registration.jsp");
		}
		
		return result;
		
	}
	
	private User constructUser(HttpServletRequest request) {
		
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
		
		User user = new UserBuilder()
				.withName(request.getParameter(JSPAttributeStorage.USER_FIRST_NAME).trim())
				.withSurname(request.getParameter(JSPAttributeStorage.USER_LAST_NAME).trim())
				.withMobile(request.getParameter(JSPAttributeStorage.USER_MOBILE).trim())
				.withEmail(request.getParameter(JSPAttributeStorage.USER_EMAIL).trim())
				.withPasswordEncrypted(request.getParameter(JSPAttributeStorage.USER_PASSWORD).trim())
				.withRegisteredAt(currentTimestamp)
				.withLastLoginAt(currentTimestamp)
				.withStatusType(StatusType.VALID)
				.withUserType(UserType.USER)
				.build();
		
		return user;
		
	}
	
}
