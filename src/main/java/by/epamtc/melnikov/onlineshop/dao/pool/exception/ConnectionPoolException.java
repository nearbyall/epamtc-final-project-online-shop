package by.epamtc.melnikov.onlineshop.dao.pool.exception;

/**
 * A class that inherits from {@link Exception}.
 * Describes an exception that can throws in connection package.
 * 
 * @author nearbyall
 *
 */
public class ConnectionPoolException extends Exception {

	private static final long serialVersionUID = 8400829999798804390L;

	public ConnectionPoolException() { }

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}

}