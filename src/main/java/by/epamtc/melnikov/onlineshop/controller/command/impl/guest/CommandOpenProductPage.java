package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.Review;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ProductService;
import by.epamtc.melnikov.onlineshop.service.ReviewService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#PRODUCT} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenProductPage implements Command {

	private static final ProductService productService = ServiceProvider.getInstance().getProductService();
	private static final ReviewService reviewService = ServiceProvider.getInstance().getReviewService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		int productId = Integer.parseInt(request.getParameter(AttributeNameStorage.PRODUCT_ID));
		
		try {
			Product product = productService.findProductById(productId);
			List<Review> reviews = reviewService.findAllReviewsByProductId(productId);
			request.setAttribute(AttributeNameStorage.PRODUCT, product);
			request.setAttribute(AttributeNameStorage.REVIEWS_LIST, reviews);
			result.setPage(PageStorage.PRODUCT);
			result.setDirection(Direction.FORWARD);
		} catch (NumberFormatException e) {
			// TODO Validate in filters
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.HOME);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}

}
