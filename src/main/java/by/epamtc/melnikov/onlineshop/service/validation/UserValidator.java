package by.epamtc.melnikov.onlineshop.service.validation;

import org.apache.commons.lang3.StringUtils;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.service.validation.exception.ValidatorException;

/**
 * The class needed to validate data associated with
 * the {@link User} using regular expressions.
 * 
 * @author nearbyall
 *
 */
public class UserValidator {

	private static final String PASSWORD_REGEX = "^[\\w-]{8,16}$";
	private static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,6}))$";
	private static final String PHONE_REGEX = "^[+]?[\\d]{7,15}$";
	private static final int MAX_EMAIL_FIELD_LENGTH = 50;

	public void validateUser(User user) throws ValidatorException {
    	
		if (user == null) {
			throw new ValidatorException("service.commonError");
		}
        
		validateMobile(user.getMobile());
		validateEmail(user.getEmail());
		validatePassword(user.getEncryptedPassword());
        
	}

	public void validateMobile(String mobile) throws ValidatorException {
		if (StringUtils.isBlank(mobile) || !mobile.matches(PHONE_REGEX)) {
			throw new ValidatorException("validation.user.registration.phone");
		}
	}

	public void validatePassword(String password) throws ValidatorException {
		if (StringUtils.isBlank(password) || !password.matches(PASSWORD_REGEX)) {
			throw new ValidatorException("validation.user.registration.password");
		}
	}

	public void validateEmail(String email) throws ValidatorException {
		if (StringUtils.isBlank(email) || email.length() > MAX_EMAIL_FIELD_LENGTH || !email.matches(EMAIL_REGEX)) {
			throw new ValidatorException("validation.user.registration.email");
		}
	}
	
}
