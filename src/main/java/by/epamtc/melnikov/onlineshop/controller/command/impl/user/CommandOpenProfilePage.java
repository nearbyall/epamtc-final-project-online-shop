package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#PROFILE} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenProfilePage implements Command {

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		result.setDirection(Direction.FORWARD);
		result.setPage(PageStorage.PROFILE);
		
		return result;
		
	}

}
