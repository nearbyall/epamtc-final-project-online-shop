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

public class ProductServiceImpl implements ProductService {

	private final static Logger logger = LogManager.getLogger(ProductServiceImpl.class);
	
	private final ProductDAO productDAO;
	private final ProductValidator validator;
	
	public ProductServiceImpl() {
		productDAO = DAOProvider.getInstance().getProductDAO();
		validator = new ProductValidator();
	}
	
	@Override
	public Product addProduct(Product product) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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
	public List<ProductCategory> findAllProductCategories() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}