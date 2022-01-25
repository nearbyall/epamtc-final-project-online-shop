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
	
	/** Field responsible for the uniqueness of {@link ProductCategory}'s name in the database */
    private static final String UNIQUE_NAME_MESSAGE = "product_categories.name_UNIQUE";
    /** Field responsible for the uniqueness of {@link ProductCategory}'s imgPath in the database */
    private static final String UNIQUE_IMG_PATH_MESSAGE = "product_categories.imgPath_UNIQUE";
	
    /** Field contains the column name of {@link ProductCategory}'s name*/
    private static final String PRODUCT_CATEGORY_NAME_COLUMN_NAME = "product_categories.name";
    /** Field contains the column name of {@link ProductCategory}'s imgPath*/
    private static final String PRODUCT_CATEGORY_IMG_PATH_COLUMN_NAME = "product_categories.imgPath";
    
	@Override
	public Product addProduct(Product product) throws DAOException {
		// TODO Auto-generated method stub
		return null;
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
				throw new DAOException("query.product.category.addition.nameAlreadyExist", e);
			}
			if (e.getMessage().contains(UNIQUE_IMG_PATH_MESSAGE)) {
				throw new DAOException("query.product.category.addition.imgPathAlreadyExist", e);
			}
			logger.warn(String.format("ProductCategory %s addition common error", category), e);
			throw new DAOException("query.product.category.addition.commonError", e);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("ProductCategory %s addition common error", category), e);
			throw new DAOException("query.product.category.addition.commonError", e);
		}
		
		return category;
		
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
		category.setName(resultSet.getString(PRODUCT_CATEGORY_NAME_COLUMN_NAME));
		category.setImgPath(resultSet.getString(PRODUCT_CATEGORY_IMG_PATH_COLUMN_NAME));
		return category;
	}
	
}
