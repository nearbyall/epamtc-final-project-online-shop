package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for log out process.
 * 
 * @author nearbyall
 *
 */
public class CommandLogOut implements Command {

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		result.setDirection(Direction.FORWARD);
		
		request.getSession().removeAttribute(JSPAttributeStorage.USER_DATA);
		request.getSession().removeAttribute(JSPAttributeStorage.USER_EMAIL);
		request.getSession().removeAttribute(JSPAttributeStorage.USER_ROLE);
		request.getSession().removeAttribute(JSPAttributeStorage.USER_ID);
		
		request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, UserType.GUEST.getName());
		
		result.setPage(PageStorage.HOME);
		return result;
		
	}

}