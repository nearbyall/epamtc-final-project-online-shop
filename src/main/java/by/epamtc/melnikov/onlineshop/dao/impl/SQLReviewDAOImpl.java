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
	
	/** Field contains the column name of {@link Review}'s id*/
	private static final String REVIEW_ID_COLUMN_NAME = "reviews.id";
	/** Field contains the column name of {@link Review}'s userId*/
	private static final String REVIEW_USER_ID_COLUMN_NAME = "reviews.userId";
	/** Field contains the column name of {@link Review}'s productId*/
	private static final String REVIEW_PRODUCT_ID_COLUMN_NAME = "reviews.productId";
	/** Field contains the column name of {@link Review}'s review*/
	private static final String REVIEW_TEXT_COLUMN_NAME = "reviews.review";
	/** Field contains the column name of {@link Review}'s createdAt*/
	private static final String REVIEW_CREATED_AT_COLUMN_NAME = "reviews.createdAt";
	/** Field contains the column name of {@link Review}'s updatedAt*/
	private static final String REVIEW_UPDATED_AT_COLUMN_NAME = "reviews.updatedAt";

	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeReviewById(int id) throws DAOException {
		// TODO Auto-generated method stub
		
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

	@Override
	public int findReviewsCountByProductId(int productId) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Constructs {@link Review} from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link Review}
	 * @return an {@link Review} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	private Review constructReviewByResultSet(ResultSet resultSet) throws SQLException {
		
		Review review = new Review();
		
		review.setId(resultSet.getInt(REVIEW_ID_COLUMN_NAME));
		review.setProductId(resultSet.getInt(REVIEW_USER_ID_COLUMN_NAME));
		review.setUserId(resultSet.getInt(REVIEW_PRODUCT_ID_COLUMN_NAME));
		review.setText(resultSet.getString(REVIEW_TEXT_COLUMN_NAME));
		review.setCreatedAt(resultSet.getTimestamp(REVIEW_CREATED_AT_COLUMN_NAME));
		review.setUpdatedAt(resultSet.getTimestamp(REVIEW_UPDATED_AT_COLUMN_NAME));
		
		return review;
		
	}
	
}
