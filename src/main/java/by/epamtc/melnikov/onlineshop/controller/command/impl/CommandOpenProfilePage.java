package by.epamtc.melnikov.onlineshop.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;

public class CommandOpenProfilePage implements Command {

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		result.setDirection(Direction.FORWARD);
		result.setPage("/jsp/profile.jsp");
		
		return result;
	}

}
