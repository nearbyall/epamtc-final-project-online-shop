package by.epamtc.melnikov.onlineshop.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.builder.ProductBuilder;
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.impl.SQLProductDAOImpl;
import by.epamtc.melnikov.onlineshop.dao.impl.SQLUserDAOImpl;
import by.epamtc.melnikov.onlineshop.dao.pool.ConnectionPool;

/**
 * Abstraction needed to create SQL implementation of DAO interfaces.
 * Contains many helper methods for working with the database.
 * 
 * @author nearbyall
 *
 */
public abstract class SQLBaseDAO {
	
	private static final Logger logger = LogManager.getLogger();

	protected final ConnectionPool pool;

	/*
	 * Product
	 */
	protected static final String UNIQUE_PRODUCT_TITLE_MESSAGE = "products.title_UNIQUE";
	protected static final String UNIQUE_PRODUCT_IMG_PATH_MESSAGE = "products.imgPath_UNIQUE";
	protected static final String UNIQUE_NAME_MESSAGE = "product_categories.name_UNIQUE";
	protected static final String UNIQUE_IMG_PATH_MESSAGE = "product_categories.imgPath_UNIQUE";
	
	protected static final String PRODUCT_CATEGORY_ID_COLUMN_NAME = "product_categories.id";
	protected static final String PRODUCT_CATEGORY_NAME_COLUMN_NAME = "product_categories.name";
	protected static final String PRODUCT_CATEGORY_IMG_PATH_COLUMN_NAME = "product_categories.imgPath";
    
	protected static final String PRODUCT_ID_COLUMN_NAME = "products.id";
	protected static final String PRODUCT_IMG_PATH_COLUMN_NAME = "products.imgPath";
	protected static final String PRODUCT_TITLE_COLUMN_NAME = "products.title";
	protected static final String PRODUCT_DESCRIPTION_COLUMN_NAME = "products.description";
	protected static final String PRODUCT_UPDATED_AT_COLUMN_NAME = "products.updatedAt";
	protected static final String PRODUCT_CREATED_AT_COLUMN_NAME = "products.createdAt";
	protected static final String PRODUCT_COUNT_COLUMN_NAME = "products.count";
	protected static final String PRODUCT_PRICE_COLUMN_NAME = "products.price";
	
	/*
	 * User
	 */
	protected static final String UNIQUE_EMAIL_MESSAGE = "users.email_UNIQUE";
	protected static final String UNIQUE_MOBILE_MESSAGE = "users.mobile_UNIQUE";
    
	protected static final String USER_ID_COLUMN_NAME = "users.id";
	protected static final String USER_ROLE_ID_COLUMN_NAME = "users.roleId";
	protected static final String USER_EMAIL_COLUMN_NAME = "users.email";
	protected static final String USER_PASSWORD_COLUMN_NAME = "users.passwordEncrypted";
	protected static final String USER_NAME_COLUMN_NAME = "users.name";
	protected static final String USER_SURNAME_COLUMN_NAME = "users.surname";
	protected static final String USER_MOBILE_COLUMN_NAME = "users.mobile";
	protected static final String USER_BALANCE_COLUMN_NAME = "users.balance";
	protected static final String USER_STATUS_ID_COLUMN_NAME = "users.statusId";
	protected static final String USER_REGISTERED_AT_COLUMN_NAME = "users.registeredAt";
	protected static final String USER_LAST_LOGIN_AT_COLUMN_NAME = "users.lastLoginAt";
	
	/*
	 * Cart
	 */
	protected static final String CART_ITEMS_ID = "cart_items.id";
	protected static final String CART_ITEMS_PRODUCT_ID = "cart_items.productId";
	protected static final String CART_ITEMS_USER_ID = "cart_items.userId";
	protected static final String CART_ITEMS_COUNT = "cart_items.count";
	protected static final String CART_ITEMS_CREATED_AT = "cart_items.createdAt";
	protected static final String CART_ITEMS_UPDATED_AT = "cart_items.updatedAt";
	
	
	protected SQLBaseDAO(){
		pool = ConnectionPool.getInstance();
	}

