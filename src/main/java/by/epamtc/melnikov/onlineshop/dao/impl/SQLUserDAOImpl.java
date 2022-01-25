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
public class SQLUserDAOImpl extends SQLBaseDAO implements UserDAO{

	private final static Logger logger = LogManager.getLogger();
	
	/** Field responsible for the uniqueness of {@link User}'s email in the database */
    private static final String UNIQUE_EMAIL_MESSAGE = "users.email_UNIQUE";
    /** Field responsible for the uniqueness of {@link User}'s mobile in the database */
    private static final String UNIQUE_MOBILE_MESSAGE = "users.mobile_UNIQUE";
    
    /** Field contains the column name of {@link User}'s id*/
    private static final String USER_ID_COLUMN_NAME = "users.id";
    /** Field contains the column name of {@link User}'s role*/
    private static final String USER_ROLE_ID_COLUMN_NAME = "users.roleId";
    /** Field contains the column name of {@link User}'s email*/
    private static final String USER_EMAIL_COLUMN_NAME = "users.email";
    /** Field contains the column name of {@link User}'s passwordEncrypted*/
    private static final String USER_PASSWORD_COLUMN_NAME = "users.passwordEncrypted";
    /** Field contains the column name of {@link User}'s name*/
    private static final String USER_NAME_COLUMN_NAME = "users.name";
    /** Field contains the column name of {@link User}'s surname*/
    private static final String USER_SURNAME_COLUMN_NAME = "users.surname";
    /** Field contains the column name of {@link User}'s mobile*/
    private static final String USER_MOBILE_COLUMN_NAME = "users.mobile";
    /** Field contains the column name of {@link User}'s status*/
    private static final String USER_STATUS_ID_COLUMN_NAME = "users.statusId";
    /** Field contains the column name of {@link User}'s registeredAt*/
    private static final String USER_REGISTERED_AT_COLUMN_NAME = "users.registeredAt";
    /** Field contains the column name of {@link User}'s lastLoginAt*/
    private static final String USER_LAST_LOGIN_AT_COLUMN_NAME = "users.lastLoginAt";
	
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
			preparedStatement.setBigDecimal(9, new BigDecimal(user.getRole().getId()));
			preparedStatement.setBigDecimal(10, new BigDecimal(user.getStatus().getId()));
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
		
		return null;
	}
	
	@Override
	public User updateUserBanStatus(User user) throws DAOException {
		
		try (Connection connection = pool.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_BAN_STATUS)) {
			 preparedStatement.setBigDecimal(1, new BigDecimal(user.getStatus().getId()));
			 preparedStatement.setBigDecimal(2, new BigDecimal(user.getId()));
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
			preparedStatement.setBigDecimal(2, new BigDecimal(user.getId()));
			preparedStatement.executeUpdate();
		} catch(SQLException | ConnectionPoolException e) {
			logger.warn(String.format("User %s balance update error", user), e);
			throw new DAOException("service.commonError", e);
		}
		
		return user;
		
	}
	
	/**
	 * Extracts founded {@link User} from <tt>resultSet</tt>.
	 * Throws SQLException and DAOException
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link User}
	 * @return an {@link User} which has been extracted
	 * @throws SQLException @see {@link SQLUserDAOImpl#constructUserByResultSet(ResultSet)}
	 * @throws DAOException if {@link User}
	 */
	private User extractFoundedUserFromResultSet(ResultSet resultSet) throws SQLException, DAOException {
		if (resultSet.next()) {
			return constructUserByResultSet(resultSet);
		} else {
			throw new DAOException("query.user.getUser.userNotFound");
		}
	}
    
	/**
	 * Constructs user from <tt>resultSet</tt>.
	 * Throws SQLException if the column label is not valid
	 * 
	 * @param resultSet {@link ResultSet} which includes {@link User}
	 * @return an {@link User} which has been constructed
	 * @throws SQLException if the column label is not valid
	 */
	private User constructUserByResultSet(ResultSet resultSet) throws SQLException {
		return new UserBuilder().withId(resultSet.getInt(USER_ID_COLUMN_NAME))
				.withUserType(UserType.getTypeById(resultSet.getInt(USER_ROLE_ID_COLUMN_NAME)))
				.withEmail(resultSet.getString(USER_EMAIL_COLUMN_NAME))
				.withPasswordEncrypted(resultSet.getString(USER_PASSWORD_COLUMN_NAME))
				.withName(resultSet.getNString(USER_NAME_COLUMN_NAME))
				.withSurname(resultSet.getNString(USER_SURNAME_COLUMN_NAME))
				.withMobile(resultSet.getString(USER_MOBILE_COLUMN_NAME))
				.withStatusType(StatusType.getTypeById(resultSet.getInt(USER_STATUS_ID_COLUMN_NAME)))
				.withRegisteredAt(resultSet.getTimestamp(USER_REGISTERED_AT_COLUMN_NAME))
				.withLastLoginAt(resultSet.getTimestamp(USER_LAST_LOGIN_AT_COLUMN_NAME))
				.build();
	}

}
