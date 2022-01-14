package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

public interface UserService {

	User registration(User user) throws ServiceException;
	
	User logInByPassword(String email, String password) throws ServiceException;
	
	User findUserByEmail(String email) throws ServiceException;
	
	List<User> findAllUsers() throws ServiceException;
	
	User updateUserProfileData(User user) throws ServiceException;
	
}
