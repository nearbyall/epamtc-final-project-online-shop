package by.epamtc.melnikov.onlineshop.controller.command.impl.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for changing the ban status of the user {@link User}.
 * 
 * @author nearbyall
 *
 */
public class CommandBanUser implements Command {

	private final static UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		String email = request.getParameter(AttributeNameStorage.USER_EMAIL);
		User user;
		try {
			user = userService.findUserByEmail(email);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.USERS_LIST);
			result.setDirection(Direction.FORWARD);
			return result;
		}
		try {
			user.setStatus(getReversedUserStatusType(user.getStatus()));
			userService.updateUserBanStatus(user);
			String redirectCommand = request.getParameter(AttributeNameStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			user.setStatus(getReversedUserStatusType(user.getStatus()));
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.USERS_LIST);
			result.setDirection(Direction.FORWARD);
		}
		return result;
		
	}
	
	private StatusType getReversedUserStatusType(StatusType statusType) {
		if (statusType == StatusType.BANNED) {
			return StatusType.VALID;
		} else {
			return StatusType.BANNED;
		}
	}

}
