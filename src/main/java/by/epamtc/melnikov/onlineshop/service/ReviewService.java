package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Review;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The interface describes the opportunity of processing {@link Review} data
 * received from the client and then sending it to the DAO layer to process
 * the incoming request.
 * 
 * @author nearbyall
 *
 */
public interface ReviewService {

	/**
	 * Gets a {@link Review} as a parameter, validates it and
	 * adds to data source via DAO layer.
	 * 
	 * @param review {@link Review} which should be added
	 * @return {@link Review} which has been added
	 * @throws ServiceException if validation by {@link ReviewValidator} has not been passed,
	 * or DAO layer throw their {@link DAOException}
	 */
	Review addReview(Review review) throws ServiceException;
	
	/**
	 * Finds all {@link Review}s on by {@link Review}'s productId via DAO layer.
	 * 
	 * @param productId {@link Review}'s productId
	 * @return {@link List} of {@link Review}s
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<Review> findAllReviewsByProductId(int productId) throws ServiceException;
	
}
