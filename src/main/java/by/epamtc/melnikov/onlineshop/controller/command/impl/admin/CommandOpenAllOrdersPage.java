package by.epamtc.melnikov.onlineshop.controller.command.impl.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.Order;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.OrderService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#ALL_ORDERS} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenAllOrdersPage implements Command {

	private static final OrderService orderService = ServiceProvider.getInstance().getOrderService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		try {
			List<Order> orders = orderService.findAllOrders();
			request.setAttribute(AttributeNameStorage.ORDERS_LIST, orders);
			result.setPage(PageStorage.ALL_ORDERS);
			result.setDirection(Direction.FORWARD);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.ALL_ORDERS);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}

}
