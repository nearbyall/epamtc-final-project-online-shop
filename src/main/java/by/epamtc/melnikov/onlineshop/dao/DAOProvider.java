package by.epamtc.melnikov.onlineshop.dao;

import by.epamtc.melnikov.onlineshop.dao.impl.SQLUserDAOImpl;

public class DAOProvider {

	private final UserDAO userDAO;
	//more fields
	
	private DAOProvider() {
		userDAO = new SQLUserDAOImpl();
		//more init
	}
	
	private static class DAOProviderHolder {
		static final DAOProvider instance = new DAOProvider();
	}
	
	public static DAOProvider getInstance() {
		return DAOProviderHolder.instance;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}
	//more getters
	
}