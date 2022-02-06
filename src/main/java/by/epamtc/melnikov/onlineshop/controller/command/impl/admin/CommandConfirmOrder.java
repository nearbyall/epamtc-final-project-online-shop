package by.epamtc.melnikov.onlineshop.controller.command.impl.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.type.OrderType;
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
 * for order confirmation process.
 * 
 * @author nearbyall
 *
 */
public class CommandConfirmOrder implements Command {

	private static final OrderService orderService = ServiceProvider.getInstance().getOrderService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		int orderId = Integer.parseInt(request.getParameter(AttributeNameStorage.ORDER_ID));
		int statusId = getReversedOrderStatusId(Integer.parseInt(request.getParameter(AttributeNameStorage.ORDER_STATUS_ID)));
		
		try {
			orderService.updateOrderStatus(orderId, statusId);
			String redirectCommand = request.getParameter(AttributeNameStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.ALL_ORDERS);
			result.setDirection(Direction.FORWARD);
		}
		return result;
		
	}
	
	private int getReversedOrderStatusId(int statusId) {
		if (statusId == OrderType.ACCEPTED.getId()) {
			return OrderType.NOTACCEPTED.getId();
		} else {
			return OrderType.ACCEPTED.getId();
		}
	}

}
