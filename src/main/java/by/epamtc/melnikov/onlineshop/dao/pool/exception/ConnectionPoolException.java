package by.epamtc.melnikov.onlineshop.dao.pool.exception;

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
