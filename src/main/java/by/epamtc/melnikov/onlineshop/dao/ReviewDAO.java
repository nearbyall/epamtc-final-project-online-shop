package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Review;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store
 * and restore {@link Review} bean.
 * 
 * @author nearbyall
 *
 */
public interface ReviewDAO {
	
	/**
	 * 
	 * @param review
	 * @return
	 * @throws DAOException
	 */
	Review addReview(Review review) throws DAOException;
	
	/**
	 * 
	 * @param review
	 * @return
	 * @throws DAOException
	 */
	Review editReview(Review review) throws DAOException;
	
	/**
	 * 
	 * @param id
	 * @throws DAOException
	 */
	void removeReviewById(int id) throws DAOException;
	
	/**
	 * 
	 * @param productId
	 * @return
	 * @throws DAOException
	 */
	List<Review> findAllReviewsByProductId(int productId) throws DAOException;
	
	/**
	 * 
	 * @param productId
	 * @return
	 * @throws DAOException
	 */
	int findReviewsCountByProductId(int productId) throws DAOException;
	
}