	protected void closeResultSet(ResultSet resultSet) throws DAOException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.error("Can`t close resultSet", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void closeConnection(Connection connection) throws DAOException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Can`t close connection", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void connectionCommitChanges(Connection connection) throws DAOException {
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				logger.error("Can`t commit connection's changes", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void connectionSetAutoCommit(Connection connection, boolean value) throws DAOException {
		if (connection != null) {
			try {
				connection.setAutoCommit(value);
			} catch (SQLException e) {
				logger.error("Can`t set autocommmit to connection", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void connectionsRollback(Connection connection) throws DAOException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				logger.error("Can`t rollback connection result", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	/**
	 * Extracts founded {@link User} from <tt>resultSet</tt>.
	 * Throws SQLException and DAOException
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link User}
	 * @return an {@link User} which has been extracted
	 * @throws SQLException @see {@link SQLUserDAOImpl#constructUserByResultSet(ResultSet)}
	 * @throws DAOException if {@link User} is not found
	 */
	protected User extractFoundedUserFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
		if (resultSet.next()) {
			return constructUserByResultSet(resultSet);
		} else {
			throw new DAOException("query.user.getUser.userNotFound");
		}
	}
    
	/**
	 * Constructs user from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link User}
	 * @return an {@link User} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	protected User constructUserByResultSet(ResultSet resultSet) throws SQLException {
		return new UserBuilder().withId(resultSet.getInt(USER_ID_COLUMN_NAME))
				.withUserType(UserType.getTypeById(resultSet.getInt(USER_ROLE_ID_COLUMN_NAME)))
				.withEmail(resultSet.getString(USER_EMAIL_COLUMN_NAME))
				.withPasswordEncrypted(resultSet.getString(USER_PASSWORD_COLUMN_NAME))
				.withName(resultSet.getNString(USER_NAME_COLUMN_NAME))
				.withSurname(resultSet.getNString(USER_SURNAME_COLUMN_NAME))
				.withMobile(resultSet.getString(USER_MOBILE_COLUMN_NAME))
				.withStatusType(StatusType.getTypeById(resultSet.getInt(USER_STATUS_ID_COLUMN_NAME)))
				.withRegisteredAt(resultSet.getTimestamp(USER_REGISTERED_AT_COLUMN_NAME))
				.withLastLoginAt(resultSet.getTimestamp(USER_LAST_LOGIN_AT_COLUMN_NAME))
				.withBalance(resultSet.getDouble(USER_BALANCE_COLUMN_NAME))
				.build();
	}
	
	/**
	 * Constructs {@link ProductCategory} from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link ProductCategory}
	 * @return an {@link ProductCategory} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	protected ProductCategory constructProductCategoryByResultSet(ResultSet resultSet) throws SQLException {
		ProductCategory category = new ProductCategory();
		category.setId(resultSet.getInt(PRODUCT_CATEGORY_ID_COLUMN_NAME));
		category.setName(resultSet.getString(PRODUCT_CATEGORY_NAME_COLUMN_NAME));
		category.setImgPath(resultSet.getString(PRODUCT_CATEGORY_IMG_PATH_COLUMN_NAME));
		return category;
	}
    
	/**
	 * Constructs {@link Product} from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link Product}
	 * @return an {@link Product} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	protected Product constructProductByResultSet(ResultSet resultSet) throws SQLException {
		
		return new ProductBuilder()
				.withId(resultSet.getInt(PRODUCT_ID_COLUMN_NAME))
				.withTitle(resultSet.getString(PRODUCT_TITLE_COLUMN_NAME))
				.withPrice(resultSet.getDouble(PRODUCT_PRICE_COLUMN_NAME))
				.withCount(resultSet.getInt(PRODUCT_COUNT_COLUMN_NAME))
				.withDescription(resultSet.getString(PRODUCT_DESCRIPTION_COLUMN_NAME))
				.withImgPath(resultSet.getString(PRODUCT_IMG_PATH_COLUMN_NAME))
				.withCreatedAt(resultSet.getTimestamp(PRODUCT_CREATED_AT_COLUMN_NAME))
				.withUpdatedAt(resultSet.getTimestamp(PRODUCT_UPDATED_AT_COLUMN_NAME))
				.withCategory(constructProductCategoryByResultSet(resultSet))
				.build();
		
	}
	
	/**
	 * Extracts founded {@link Product} from <tt>resultSet</tt>.
	 * Throws SQLException and DAOException.
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link Product}
	 * @return an {@link Product} which has been extracted
	 * @throws SQLException @see {@link SQLProductDAOImpl#constructProductByResultSet(ResultSet)}
	 * @throws DAOException if {@link Product} is not found
	 */
	protected Product extractFoundedProductFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
		if (resultSet.next()) {
			return constructProductByResultSet(resultSet);
		} else {
			throw new DAOException("query.product.getProduct.productNotFound");
		}
	}
	
	/**
	 * Constructs {@link CartItem} from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link CartItem}
	 * @return an {@link Product} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	protected CartItem constructCartItemByResultSet(ResultSet resultSet) throws SQLException {
		CartItem cartItem = new CartItem();
		cartItem.setId(resultSet.getInt(CART_ITEMS_ID));
		cartItem.setUserId(resultSet.getInt(CART_ITEMS_USER_ID));
		cartItem.setProduct(constructProductByResultSet(resultSet));
		cartItem.setCount(resultSet.getInt(CART_ITEMS_COUNT));
		cartItem.setCreatedAt(resultSet.getTimestamp(CART_ITEMS_CREATED_AT));
		cartItem.setUpdatedAt(resultSet.getTimestamp(CART_ITEMS_UPDATED_AT));
		return cartItem;
	}
	
}