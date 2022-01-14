package by.epamtc.melnikov.onlineshop.controller.command.impl;

import java.io.IOException;
import java.util.List;

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

public class CommandAllUsersList implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		try {
			List<User> userList = userService.findAllUsers();
			request.setAttribute(JSPAttributeStorage.ALL_USERS_LIST, userList);
            result.setPage("/jsp/usersList.jsp");
            result.setDirection(Direction.FORWARD);
		} catch (ServiceException e) {
            setErrorMessage(request, e.getMessage());
            result.setPage("/jsp/usersList.jsp");
            result.setDirection(Direction.FORWARD);
		}
		
		return result;
	}

}
