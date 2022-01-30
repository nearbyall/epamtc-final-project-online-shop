package by.epamtc.melnikov.onlineshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.CartItem;
import by.epamtc.melnikov.onlineshop.dao.CartDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLBaseDAO;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLQueriesStorage;

/**
 * SQL {@link CartDAO} interface implementation
 * 
 * @author nearbyall
 *
 */
public class SQLCartDAOImpl extends SQLBaseDAO implements CartDAO {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public CartItem addCartItem(CartItem cartItem) throws DAOException {

		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_CART_ITEM)) {
			preparedStatement.setInt(1, cartItem.getUserId());
			preparedStatement.setInt(2, cartItem.getProduct().getId());
			preparedStatement.setInt(3, cartItem.getCount());
			preparedStatement.setTimestamp(4, cartItem.getCreatedAt());
			preparedStatement.setTimestamp(5, cartItem.getUpdatedAt());
			preparedStatement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("CartItem %s addition error", cartItem), e);
			throw new DAOException("query.cartItem.addition.commonError", e);
		}
		
		return cartItem;
		
	}

	@Override
	public CartItem deleteCartItem(CartItem cartItem) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.CLEAR_CART_ITEMS_BY_PRODUCT_ID)) {
			preparedStatement.setInt(1, cartItem.getProduct().getId());
			preparedStatement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("CartItem %s removing error", cartItem), e);
			throw new DAOException("query.cartItem.removing.commonError", e);
		}
			
		return cartItem;
		
	}
	
	@Override
	public CartItem updatedCartItemInfo(CartItem cartItem) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_CART_ITEMS_COUNT_BY_USER_ID_AND_PRODUCT_ID)) {
			preparedStatement.setInt(1, cartItem.getCount());
			preparedStatement.setTimestamp(2, cartItem.getUpdatedAt());
			preparedStatement.setInt(3, cartItem.getProduct().getId());
			preparedStatement.setInt(4, cartItem.getUserId());
			preparedStatement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("Cart count %s updating error", cartItem.getCount()), e);
			throw new DAOException("service.commonError", e);
		}
			
		return cartItem;
			
	}
	
	@Override
	public List<CartItem> findAllCartItemsByUserId(int userId) throws DAOException {
		
		ResultSet resultSet = null;
		List<CartItem> cartItems = Collections.emptyList();
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_CART_ITEMS_BY_USER_ID)) {
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Products is empty");
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				cartItems = new ArrayList<>(listSize);
				while (resultSet.next()) {
					cartItems.add(constructCartItemByResultSet(resultSet));
				}
			}
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Cart items List finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
		return cartItems;
		
	}
	
	@Override
	public int findCartItemCount(int userId, int productId) throws DAOException {
		
		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_CART_ITEMS_COUNT_BY_USER_ID_AND_PRODUCT_ID)) {
			preparedStatement.setInt(1, productId);
			preparedStatement.setInt(2, userId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("CartItems is empty");
				return 0;
			}
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("CartItems count finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
	}

}
