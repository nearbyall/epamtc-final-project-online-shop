package by.epamtc.melnikov.onlineshop.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.dao.exception.DAOException;
import by.epamtc.melnikov.onlineshop.dao.pool.ConnectionPool;

public abstract class SQLBaseDAO {
	
	private static final Logger logger = LogManager.getLogger(SQLBaseDAO.class);

	protected final ConnectionPool pool;

	protected SQLBaseDAO(){
		pool = ConnectionPool.getInstance();
	}

	protected void closeResultSet(ResultSet resultSet) throws DAOException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.error("Can`t close resultSet", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void closeConnection(Connection connection) throws DAOException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Can`t close connection", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void connectionCommitChanges(Connection connection) throws DAOException {
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				logger.error("Can`t commit connection's changes", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void connectionSetAutoCommit(Connection connection, boolean value) throws DAOException {
		if (connection != null) {
			try {
				connection.setAutoCommit(value);
			} catch (SQLException e) {
				logger.error("Can`t set autocommmit to connection", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}

	protected void connectionsRollback(Connection connection) throws DAOException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				logger.error("Can`t rollback connection result", e);
				throw new DAOException("service.commonError", e);
			}
		}
	}
    
	private String injectionProtection(String value) {
		return !StringUtils.isBlank(value) ? value.replace("'", "\\'" ) : value;
	}

}
