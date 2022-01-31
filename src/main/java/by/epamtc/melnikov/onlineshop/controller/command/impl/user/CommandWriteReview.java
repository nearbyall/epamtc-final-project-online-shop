package by.epamtc.melnikov.onlineshop.controller.command.impl.user;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.Review;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ReviewService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for review writing process.
 * 
 * @author nearbyall
 *
 */
public class CommandWriteReview implements Command {

	private static final ReviewService reviewService = ServiceProvider.getInstance().getReviewService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommandResult result = new CommandResult();
		
		try {
			reviewService.addReview(constructReview(request));
			String redirectCommand = request.getParameter(JSPAttributeStorage.REDIRECT_PAGE_COMMAND);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.HOME);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}
	
	private Review constructReview(HttpServletRequest request) {
		
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
		
		Review review = new Review();
		
		review.setProductId(Integer.parseInt(request.getParameter(JSPAttributeStorage.PRODUCT_ID)));
		review.setUserId(Integer.parseInt(request.getParameter(JSPAttributeStorage.USER_ID)));
		review.setText(request.getParameter(JSPAttributeStorage.REVIEW).replaceAll("(\r\n|\n)", "<br>"));
		review.setCreatedAt(currentTimestamp);
		review.setUpdatedAt(currentTimestamp);
		
		return review;
		
	}

}
