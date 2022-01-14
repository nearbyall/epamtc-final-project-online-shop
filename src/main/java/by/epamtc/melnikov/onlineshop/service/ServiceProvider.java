package by.epamtc.melnikov.onlineshop.service;

import by.epamtc.melnikov.onlineshop.service.impl.UserServiceImpl;

public class ServiceProvider {

	private final UserService userService;
	
	private ServiceProvider() {
		userService = new UserServiceImpl();
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
	
}
