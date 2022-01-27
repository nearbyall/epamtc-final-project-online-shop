package by.epamtc.melnikov.onlineshop.dao.impl;

import java.math.BigDecimal;
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
import by.epamtc.melnikov.onlineshop.bean.builder.ProductBuilder;
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
	
	/** Field responsible for the uniqueness of {@link Product}'s title in the database */
	private static final String UNIQUE_PRODUCT_TITLE_MESSAGE = "products.title_UNIQUE";
	/** Field responsible for the uniqueness of {@link Product}'s imgPath in the database */
	private static final String UNIQUE_PRODUCT_IMG_PATH_MESSAGE = "products.imgPath_UNIQUE";
	/** Field responsible for the uniqueness of {@link ProductCategory}'s name in the database */
	private static final String UNIQUE_NAME_MESSAGE = "product_categories.name_UNIQUE";
	/** Field responsible for the uniqueness of {@link ProductCategory}'s imgPath in the database */
	private static final String UNIQUE_IMG_PATH_MESSAGE = "product_categories.imgPath_UNIQUE";
	
	/** Field contains the column name of {@link ProductCategory}'s id*/
	private static final String PRODUCT_CATEGORY_ID_COLUMN_NAME = "product_categories.id";
	/** Field contains the column name of {@link ProductCategory}'s name*/
	private static final String PRODUCT_CATEGORY_NAME_COLUMN_NAME = "product_categories.name";
	/** Field contains the column name of {@link ProductCategory}'s imgPath*/
	private static final String PRODUCT_CATEGORY_IMG_PATH_COLUMN_NAME = "product_categories.imgPath";
    
	/** Field contains the column name of {@link Product}'s id*/
	private static final String PRODUCT_ID_COLUMN_NAME = "products.id";
	/** Field contains the column name of {@link Product}'s imgPath*/
	private static final String PRODUCT_IMG_PATH_COLUMN_NAME = "products.imgPath";
	/** Field contains the column name of {@link Product}'s title*/
	private static final String PRODUCT_TITLE_COLUMN_NAME = "products.title";
	/** Field contains the column name of {@link Product}'s description*/
	private static final String PRODUCT_DESCRIPTION_COLUMN_NAME = "products.description";
	/** Field contains the column name of {@link Product}'s updatedAt*/
	private static final String PRODUCT_UPDATED_AT_COLUMN_NAME = "products.updatedAt";
	/** Field contains the column name of {@link Product}'s createdAt*/
	private static final String PRODUCT_CREATED_AT_COLUMN_NAME = "products.createdAt";
	/** Field contains the column name of {@link Product}'s count*/
	private static final String PRODUCT_COUNT_COLUMN_NAME = "products.count";
	/** Field contains the column name of {@link Product}'s price*/
	private static final String PRODUCT_PRICE_COLUMN_NAME = "products.price";
    
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
			preparedStatement.setBigDecimal(8, new BigDecimal(product.getCategory().getId()));
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
				products = Collections.emptyList();
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
	
	/**
	 * Constructs {@link ProductCategory} from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link ProductCategory}
	 * @return an {@link ProductCategory} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	private ProductCategory constructProductCategoryByResultSet(ResultSet resultSet) throws SQLException {
		ProductCategory category = new ProductCategory();
		category.setId(resultSet.getInt(PRODUCT_CATEGORY_ID_COLUMN_NAME));
		category.setName(resultSet.getString(PRODUCT_CATEGORY_NAME_COLUMN_NAME));
		category.setImgPath(resultSet.getString(PRODUCT_CATEGORY_IMG_PATH_COLUMN_NAME));
		return category;
	}
    
	/**
	 * Constructs {@link Product} from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link Product}
	 * @return an {@link Product} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	private Product constructProductByResultSet(ResultSet resultSet) throws SQLException {
		
		return new ProductBuilder()
				.withId(resultSet.getInt(PRODUCT_ID_COLUMN_NAME))
				.withTitle(resultSet.getString(PRODUCT_TITLE_COLUMN_NAME))
				.withPrice(resultSet.getDouble(PRODUCT_PRICE_COLUMN_NAME))
				.withCount(resultSet.getInt(PRODUCT_COUNT_COLUMN_NAME))
				.withDescription(resultSet.getString(PRODUCT_DESCRIPTION_COLUMN_NAME))
				.withImgPath(resultSet.getString(PRODUCT_IMG_PATH_COLUMN_NAME))
				.withCreatedAt(resultSet.getTimestamp(PRODUCT_CREATED_AT_COLUMN_NAME))
				.withUpdatedAt(resultSet.getTimestamp(PRODUCT_UPDATED_AT_COLUMN_NAME))
				.withCategory(constructProductCategoryByResultSet(resultSet))
				.build();
		
	}
	
}