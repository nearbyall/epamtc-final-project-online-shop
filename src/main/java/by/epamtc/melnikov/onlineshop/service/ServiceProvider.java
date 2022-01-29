package by.epamtc.melnikov.onlineshop.service;

import by.epamtc.melnikov.onlineshop.service.impl.*;

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
	private final ReviewService reviewService;
	private final CartService cartService;
	private final OrderService orderService;
	
	private ServiceProvider() {
		userService = new UserServiceImpl();
		productService = new ProductServiceImpl();
		reviewService = new ReviewServiceImpl();
		cartService = new CartServiceImpl();
		orderService = new OrderServiceImpl();
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

	public ReviewService getReviewService() {
		return reviewService;
	}

	public CartService getCartService() {
		return cartService;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	
}