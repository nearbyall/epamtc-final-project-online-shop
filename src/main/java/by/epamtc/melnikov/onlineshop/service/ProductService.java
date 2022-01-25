package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The interface describes the opportunity of processing {@link Product} data
 * and their components like a {@link ProductCategory} received from the client 
 * and then sending it to the DAO layer to process the incoming request.
 * 
 * @author nearbyall
 *
 */
public interface ProductService {

	/**
	 * Gets a {@link Product} as a parameter, validates it and
	 * adds to data source via DAO layer.
	 * 
	 * @param product {@link Product} which should be added
	 * @return {@link Product} which has been added
	 * @throws ServiceException if validation by {@link ProductValidator} has not been passed,
	 * or DAO layer throw their {@link DAOException}
	 */
	Product addProduct(Product product) throws ServiceException;
	
	/**
	 * Gets a {@link ProductCategory} as a parameter, validates it and
	 * adds to data source via DAO layer
	 * 
	 * @param category {@link ProductCategory} which should be added
	 * @return {@link ProductCategory} which has been added
	 * @throws ServiceException if validation by {@link ProductValidator} has not been passed,
	 * or DAO layer throw their {@link DAOException}
	 */
	ProductCategory addProductCategory(ProductCategory category) throws ServiceException;
	
	/**
	 * Finds all {@link ProductCategory}s via DAO layer.
	 * 
	 * @return {@link List} of {@link ProductCategory}s.
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<ProductCategory> findAllProductCategories() throws ServiceException;
	
}
