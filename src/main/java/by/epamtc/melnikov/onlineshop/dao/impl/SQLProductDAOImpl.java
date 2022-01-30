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

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.dao.ProductDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLBaseDAO;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLQueriesStorage;

/**
 * SQL {@link ProductDAO} interface implementation
 * 
 * @author nearbyall
 *
 */
public class SQLProductDAOImpl extends SQLBaseDAO implements ProductDAO {

	private final static Logger logger = LogManager.getLogger();
    
	@Override
	public Product addProduct(Product product) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_PRODUCT)) {
			preparedStatement.setString(1, product.getTitle());
			preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.setInt(3, product.getCount());
			preparedStatement.setTimestamp(4, product.getCreatedAt());
			preparedStatement.setTimestamp(5, product.getUpdatedt());
			preparedStatement.setString(6, product.getDescription());
			preparedStatement.setString(7, product.getImgPath());
			preparedStatement.setInt(8, product.getCategory().getId());
			preparedStatement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().contains(UNIQUE_PRODUCT_TITLE_MESSAGE)) {
				throw new DAOException("query.product.category.addition.nameAlreadyExist", e);
			}
			if (e.getMessage().contains(UNIQUE_PRODUCT_IMG_PATH_MESSAGE)) {
				throw new DAOException("query.product.category.addition.imgPathAlreadyExist", e);
			}
			logger.warn(String.format("ProductCategory %s addition common error", product), e);
			throw new DAOException("query.product.category.addition.commonError", e);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("ProductCategory %s addition common error", product), e);
			throw new DAOException("query.product.category.addition.commonError", e);
		}
		
		return product;
		
	}

	@Override
	public ProductCategory addProductCategory(ProductCategory category) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.INSERT_PRODUCT_CATEGORY)) {
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getImgPath());
			preparedStatement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().contains(UNIQUE_NAME_MESSAGE)) {
				throw new DAOException("query.product.addition.titleAlreadyExist", e);
			}
			if (e.getMessage().contains(UNIQUE_IMG_PATH_MESSAGE)) {
				throw new DAOException("query.product.addition.imgPathAlreadyExist", e);
			}
			logger.warn(String.format("Product %s addition common error", category), e);
			throw new DAOException("query.product.addition.commonError", e);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("Product %s addition common error", category), e);
			throw new DAOException("query.product.addition.commonError", e);
		}
		
		return category;
		
	}
	
	@Override
	public Product findProductById(int productId) throws DAOException {
		
		Product product = null;
		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_PRODUCT_BY_ID)) {
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			product = extractFoundedProductFromResultSet(resultSet);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Product finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
		return product;
		
	}

	@Override
	public List<Product> findAllProductsPerPage(int currentPage, int recordsPerPage) throws DAOException {
		
		ResultSet resultSet = null;
		List<Product> products = Collections.emptyList();
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_PRODCUTS + SQLQueriesStorage.LIMIT_OFFSET_STATEMENT)) {
			preparedStatement.setInt(1, recordsPerPage);
			preparedStatement.setInt(2, (currentPage - 1) * recordsPerPage);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Products is empty");
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				products = new ArrayList<>(listSize);
				while (resultSet.next()) {
					products.add(constructProductByResultSet(resultSet));
				}
			}
			
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Products List finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
		return products;
		
	}
	
	@Override
	public List<Product> findAllProductsByCategoryIdPerPage(int currentPage, int recordsPerPage, int categoryId) throws DAOException {

		ResultSet resultSet = null;
		List<Product> products = Collections.emptyList();
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_PRODUCTS_BY_CATEGORY_ID + SQLQueriesStorage.LIMIT_OFFSET_STATEMENT)) {
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setInt(2, recordsPerPage);
			preparedStatement.setInt(3, (currentPage - 1) * recordsPerPage);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				logger.info("Products is empty");
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				products = new ArrayList<>(listSize);
				while (resultSet.next()) {
					products.add(constructProductByResultSet(resultSet));
				}
			}
			
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Products List finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
		return products;
		
	}
	
	@Override
	public List<ProductCategory> findAllProductCategories() throws DAOException {
		
		List<ProductCategory> categories;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_PRODUCT_CATEGORIES);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			if (!resultSet.isBeforeFirst()) {
				logger.info("Categories is empty");
				categories = Collections.emptyList();
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				categories = new ArrayList<>(listSize);
				while (resultSet.next()) {
					categories.add(constructProductCategoryByResultSet(resultSet));
				}
			}
			
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Categories List finding error", e);
			throw new DAOException("service.commonError", e);
		}
		
		return categories;
		
	}

	@Override
	public int findProductsCount() throws DAOException {
		
		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_PRODUCTS_COUNT)) {
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Products count finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}

	}
	
	@Override
	public int findProductsCountByCategoryId(int categoryId) throws DAOException {

		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_PRODUCTS_COUNT_BY_CATEGORY_ID)) {
			preparedStatement.setInt(1, categoryId);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("Products count finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
	}
	
}