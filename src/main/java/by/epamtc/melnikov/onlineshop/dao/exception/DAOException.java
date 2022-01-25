package by.epamtc.melnikov.onlineshop.dao.exception;

/**
 * A class that inherits from {@link Exception}.
 * Describes an exception that can throws in DAO layer.
 * 
 * @author nearbyall
 *
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -8792996254079699193L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
