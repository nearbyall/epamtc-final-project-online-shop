package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;
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
 * for registration process.
 * 
 * @author nearbyall
 *
 */
public class CommandRegistration implements Command {

	private static final UserService userService = ServiceProvider.getInstance().getUserService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		

		User user = constructUser(request);
		
		try {
			userService.registration(user);

			request.getSession().setAttribute(AttributeNameStorage.USER_EMAIL, user.getEmail());
			request.getSession().setAttribute(AttributeNameStorage.USER_ROLE, user.getRole().getName());
			request.getSession().setAttribute(AttributeNameStorage.USER_ID, user.getId());
			request.getSession().setAttribute(AttributeNameStorage.USER_DATA, user);

			String redirectCommand = request.getParameter(AttributeNameStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
			
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.REGISTRATION);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}
	
	private User constructUser(HttpServletRequest request) {
		
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
		
		User user = new UserBuilder()
				.withName(request.getParameter(AttributeNameStorage.USER_FIRST_NAME).trim())
				.withSurname(request.getParameter(AttributeNameStorage.USER_LAST_NAME).trim())
				.withMobile(request.getParameter(AttributeNameStorage.USER_MOBILE).trim())
				.withEmail(request.getParameter(AttributeNameStorage.USER_EMAIL).trim())
				.withPasswordEncrypted(request.getParameter(AttributeNameStorage.USER_PASSWORD).trim())
				.withRegisteredAt(currentTimestamp)
				.withLastLoginAt(currentTimestamp)
				.withStatusType(StatusType.VALID)
				.withUserType(UserType.USER)
				.build();
		
		return user;
		
	}
	
}
