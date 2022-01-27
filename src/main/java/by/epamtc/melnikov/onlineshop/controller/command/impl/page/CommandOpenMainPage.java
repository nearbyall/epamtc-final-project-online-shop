package by.epamtc.melnikov.onlineshop.controller.command.impl.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ProductService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#HOME} page.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenMainPage implements Command {

	private static final ProductService productService = ServiceProvider.getInstance().getProductService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		List<ProductCategory> categories;
		
		try {
			categories = productService.findAllProductCategories();
			request.getSession().setAttribute(JSPAttributeStorage.PRODUCT_CATEGORIES_LIST, categories);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
		}
		
		if (request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE) == null) {
			request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, "guest");	
		}
		
		//TODO if language is setted
		request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, "en");
		
		result.setPage(PageStorage.HOME);
		result.setDirection(Direction.FORWARD);
		
		return result;
		
	}

}
