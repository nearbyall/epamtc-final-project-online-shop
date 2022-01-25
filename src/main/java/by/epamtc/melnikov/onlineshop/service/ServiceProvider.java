package by.epamtc.melnikov.onlineshop.service;

import by.epamtc.melnikov.onlineshop.service.impl.ProductServiceImpl;
import by.epamtc.melnikov.onlineshop.service.impl.UserServiceImpl;

/**
 * The class serves as a repository of implementations of Service interfaces
 * like {@link UserService} and {@link ProductService}.
 * 
 * @author nearbyall
 *
 */
public class ServiceProvider {

	private final UserService userService;
	private final ProductService productService;
	
	private ServiceProvider() {
		userService = new UserServiceImpl();
		productService = new ProductServiceImpl();
	}
	
	private static class ServiceProviderHolder {
		static final ServiceProvider instance = new ServiceProvider();
	}
	
	public static ServiceProvider getInstance() {
		return ServiceProviderHolder.instance;
	}

	public UserService getUserService() {
		return userService;
	}

	public ProductService getProductService() {
		return productService;
	}
	
}
