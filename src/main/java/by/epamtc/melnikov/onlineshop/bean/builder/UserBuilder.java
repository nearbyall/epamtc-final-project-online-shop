package by.epamtc.melnikov.onlineshop.bean.builder;

import java.sql.Timestamp;

import by.epamtc.melnikov.onlineshop.bean.User;
import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;

/**
 * Realization of the pattern Builder. 
 * Builds a {@link User} from the received parameters.
 * 
 * @author nearbyall
 *
 */
public class UserBuilder {

	private User user;
	
	public UserBuilder() {
		user = new User();
	}
	
	public UserBuilder withId(int id) {
		user.setId(id);
		return this;
	}
	
	public UserBuilder withStatusType(StatusType status) {
		user.setStatus(status);
		return this;
	}
	
	public UserBuilder withUserType(UserType role) {
		user.setRole(role);
		return this;
	}
	
	public UserBuilder withName(String name) {
		user.setName(name);
		return this;
	}
	
	public UserBuilder withSurname(String surname) {
		user.setSurname(surname);
		return this;
	}
	
	public UserBuilder withMobile(String mobile) {
		user.setMobile(mobile);
		return this;
	}
	
	public UserBuilder withEmail(String email) {
		user.setEmail(email);
		return this;
	}
	
	public UserBuilder withRegisteredAt(Timestamp registeredAt) {
		user.setRegisteredAt(registeredAt);
		return this;
	}
	
	public UserBuilder withLastLoginAt(Timestamp lastLoginAt) {
		user.setLastLoginAt(lastLoginAt);
		return this;
	}
	
	public UserBuilder withPasswordEncrypted(String password) {
		user.setEncryptedPassword(password);
		return this;
	}
	
	public UserBuilder withBalance(double balance) {
		user.setBalance(balance);
		return this;
	}
	
	public User build() {
		return user;
	}
}
