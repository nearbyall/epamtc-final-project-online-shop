package by.epamtc.melnikov.onlineshop.dao.impl;

import java.math.BigDecimal;
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
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.dao.BaseDAO;
import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.exception.ConnectionPoolException;
import by.epamtc.melnikov.onlineshop.dao.sql.SQLQueriesStorage;
import by.epamtc.melnikov.onlineshop.dao.UserDAO;

public class SQLUserDAOImpl extends BaseDAO implements UserDAO{

	private final static Logger logger = LogManager.getLogger(SQLUserDAOImpl.class);
	
    private static final String UNIQUE_EMAIL_MESSAGE = "users.email_UNIQUE";
    private static final String UNIQUE_MOBILE_MESSAGE = "users.mobile_UNIQUE";
	
	@Override
	public User registration(User user) throws DAOException {
		
		try (Connection connection = pool.getConnection()) {
			
			PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.REGISTER_USER);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getMobile());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getEncryptedPassword());
			preparedStatement.setTimestamp(6, user.getRegisteredAt());
			preparedStatement.setTimestamp(7, user.getLastLoginAt());
			preparedStatement.setDouble(8, user.getBalance());
			preparedStatement.setBigDecimal(9, new BigDecimal(user.getRole().getId()));
			preparedStatement.setBigDecimal(10, new BigDecimal(user.getStatus().getId()));
			preparedStatement.executeUpdate();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().contains(UNIQUE_EMAIL_MESSAGE)) {
                throw new DAOException("query.user.registration.emailAlreadyExist", e);
            }
			//TODO query mobile already exist
			if (e.getMessage().contains(UNIQUE_MOBILE_MESSAGE)) {
                throw new DAOException("query.user.registration.emailAlreadyExist", e);
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
            //throw new DAOException("query.user.registration.emailAlreadyExist", e);
			//mobile is already exists
        } catch (SQLException | ConnectionPoolException e) {
            logger.warn(String.format("User %s profile update error", user), e);
            throw new DAOException("service.commonError", e);
        }
		
		return null;
	}
	
    private User extractFoundedUserFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
        if (resultSet.next()) {
            return constructUserByResultSet(resultSet);
        } else {
            throw new DAOException("query.user.getUser.userNotFound");
        }
    }
    
    private User constructUserByResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder().withId(resultSet.getInt("users.id"))
                .withUserType(UserType.getTypeById(resultSet.getInt("users.roleId")))
                .withEmail(resultSet.getString("users.email"))
                .withPasswordEncrypted(resultSet.getString("users.passwordEncrypted"))
                .withName(resultSet.getNString("users.name"))
                .withSurname(resultSet.getNString("users.surname"))
                .withMobile(resultSet.getString("users.mobile"))
                .withStatusType(StatusType.getTypeById(resultSet.getInt("users.statusId")))
                .withRegisteredAt(resultSet.getTimestamp("users.registeredAt"))
                .withLastLoginAt(resultSet.getTimestamp("users.lastLoginAt"))
                .build();
    }

}
