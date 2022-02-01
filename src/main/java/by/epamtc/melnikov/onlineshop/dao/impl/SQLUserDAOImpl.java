package by.epamtc.melnikov.onlineshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLBaseDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLQueriesStorage;
import by.epamtc.melnikov.onlineshop.dao.UserDAO;

/**
 * SQL {@link UserDAO} interface implementation.
 * 
 * @author nearbyall
 *
 */
public class SQLUserDAOImpl extends SQLBaseDAO implements UserDAO {

	private final static Logger logger = LogManager.getLogger();
	
	@Override
	public User registration(User user) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.REGISTER_USER)) {
			
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getMobile());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getEncryptedPassword());
			preparedStatement.setTimestamp(6, user.getRegisteredAt());
			preparedStatement.setTimestamp(7, user.getLastLoginAt());
			preparedStatement.setDouble(8, user.getBalance());
			preparedStatement.setInt(9, user.getRole().getId());
			preparedStatement.setInt(10, user.getStatus().getId());
			preparedStatement.executeUpdate();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().contains(UNIQUE_EMAIL_MESSAGE)) {
				throw new DAOException("query.user.registration.emailAlreadyExist", e);
			}
			if (e.getMessage().contains(UNIQUE_MOBILE_MESSAGE)) {
				throw new DAOException("query.user.registration.mobileAlreadyExist", e);
			}
			logger.warn(String.format("User %s registration common error", user), e);
			throw new DAOException("query.user.registration.commonError", e);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User %s registration error", user), e);
			throw new DAOException("query.user.registration.commonError", e);
		}
		
		return user;
		
	}

	@Override
	public User findUserByEmail(String email) throws DAOException {
		
		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_EMAIL)) {
			 preparedStatement.setString(1, email);
			 resultSet = preparedStatement.executeQuery();
			 return extractFoundedUserFromResultSet(resultSet);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User email: %s, finding error", email), e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
	}
	
	@Override
	public User findUserByIdAndToken(int userId, String token) throws DAOException {
		
		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_ID_AND_LOG_IN_TOKEN)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, token);
			resultSet = preparedStatement.executeQuery();
			return extractFoundedUserFromResultSet(resultSet);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User userId: %d, token %s finding error", userId, token), e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}
		
	}
	
	@Override
	public List<User> findAllUsers() throws DAOException {
		
		List<User> users;
		
		try(Connection connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_USERS);
			ResultSet resultSet = preparedStatement.executeQuery()) {
			
			if (!resultSet.isBeforeFirst()) {
				users = Collections.emptyList();
			} else {
				resultSet.last();
				int listSize = resultSet.getRow();
				resultSet.beforeFirst();
				users = new ArrayList<>(listSize);
				while (resultSet.next()) {
					users.add(constructUserByResultSet(resultSet));
				}
			}
			
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("User list finding error", e);
			throw new DAOException("service.commonError", e);
		}
		
		return users;
		
	}
	
	@Override
	public User updateProfileUserData(User user) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_PROFILE_DATA)) {
			 preparedStatement.setString(1, user.getEncryptedPassword());
			 preparedStatement.setString(2, user.getName());
			 preparedStatement.setString(3, user.getSurname());
			 preparedStatement.setString(4, user.getMobile());
			 preparedStatement.setInt(5, user.getId());
			 preparedStatement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().contains(UNIQUE_MOBILE_MESSAGE)) {
				throw new DAOException("query.user.registration.mobileAlreadyExist", e);
			}
			logger.warn(String.format("User %s updating common error", user), e);
			throw new DAOException("query.user.updating.commonError", e);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User %s profile update error", user), e);
			throw new DAOException("service.commonError", e);
		}
		
		return user;
		
	}
	
	@Override
	public User updateUserBanStatus(User user) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_BAN_STATUS)) {
			 preparedStatement.setInt(1, user.getStatus().getId());
			 preparedStatement.setInt(2, user.getId());
			 preparedStatement.executeUpdate();
		} catch(SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User %s ban status update error", user), e);
			throw new DAOException("service.commonError", e);
		}
		
		return user;
		
	}
	
	@Override
	public User updateUserBalance(User user) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_BALANCE)) {
			preparedStatement.setDouble(1, user.getBalance());
			preparedStatement.setInt(2, user.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User %s balance update error", user), e);
			throw new DAOException("service.commonError", e);
		}
		
		return user;
		
	}

	@Override
	public String updateUserRememberToken(int userId, String token) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN)) {
			preparedStatement.setString(1, token);
			preparedStatement.setInt(2, userId);
			preparedStatement.executeUpdate();
		} catch(SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User %s token update error", token), e);
			throw new DAOException("service.commonError", e);
		}
		
		return token;
		
	}
	
	@Override
	public double findUserBalance(int userId) throws DAOException {
		
		ResultSet resultSet = null;
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BALANCE_BY_ID)) {
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getDouble(1);
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn("User balance finding error", e);
			throw new DAOException("service.commonError", e);
		} finally {
			closeResultSet(resultSet);
		}

	}

	@Override
	public int deleteUserRememberToken(int userId) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN_TO_NULL)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User id: %d, token delete error", userId), e);
			throw new DAOException("service.commonError", e);
		}
		
		return userId;
		
	}
	
}