package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

public interface UserDAO {

	User registration(User user) throws DAOException;
	
	User findUserByEmail(String email) throws DAOException;
	
	List<User> findAllUsers() throws DAOException;
	
	User updateProfileUserData(User user) throws DAOException;
	
	User updateUserBanStatus(User user) throws DAOException;
	
}
