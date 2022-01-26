package by.epamtc.melnikov.onlineshop.service.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.builder.UserBuilder;
import by.epamtc.melnikov.onlineshop.service.validation.UserValidator;
import by.epamtc.melnikov.onlineshop.service.validation.exception.ValidatorException;

public class UserValidatorTest {

	private static final UserValidator validator = new UserValidator();
	
	private User user;
	private User invalidUser;
	
	@Before
	public void init() {
		user = new UserBuilder()
				.withEmail("testmail@gmail.com")
				.withMobile("+375298682036")
				.withPasswordEncrypted("rbceyzvty12345X")
				.build();
		invalidUser = new User();
	}
		
	@Test
	public void validateUserPositive() throws ValidatorException {
		validator.validateUser(user);
		assertNotNull(user);
	}
	
	@Test(expected = ValidatorException.class)
	public void validateUserEmailNegative() throws ValidatorException {
		validator.validateEmail("");
	}
	
	@Test(expected = ValidatorException.class)
	public void validateUserMobileNegative() throws ValidatorException {
		validator.validateMobile("");
	}
	
	@Test(expected = ValidatorException.class)
	public void validateUserPasswordNegative() throws ValidatorException {
		validator.validatePassword("");
	}
	
	@Test(expected = ValidatorException.class)
	public void validateUserNegative() throws ValidatorException {
		user.setEmail("");
		user.setEncryptedPassword("");
		user.setMobile("");
		validator.validateUser(user);
		assertNotNull(user);
	}

	@Test(expected = ValidatorException.class)
	public void validateNewUserNegative() throws ValidatorException {
		validator.validateUser(invalidUser);
		assertNotNull(invalidUser);
	}
	
}