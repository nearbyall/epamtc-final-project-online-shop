package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

public interface ProductService {

	Product addProduct(Product product) throws ServiceException;
	
	ProductCategory addProductCategory(ProductCategory category) throws ServiceException;
	
	List<ProductCategory> findAllProductCategories() throws ServiceException;
	
}
