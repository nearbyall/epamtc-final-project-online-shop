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
	 * Saves the <tt>review</tt> into data source. Throws DAOException
	 * if an error occurs while writing a <tt>review</tt>.
	 * 
	 * @param review the {@link Review} that should added to data source
	 * @return an {@link Review} which has been added
	 * @throws DAOException if an error occurs while writing a <tt>review</tt>
	 */
	Review addReview(Review review) throws DAOException;
	
	/**
	 * Edits the <tt>review</tt> in data source. Throws DAOException
	 * if an error occurs while writing a <tt>review</tt>.
	 * 
	 * @param review the {@link Review} that should edited in data source
	 * @return an {@link Review} which has been edited
	 * @throws DAOException if an error occurs while writing a <tt>review</tt>
	 */
	Review editReview(Review review) throws DAOException;
	
	/**
	 * Removes the <tt>review</tt> from data source by id. Throws DAOException
	 * if an error occurs while removing a <tt>review</tt>.
	 * 
	 * @param id {@link Review}'s id
	 * @return id of the review that was deleted
	 * @throws DAOException if an error occurs while removing a <tt>review</tt>
	 */
	int removeReviewById(int id) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link Review}s into data source.
	 * If no such reviews contains into data source returns empty {@link List} collection.
	 * 
	 * @param productId {@link Reviews}'s productId 
	 * @return {@link List} of {@link Reviews}s
	 * @throws DAOException if an error occurs while getting a <tt>review</tt>
	 */
	List<Review> findAllReviewsByProductId(int productId) throws DAOException;
	
}
