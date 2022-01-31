package by.epamtc.melnikov.onlineshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.Review;
import by.epamtc.melnikov.onlineshop.dao.ReviewDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLBaseDAO;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLQueriesStorage;

/**
 * SQL {@link ReviewDAO} interface implementation
 * 
 * @author nearbyall
 *
 */
public class SQLReviewDAOImpl extends SQLBaseDAO implements ReviewDAO {

	private static final Logger logger = LogManager.getLogger();
		
	@Override
	public Review addReview(Review review) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_REVIEW)) {
				preparedStatement.setInt(1, review.getProductId());
				preparedStatement.setInt(2, review.getUserId());
				preparedStatement.setString(3, review.getText());
				preparedStatement.setTimestamp(4, review.getCreatedAt());
				preparedStatement.setTimestamp(5, review.getUpdatedAt());
				preparedStatement.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException e) {
				logger.warn(String.format("Review %s addition common error", review), e);
				throw new DAOException("query.review.addition.commonError", e);
			} catch (SQLException | ConnectionPoolException e) {
				logger.warn(String.format("Review %s addition common error", review), e);
				throw new DAOException("query.review.addition.commonError", e);
			}
			
			return review;
			
	}

	@Override
	public Review editReview(Review review) throws DAOException {
		
		return review;
		
	}

	@Override
	public int removeReviewById(int id) throws DAOException {
		
		return id;
		
	}

	@Override
	public List<Review> findAllReviewsByProductId(int productId) throws DAOException {
		
		ResultSet resultSet = null;
		List<Review> reviews = Collections.emptyList();
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_REVIEWS_BY_PRODUCT_ID)) {
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Reviews is empty");
				reviews = Collections.emptyList();
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				reviews = new ArrayList<>(listSize);
				while (resultSet.next()) {
					reviews.add(constructReviewByResultSet(resultSet));
				}
			}
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Reviews finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
		return reviews;
		
	}
	
}