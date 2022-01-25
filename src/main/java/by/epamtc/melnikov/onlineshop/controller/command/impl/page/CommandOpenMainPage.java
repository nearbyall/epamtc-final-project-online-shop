package by.epamtc.melnikov.onlineshop.controller.command.impl.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#HOME} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenMainPage implements Command {

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		result.setDirection(Direction.FORWARD);

		if (request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE) == null) {
			request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, "guest");	
		}
		
		//TODO if language is setted
		request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, "en");
		
		result.setPage(PageStorage.HOME);
		return result;
		
	}

}
