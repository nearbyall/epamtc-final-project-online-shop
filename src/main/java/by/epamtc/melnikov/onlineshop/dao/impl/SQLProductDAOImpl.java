package by.epamtc.melnikov.onlineshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

public class SQLProductDAOImpl extends SQLBaseDAO implements ProductDAO {

	private final static Logger logger = LogManager.getLogger();
	
	/** Field responsible for the uniqueness of {@link ProductCategory}'s name in the database */
    private static final String UNIQUE_NAME_MESSAGE = "product_categories.name_UNIQUE";
    /** Field responsible for the uniqueness of {@link ProductCategory}'s imgPath in the database */
    private static final String UNIQUE_IMG_PATH_MESSAGE = "product_categories.imgPath_UNIQUE";
	
	@Override
	public Product addProduct(Product product) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductCategory addProductCategory(ProductCategory category) throws DAOException {
		
		try(Connection connection = pool.getConnection();
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
		
		
		
		return null;
	}

}
