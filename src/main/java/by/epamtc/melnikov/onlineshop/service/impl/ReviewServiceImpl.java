package by.epamtc.melnikov.onlineshop.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.Review;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.ReviewDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.ReviewService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;
import by.epamtc.melnikov.onlineshop.service.validation.ReviewValidator;

/**
 * {@link ReviewService} interface implementation.
 * 
 * @author nearbyall
 *
 */
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewDAO reviewDAO;
	
	public ReviewServiceImpl() {
		reviewDAO = DAOProvider.getInstance().getReviewDAO();
	}
	
	@Override
	public Review addReview(Review review) throws ServiceException {
		
		//TODO validate review
		
		try {
			reviewDAO.addReview(review);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return review;
		
	}

	@Override
	public List<Review> findAllReviewsByProductId(int productId) throws ServiceException {
		
		List<Review> reviews;
		
		try {
			reviews = reviewDAO.findAllReviewsByProductId(productId);
			return reviews;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}

}
