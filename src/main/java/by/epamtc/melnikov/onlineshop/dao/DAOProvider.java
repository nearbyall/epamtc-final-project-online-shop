package by.epamtc.melnikov.onlineshop.dao;

import by.epamtc.melnikov.onlineshop.dao.impl.SQLProductDAOImpl;
import by.epamtc.melnikov.onlineshop.dao.impl.SQLUserDAOImpl;

/**
 * The class serves as a repository of implementations of DAO interfaces
 * like {@link UserDAO} and {@link ProductDAO}.
 * 
 * @author nearbyall
 *
 */
public class DAOProvider {

	private UserDAO userDAO;
	private ProductDAO productDAO;

	private DAOProvider() {
		userDAO = new SQLUserDAOImpl();
		productDAO = new SQLProductDAOImpl();
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
	
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
}