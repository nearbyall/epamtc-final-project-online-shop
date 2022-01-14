package by.epamtc.melnikov.onlineshop.dao;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

public interface ProductDAO {

	Product addProduct(Product product) throws DAOException;
	
}
