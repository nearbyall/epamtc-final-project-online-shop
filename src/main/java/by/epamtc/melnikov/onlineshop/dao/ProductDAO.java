package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store
 * and restore {@link Product} bean and {@link ProductCategory} bean.
 * 
 * @author nearbyall
 *
 */
public interface ProductDAO {

	/**
	 * Saves the <tt>product</tt> into data source. Throws DAOException
	 * if an error occurs while writing a <tt>product</tt>.
	 * 
	 * @param product the {@link Product} that should added to data source
	 * @return an {@link Product} which has been added
	 * @throws DAOException if an error occurs while writing a <tt>product</tt>
	 */
	Product addProduct(Product product) throws DAOException;
	
	/**
	 * Saves the <tt>category</tt> into data source. Throws DAOException
	 * if an error occurs while writing a <tt>category</tt>.
	 * 
	 * @param category the {@link ProductCategory} that should added to data source
	 * @return an {@link ProductCategory} which has been added
	 * @throws DAOException if an error occurs while writing a <tt>category</tt>
	 */
	ProductCategory addProductCategory(ProductCategory category) throws DAOException;
	
	/**
	 * Updates the <tt>product</tt> in data source by id. Throws DAOException
	 * if an error occurs while writing a <tt>product</tt>.
	 * 
	 * @param product the {@link Product} that should be updated in data source
	 * @return an {@link Product} which has been updated
	 * @throws DAOException if an error occurs while writing a <tt>product</tt> 
	 */
	Product updateProduct(Product product) throws DAOException;
	
	/**
	 * Retrieves and returns {@link Product} by <tt>id</tt>. Throws DAOException
	 * if an error occurs while getting a <tt>product</tt>.
	 * 
	 * @param id {@link Product}'s id
	 * @return a {@link Product} which has been found
	 * @throws DAOException if an error occurs while getting a <tt>product</tt>
	 */
	Product findProductById(int id) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link Product}s into data source
	 * which should be displayed on a specific page <tt>currentPage</tt> with a
	 * specific number of records <tt>recordsPerPage</tt>.
	 * If no such products contains into data source returns empty {@link List} collection.
	 * 
	 * @param currentPage current client page number
	 * @param recordsPerPage records per client page
	 * @return {@link List} of {@link Product}s
	 * @throws DAOException if an error occurs while getting a <tt>product</tt>
	 */
	List<Product> findAllProductsPerPage(int currentPage, int recordsPerPage) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link Product}s into data source
	 * by specific {@link ProductCategory}'s id which should be displayed on a
	 * specific page <tt>currentPage</tt> with a specific number of records <tt>recordsPerPage</tt>.
	 * If no such products contains into data source returns empty {@link List} collection. 
	 * 
	 * @param currentPage current client page number
	 * @param recordsPerPage records per client page
	 * @param categoryId {@link ProductCategory}'s id
	 * @return {@link List} of {@link Product}s
	 * @throws DAOException if an error occurs while getting a <tt>product</tt>
	 */
	List<Product> findAllProductsByCategoryIdPerPage(int currentPage, int recordsPerPage, int categoryId) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link ProductCategory}s into data source
	 * If no such categories contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link ProductCategory}s
	 * @throws DAOException if an error occurs while getting a <tt>category</tt>
	 */
	List<ProductCategory> findAllProductCategories() throws DAOException;
	
	/**
	 * Retrieves and returns total count of {@link Product}s into data source.
	 * 
	 * @return count of {@link Product}s into data source
	 * @throws DAOException if an error occurs while getting a <tt>products</tt> count
	 */
	int findProductsCount() throws DAOException;
	
	/**
	 * Retrieves and returns total count of {@link Product}s into data source by <tt>categoryId</tt>.
	 * 
	 * @param categoryId {@link ProductCategory}'s id
	 * @return count of {@link Product}s into data source
	 * @throws DAOException if an error occurs while getting a <tt>products</tt> count
	 */
	int findProductsCountByCategoryId(int categoryId) throws DAOException;
	
}