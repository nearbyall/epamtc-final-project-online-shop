package by.epamtc.melnikov.onlineshop.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.UserDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;
import by.epamtc.melnikov.onlineshop.service.validation.UserValidator;
import by.epamtc.melnikov.onlineshop.service.validation.exception.ValidatorException;
import by.epamtc.melnikov.onlineshop.util.HashGenerator;
import by.epamtc.melnikov.onlineshop.util.exception.UtilException;

public class UserServiceImpl implements UserService {

	private final static Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	private final UserDAO userDAO;
	private final UserValidator validator;
	private final HashGenerator hashGenerator;
	
	public UserServiceImpl() {
		userDAO = DAOProvider.getInstance().getUserDAO();
		validator = new UserValidator();
		hashGenerator = new HashGenerator();
	}
	
	
	@Override
	public User registration(User user) throws ServiceException {
		
		try {
			validator.validateUser(user);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		try {
			user.setEncryptedPassword(hashGenerator.generateHash(user.getEncryptedPassword()));
			userDAO.registration(user);
		} catch (UtilException e) {
			logger.warn(e.getMessage(), e);
			throw new ServiceException("service.commonError", e);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
			
		return user;
		
	}

	@Override
	public User logInByPassword(String email, String password) throws ServiceException {
		
		if (StringUtils.isAnyBlank(email, password)) {
			logger.info("invalid input values");
			throw new ServiceException("service.commonError");
		}
		
		try {
			User user = userDAO.findUserByEmail(email);
			if (hashGenerator.validatePassword(password, user.getEncryptedPassword())) {
				/* check banned status
				if (user.getBanned()) {
					throw new LibraryServiceException("validation.user.login.isBanned");
				}
				 */
				return user;
			} else {
				throw new ServiceException("validation.user.login.incorrect");
			}
		} catch (UtilException e) {
			logger.warn(e.getMessage(), e);
			throw new ServiceException("service.commonError", e);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}
	
	@Override
	public User findUserByEmail(String email) throws ServiceException {
		
		if (email == null) {
			logger.warn("email is null");
			throw new ServiceException("service.commonError");
		}
		
		User user = null;
		
		try {
			user = userDAO.findUserByEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return user;
		
	}

	@Override
	public List<User> findAllUsers() throws ServiceException {
		
		try {
			List<User> userList = userDAO.findAllUsers();
			if(userList.isEmpty()) {
				throw new ServiceException("query.user.getUsers.usersNotFound");
			} else {
				return userList;
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}
	
	@Override
	public User updateUserProfileData(User user) throws ServiceException {
		
		if (user == null) {
			throw new ServiceException("service.commonError");
		}
		
		try {
			validator.validateUser(user);
		} catch (ValidatorException e) {
			logger.info(String.format("invalid %s %n update data %s", user, e.getMessage()));
			throw new ServiceException(e.getMessage(), e);
		}
		
		try {
			user.setEncryptedPassword(hashGenerator.generateHash(user.getEncryptedPassword()));
			userDAO.updateProfileUserData(user);
		} catch (UtilException e) {
			logger.warn(e.getMessage(), e);
			throw new ServiceException("service.commonError", e);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return user;
		
	}


	@Override
	public User updateUserBanStatus(User user) throws ServiceException {
		
		if (user == null) {
			logger.warn("User is null");
			throw new ServiceException("service.commonError");
		}
		try {
			userDAO.updateUserBanStatus(user);
			//TODO send mail
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		} 
		
		return user;
		
	}

}
