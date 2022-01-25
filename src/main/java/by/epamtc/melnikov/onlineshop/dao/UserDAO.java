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
	 * @return an {@link User}
	 * @throws DAOException if an error occurs while getting a <tt>user</tt>
	 */
	User findUserByEmail(String email) throws DAOException;
	
	/**
	 * Retrieves and returns {@link List} of {@link User}s into data source.
	 * If no such users contains into data source returns empty {@link List} collection.
	 * 
	 * @return {@link List} of {@link User}s
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
	 * @param user {@link User} that information is updating
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
	
}