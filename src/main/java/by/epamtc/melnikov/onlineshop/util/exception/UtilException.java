package by.epamtc.melnikov.onlineshop.util.exception;

public class UtilException extends Exception {

	private static final long serialVersionUID = -335301940789510133L;

	public UtilException() {
		super();
	}

	public UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtilException(String message) {
		super(message);
	}

	public UtilException(Throwable cause) {
		super(cause);
	}
	
}
