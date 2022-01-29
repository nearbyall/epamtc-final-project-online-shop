package by.epamtc.melnikov.onlineshop.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.ProductDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.ProductService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;
import by.epamtc.melnikov.onlineshop.service.validation.ProductValidator;
import by.epamtc.melnikov.onlineshop.service.validation.exception.ValidatorException;

/**
 * {@link ProductService} interface implementation.
 * 
 * @author nearbyall
 *
 */
public class ProductServiceImpl implements ProductService {

	private final static Logger logger = LogManager.getLogger();
	
	private final ProductDAO productDAO;
	private final ProductValidator validator;
	
	public ProductServiceImpl() {
		productDAO = DAOProvider.getInstance().getProductDAO();
		validator = new ProductValidator();
	}
	
	@Override
	public Product addProduct(Product product) throws ServiceException {
		
		try {
			validator.validateProduct(product);
		} catch (ValidatorException e) {
			logger.info(String.format("invalid %s %n product data %s", product, e.getMessage()));
			throw new ServiceException(e.getMessage(), e);
		}
		
		try {
			productDAO.addProduct(product);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return product;
		
	}

	@Override
	public Product findProductById(int id) throws ServiceException {
		
		//TODO validate id
		
		try {
			return productDAO.findProductById(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}
	
	@Override
	public ProductCategory addProductCategory(ProductCategory category) throws ServiceException {
		
		try {
			validator.validateProductCategory(category);
		} catch (ValidatorException e) {
			logger.info(String.format("invalid %s %n category data %s", category, e.getMessage()));
			throw new ServiceException(e.getMessage(), e);
		}
		
		try {
			productDAO.addProductCategory(category);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return category;
		
	}

	@Override
	public List<Product> findAllProductsPerPage(int currentPage, int recordsPerPage) throws ServiceException {
		
		if (currentPage < 1 || recordsPerPage < 1) {
			throw new ServiceException("validation.pagination");
		}
		
		List<Product> products;
		
		try {
			products = productDAO.findAllProductsPerPage(currentPage, recordsPerPage);
			if (products.isEmpty()) {
				throw new ServiceException("query.product.getProducts.productsNotFound");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return products;
		
	}
	
	@Override
	public List<Product> findAllProductsByCategoryIdPerPage(int currentPage, int recordsPerPage, int categoryId) throws ServiceException {
		
		if (currentPage < 1 || recordsPerPage < 1) {
			throw new ServiceException("validation.pagination");
		}
		
		List<Product> products;
		
		try {
			products = productDAO.findAllProductsByCategoryIdPerPage(currentPage, recordsPerPage, categoryId);
			if (products.isEmpty()) {
				throw new ServiceException("query.product.getProducts.productsNotFound");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return products;
		
	}
	
	@Override
	public List<ProductCategory> findAllProductCategories() throws ServiceException {
		
		List<ProductCategory> categories;
		
		try {
			categories = productDAO.findAllProductCategories();
			if (categories.isEmpty()) {
				throw new ServiceException("query.product.category.getCategories.categoriesNotFound");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return categories;
		
	}
	
	@Override
	public int findProductsCount() throws ServiceException {
		
		try {
			return productDAO.findProductsCount();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}

	@Override
	public int findProductsCountByCategoryId(int categoryId) throws ServiceException {
		
		try {
			return productDAO.findProductsCountByCategoryId(categoryId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}

}
