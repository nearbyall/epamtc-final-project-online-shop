package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Review;
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
	 * 
	 * @param review
	 * @return
	 * @throws ServiceException
	 */
	Review addReview(Review review) throws ServiceException;
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	List<Review> findAllReviewsByProductId(int productId) throws ServiceException;
	
}
