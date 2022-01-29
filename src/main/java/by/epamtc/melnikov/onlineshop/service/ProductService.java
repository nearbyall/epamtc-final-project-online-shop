package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
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
	 * Finds {@link Product} by id via DAO layer.
	 * 
	 * @param id {@link Product}'s id
	 * @return a {@link Product} which has been found
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	Product findProductById(int id) throws ServiceException;
	
	/**
	 * Finds all {@link Product}s on the current page via DAO layer.
	 * 
	 * @param currentPage current client page number
	 * @param recordsPerPage records per client page
	 * @return {@link List} of {@link Product}s
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<Product> findAllProductsPerPage(int currentPage, int recordsPerPage) throws ServiceException;
	
	/**
	 * Finds all {@link Product}s on the current page by {@link ProductCategory}'s id via DAO layer.
	 * 
	 * @param currentPage current client page number
	 * @param recordsPerPage records per client page
	 * @return {@link List} of {@link Product}s
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<Product> findAllProductsByCategoryIdPerPage(int currentPage, int recordsPerPage, int categoryId) throws ServiceException;
	
	/**
	 * Finds all {@link ProductCategory}s via DAO layer.
	 * 
	 * @return {@link List} of {@link ProductCategory}s.
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<ProductCategory> findAllProductCategories() throws ServiceException;
	
	/**
	 * Finds total count of {@link Product}s via DAO layer.
	 * 
	 * @return count of {@link Product} into data source
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	int findProductsCount() throws ServiceException;
	
	/**
	 * Finds total count of {@link Product}s by <tt>categoryId</tt> via DAO layer.
	 * 
	 * @param categoryId {@link ProductCategory}'s id
	 * @return count of {@link Product}s into data source
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	int findProductsCountByCategoryId(int categoryId) throws ServiceException;
	
}
