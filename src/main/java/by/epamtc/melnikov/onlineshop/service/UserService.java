package by.epamtc.melnikov.onlineshop.service;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The interface describes the opportunity of processing {@link User} data
 * received from the client and then sending it to the DAO layer to process
 * the incoming request.
 * 
 * @author nearbyall
 *
 */
public interface UserService {

	/**
	 * Gets a {@link User} as a parameter and registers it.
	 * 
	 * @param user {@link User} which should be registered
	 * @return {@link User} which has been registered
	 * @throws ServiceException if validation by {@link UserValidator} has not been passed,
	 * DAO layer throw their {@link DAOException} or HashGenerator throw their {@link UtilException}
	 * 
	 */
	User registration(User user) throws ServiceException;
	
	/**
	 * Gets <tt>email</tt> and <tt>password</tt> as a parameters and
	 * does log in process by password.
	 * 
	 * @param email the {@link User}'s field email
	 * @param password the {@link User}'s field encryptedPassword
	 * @return {@link User} which has been authorize
	 * @throws ServiceException if validation by {@link UserValidator} has not been passed,
	 * DAO layer throw their {@link DAOException} or HashGenerator throw their {@link UtilException}
	 */
	User logInByPassword(String email, String password) throws ServiceException;
	
	/**
	 * Gets <tt>token</tt> as a parameter and
	 * does log in process by log in token.
	 * 
	 * @param token which needed to log in
	 * @return {@link User} which has been authorize
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	User logInByToken(String token) throws ServiceException;
	
	/**
	 * Gets <tt>email</tt> as a parameter and does finding process.
	 * 
	 * @param email the {@link User}'s field email
	 * @return {@link User} which has been found
	 * @throws ServiceException if validation by {@link UserValidator} has not been passed or
	 * DAO layer throw their {@link DAOException}
	 */
	User findUserByEmail(String email) throws ServiceException;
	
	/**
	 * Finds all {@link User}s.
	 * 
	 * @return {@link List} of {@link User}s which has been found
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 */
	List<User> findAllUsers() throws ServiceException;
	
	/**
	 * Gets a {@link User} as a parameter and update their data.
	 * 
	 * @param user {@link User} which should be updated
	 * @return {@link User} which has been updated
	 * @throws ServiceException if validation by {@link UserValidator} has not been passed,
	 * DAO layer throw their {@link DAOException} or HashGenerator throw their {@link UtilException}
	 */
	User updateUserProfileData(User user) throws ServiceException;
	
	/**
	 * Gets a {@link User} as a parameter and updates their ban status.
	 * 
	 * @param user {@link User} that ban status is updating
	 * @return {@link User} which has been updated
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 * or <tt>user</tt> is null
	 */
	User updateUserBanStatus(User user) throws ServiceException;
	
	/**
	 * Gets a {@link User} as a parameter and updates their balance.
	 * 
	 * @param user {@link User} that balance is updating
	 * @return {@link User} which has been updated
	 * @throws ServiceException if DAO layer throw their {@link DAOException}
	 * or <tt>user</tt> is null
	 */
	User updateUserBalance(User user) throws ServiceException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	String findUpdatedUserRememberToken(int userId) throws ServiceException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	int deleteUserRememberToken(int userId) throws ServiceException;
	
	/**
	 * 
	 * @param email
	 * @param string
	 */
	void sendLogInTokenIfForgetPassword(String email, String string) throws ServiceException;
	
}