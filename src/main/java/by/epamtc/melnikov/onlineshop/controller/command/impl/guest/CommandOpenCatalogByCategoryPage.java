package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ProductService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for open {@link PageStorage#CATALOG} page with products with specific category.
 * 
 * @author nearbyall
 *
 */
public class CommandOpenCatalogByCategoryPage implements Command {

	private static final ProductService productService = ServiceProvider.getInstance().getProductService();
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		int currentPage = Integer.parseInt(request.getParameter(AttributeNameStorage.PAGINATION_CURRENT_PAGE));
		int recordsPerPage = Integer.parseInt(request.getParameter(AttributeNameStorage.PAGINATION_RECORDS_PER_PAGE));
		int categoryId = Integer.parseInt(request.getParameter(AttributeNameStorage.PRODUCT_CATEGORY_ID));
		
		try {
			definePaginationContext(request, productService.findProductsCountByCategoryId(categoryId), currentPage, recordsPerPage);
			List<Product> products = productService.findAllProductsByCategoryIdPerPage(currentPage, recordsPerPage, categoryId);
			request.setAttribute(AttributeNameStorage.PRODUCT_LIST, products);
			result.setPage(PageStorage.CATALOG_BY_CATEGORY);
			result.setDirection(Direction.FORWARD);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.HOME);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}

}
