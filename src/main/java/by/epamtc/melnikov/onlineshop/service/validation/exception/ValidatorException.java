package by.epamtc.melnikov.onlineshop.service.validation.exception;

/**
 * A class that inherits from {@link Exception}.
 * Describes an exception that can throws in validation package.
 * 
 * @author nearbyall
 *
 */
public class ValidatorException extends Exception {

	private static final long serialVersionUID = -5222852599955752746L;

	public ValidatorException() {
		super();
	}

	public ValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}

}
