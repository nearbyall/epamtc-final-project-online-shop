package by.epamtc.melnikov.onlineshop.service.exception;

/**
 * A class that inherits from {@link Exception}.
 * Describes an exception that can throws in Service layer.
 * 
 * @author nearbyall
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -1837669697625386831L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
