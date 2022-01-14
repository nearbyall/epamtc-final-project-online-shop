package by.epamtc.melnikov.onlineshop.controller;

import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandProvider;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.dao.pool.ConnectionPool;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(Controller.class);
	private CommandProvider commandProvider = new CommandProvider();
	
	public Controller() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
        try {
            ConnectionPool.getInstance().init();
        } catch (ConnectionPoolException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SessionRequestContent content = new SessionRequestContent(request);
		logger.info(content);
		
		Command command = commandProvider.getCommand(request.getParameter(JSPAttributeStorage.COMMAND));
		
		CommandResult result = command.execute(request, response);
		
		if (result.getDirection() == Direction.FORWARD)
			request.getRequestDispatcher(result.getPage()).forward(request, response);
		
		if (result.getDirection() == Direction.REDIRECT)
			response.sendRedirect(result.getPage());
		
	}

	@Override
	public void destroy() {
		ConnectionPool.getInstance().destroy();
		super.destroy();
	}
	
}