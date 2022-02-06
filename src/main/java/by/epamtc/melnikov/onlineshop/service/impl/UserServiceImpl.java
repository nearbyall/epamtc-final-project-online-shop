package by.epamtc.melnikov.onlineshop.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;
import by.epamtc.melnikov.onlineshop.controller.command.CommandHolder;
import by.epamtc.melnikov.onlineshop.dao.DAOProvider;
import by.epamtc.melnikov.onlineshop.dao.UserDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.service.UserService;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;
import by.epamtc.melnikov.onlineshop.service.validation.UserValidator;
import by.epamtc.melnikov.onlineshop.service.validation.exception.ValidatorException;
import by.epamtc.melnikov.onlineshop.util.hash.HashGenerator;
import by.epamtc.melnikov.onlineshop.util.email.EmailDistributor;
import by.epamtc.melnikov.onlineshop.util.email.EmailMessageLocalizationDispatcher;
import by.epamtc.melnikov.onlineshop.util.email.EmailMessageType;
import by.epamtc.melnikov.onlineshop.util.exception.UtilException;

/**
 * {@link UserService} interface implementation.
 * 
 * @author nearbyall
 *
 */
public class UserServiceImpl implements UserService {

	private static final int TOKEN_VALUE_COOKIE_INDEX = 0;
	private static final int USER_ID_COOKIE_INDEX = 1;
	
	private final static Logger logger = LogManager.getLogger();
	
	private final UserDAO userDAO;
	private final UserValidator validator;
	private final HashGenerator hashGenerator;
	private final EmailDistributor emailDistributor;
	private final EmailMessageLocalizationDispatcher emailLocalizationDispatcher;
	
	public UserServiceImpl() {
		userDAO = DAOProvider.getInstance().getUserDAO();
		validator = new UserValidator();
		hashGenerator = new HashGenerator();
		emailDistributor = new EmailDistributor();
		emailLocalizationDispatcher = new EmailMessageLocalizationDispatcher();
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
		
		nullifyUserPassword(user);
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
				if (user.isBanned()) {
					throw new ServiceException("validation.user.login.isBanned");
				}
				nullifyUserPassword(user);
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
	public User logInByToken(String token) throws ServiceException {
		
		if (token == null) {
			logger.warn("token is null");
			throw new ServiceException("service.commonError");
		}
		
		try {
			String[] tokenComponents = token.split(AttributeNameStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
			int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
			String tokenValue = tokenComponents[TOKEN_VALUE_COOKIE_INDEX];
			User user = userDAO.findUserByIdAndToken(userId, tokenValue);
			if (user != null) {
				if (user.isBanned()) {
					throw new ServiceException("validation.user.login.isBanned");
				}
				nullifyUserPassword(user);
				return user;
			}
			logger.warn(String.format("Cant use token %s for log in", token));
			throw new ServiceException("service.commonError");
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void sendLogInTokenIfForgetPassword(String email, String pageRootURL) throws ServiceException {
		
		if (StringUtils.isAnyBlank(email, pageRootURL)) {
			throw new ServiceException("service.commonError");
		}
        
		try {
			User user = userDAO.findUserByEmail(email);
			String token = findUpdatedUserRememberToken(user.getId());
			String userLogInLink = constructLogInLink(CommandHolder.FORGET_PASSWORD_LOG_IN.getCommandName(), pageRootURL, token);
			String messageTitle = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.TITLE_FORGET_PASSWORD);
			String messageText = emailLocalizationDispatcher.getLocalizedMessage(EmailMessageType.MESSAGE_FORGET_PASSWORD, userLogInLink);
			emailDistributor.addEmailToSendingQueue(messageTitle, messageText, email);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (UtilException e) {
			throw new ServiceException("service.commonError", e);
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
			nullifyUserPassword(user);
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
				nullifyUsersPassword(userList);
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


	@Override
	public User updateUserBalance(User user) throws ServiceException {
	
		if (user == null) {
			logger.warn("User is null");
			throw new ServiceException("service.commonError");
		}
		
		try {
			userDAO.updateUserBalance(user);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return user;
		
	}


	@Override
	public String findUpdatedUserRememberToken(int userId) throws ServiceException {
		String token = UUID.randomUUID().toString();
		try {
			userDAO.updateUserRememberToken(userId, token);
			return token + AttributeNameStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER + userId;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}


	@Override
	public int deleteUserRememberToken(int userId) throws ServiceException {
		try {
			userDAO.deleteUserRememberToken(userId);
			return userId;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	/**
	 * Constructs link for log in process by token.
	 * 
	 * @param commandName log in command in {@link CommandHolder}
	 * @param pageRootUrl root URL
	 * @param token single-use token for authorize
	 * @return new log in link
	 */
	private String constructLogInLink(String commandName, String pageRootUrl, String token) {
		return pageRootUrl + '?' +AttributeNameStorage.COMMAND + '=' + commandName
				+ '&' + AttributeNameStorage.COOKIE_REMEMBER_USER_TOKEN + '=' + token;
	}
	
	/**
	 * Nullifies the password of the {@link User}
	 * 
	 * @param user
	 */
	private void nullifyUserPassword(User user) {
		user.setEncryptedPassword(StringUtils.EMPTY);
	}
	
	/**
	 * Resets the password of each user from {@link List} of {@link User}s.
	 * 
	 * @param users
	 */
	private void nullifyUsersPassword(List<User> users) {
		for (User user : users) {
			nullifyUserPassword(user);
		}
	}

}