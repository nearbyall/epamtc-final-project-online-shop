package by.epamtc.melnikov.onlineshop.dao;

import by.epamtc.melnikov.onlineshop.dao.impl.*;

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
	private ReviewDAO reviewDAO;
	private OrderDAO orderDAO;
	private CartDAO cartDAO;

	private DAOProvider() {
		userDAO = new SQLUserDAOImpl();
		productDAO = new SQLProductDAOImpl();
		reviewDAO = new SQLReviewDAOImpl();
		orderDAO = new SQLOrderDAOImpl();
		cartDAO = new SQLCartDAOImpl();
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

	public ReviewDAO getReviewDAO() {
		return reviewDAO;
	}

	public void setReviewDAO(ReviewDAO reviewDAO) {
		this.reviewDAO = reviewDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public CartDAO getCartDAO() {
		return cartDAO;
	}

	public void setCartDAO(CartDAO cartDAO) {
		this.cartDAO = cartDAO;
	}
	
}