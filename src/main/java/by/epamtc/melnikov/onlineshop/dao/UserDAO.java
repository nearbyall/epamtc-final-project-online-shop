package by.epamtc.melnikov.onlineshop.dao;

import java.util.List;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;

/**
 * Interface describes the opportunity that data source provide to store
 * and restore {@link User} bean.
 * 
 * @author nearbyall
 *
 */
public interface UserDAO {

	/**
	 * Saves the <tt>user</tt> into data source. Throws DAOException
	 * if an error occurs while writing a <tt>user</tt>.
	 * 
	 * @param user the {@link User} that should added to data source
	 * @return an {@link User} which has been registered
	 * @throws DAOException if an error occurs while writing a <tt>user</tt>
	 */
	User registration(User user) throws DAOException;
	
	/**
	 * Retrieves and returns {@link User}.
	 * If no such <tt>user</tt> contains into data source throws DAOException.
	 * 
	 * @param email {@link User}'s email
	 * @return an {@link User} which has been found
	 * @throws DAOException if an error occurs while getting a <tt>user</tt>
	 */
	User findUserByEmail(String email) throws DAOException;
	
	/**
	 * Retrieves and returns {@link User}.
	 * If no such <tt>user</tt> contains into data source throws DAOException.
	 * 
	 * @param userId {@link User}'s id
	 * @param token unique {@link User}'s token
	 * @return {@link User} which has been found
	 * @throws DAOException
	 */
	User findUserByIdAndToken(int userId, String token) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link User}s into data source.
	 * If no such users contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link User}s which has been found
	 * @throws DAOException if an error occurs while getting a <tt>user</tt>
	 */
	List<User> findAllUsers() throws DAOException;
	
	/**
	 * Updates common information about {@link User} in a data source.
	 * Throws DAOException if an error occurs while writing a <tt>user</tt>.
	 * 
	 * @param user {@link User} that information is updating
	 * @return an {@link User} which has been updated
	 * @throws DAOException if an error occurs while writing a <tt>user</tt>
	 */
	User updateProfileUserData(User user) throws DAOException;
	
	/**
	 * Updates ban status of {@link User} in a data source.
	 * Throws DAOException if an error occurs while writing a <tt>user</tt>.
	 * 
	 * @param user {@link User} that ban status is updating
	 * @return an {@link User} which has been updated
	 * @throws DAOException if an error occurs while writing a <tt>user</tt>
	 */
	User updateUserBanStatus(User user) throws DAOException;
	
	/**
	 * Update balance of {@link User} in data source.
	 * Throws DAOException if an error occurs while writing a <tt>user</tt>.
	 * 
	 * @param user {@link User} that balance is updating
	 * @return an {@link User} which has been updated
	 * @throws DAOException if an error occurs while writing a <tt>user</tt>
	 */
	User updateUserBalance(User user) throws DAOException;
	
	/**
	 * Update log in token of {@link User} by id in data source.
	 * Throws DAOException if an error occurs while writing a <tt>token</tt>.
	 * 
	 * @param userId {@link User}'s id that token is updating
	 * @param token which should be updated
	 * @return new log in token
	 * @throws DAOException if an error occurs while writing a <tt>token</tt>
	 */
	String updateUserRememberToken(int userId, String token) throws DAOException;
	
	/**
	 * Retrieves and returns {@link User}'s balance by id. Throws DAOException
	 * if an error occurs while getting <tt>user</tt>
	 * 
	 * @param userId {@link User}'s id that balance is finding
	 * @return {@link User}'s balance
	 * @throws DAOException if an error occurs while getting a <tt>user</tt>
	 */
	double findUserBalance(int userId) throws DAOException;

	/**
	 * Removes {@link User}'s log in token by id. Throws DAOException
	 * if an error occurs while writing <tt>token</tt>
	 * 
	 * @param userId {@link User}'s id that token is removing
	 * @return {@link User}'s id that token is removed
	 * @throws DAOException if an error occurs while removing <tt>token</tt>
	 */
	int deleteUserRememberToken(int userId) throws DAOException;
	
}