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

/**
 * A class designed to directly process requests from the client and return the results.
 * Initializes {@link ConnectionPool} in the method {@link Controller#init()} and destroys it
 * in the method {@link Controller#destroy()}. Request processing happens in the method
 * {@link Controller#process(HttpServletRequest, HttpServletResponse)}
 * 
 * @author nearbyall
 *
 */
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger();
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

	/**
	 * Receives a request and a response. Reads information to {@link SessionRequestContent} and logs it.
	 * Creates an object of type {@link Command} and using a class {@link CommandProvider} chooses needed command.
	 * As a result, depending on the {@link Direction} field in the {@link CommandResult}, either a forward or a redirect is used.
	 * 
	 * @param request the {@link HttpServletRequest}
	 * @param response the {@link HttpServletResponse}
	 * @throws ServletException servlet can throw when it encounters difficulty
	 * @throws IOException if an input or output error is detected when the servlet handles the GET request
	 */
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