package by.epamtc.melnikov.onlineshop.controller.command.impl.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#EDIT_PRODUCT} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenEditProductPage implements Command {

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandResult result = new CommandResult();
					
		return result;
		
	}

}
