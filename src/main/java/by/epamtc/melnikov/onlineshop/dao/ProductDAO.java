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
	 * Retrieves and returns {@link List} of {@link ProductCategory}s into data source
	 * If no such categories contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link ProductCategory}s
	 * @throws DAOException if an error occurs while getting a <tt>category</tt>
	 */
	List<ProductCategory> findAllProductCategories() throws DAOException;
	
}
