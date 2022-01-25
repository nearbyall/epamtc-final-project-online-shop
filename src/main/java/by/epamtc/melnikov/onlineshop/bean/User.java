package by.epamtc.melnikov.onlineshop.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import by.epamtc.melnikov.onlineshop.bean.type.StatusType;
import by.epamtc.melnikov.onlineshop.bean.type.UserType;

/**
 * Java bean class which describes the user.
 * 
 * @author nearbyall
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2248522943128116980L;
	
	private int id;
	private StatusType status;
	private UserType role;
	private String name;
	private String surname;
	private String mobile;
	private String email;
	private String encryptedPassword;
	private Timestamp registeredAt;
	private Timestamp lastLoginAt;
	private double balance;
	
	public User() { }

	public User(int id, StatusType status, UserType role, String name, String surname, String mobile, String email,
			String encryptedPassword, Timestamp registeredAt, Timestamp lastLoginAt, double balance) {
		this.id = id;
		this.status = status;
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.mobile = mobile;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.registeredAt = registeredAt;
		this.lastLoginAt = lastLoginAt;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public UserType getRole() {
		return role;
	}

	public void setRole(UserType role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Timestamp getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(Timestamp registeredAt) {
		this.registeredAt = registeredAt;
	}

	public Timestamp getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Timestamp lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isBanned() {
		if (status == StatusType.BANNED) {
			return true;
		} else {
			return false;
		}
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() 
				+ " [id=" + id + ", status=" + status + ", role=" + role + ", name=" + name + ", surname=" + surname
				+ ", mobile=" + mobile + ", email=" + email + ", encryptedPassword=" + encryptedPassword
				+ ", registeredAt=" + registeredAt + ", lastLoginAt=" + lastLoginAt + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((encryptedPassword == null) ? 0 : encryptedPassword.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastLoginAt == null) ? 0 : lastLoginAt.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((registeredAt == null) ? 0 : registeredAt.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (encryptedPassword == null) {
			if (other.encryptedPassword != null)
				return false;
		} else if (!encryptedPassword.equals(other.encryptedPassword))
			return false;
		if (id != other.id)
			return false;
		if (lastLoginAt == null) {
			if (other.lastLoginAt != null)
				return false;
		} else if (!lastLoginAt.equals(other.lastLoginAt))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (registeredAt == null) {
			if (other.registeredAt != null)
				return false;
		} else if (!registeredAt.equals(other.registeredAt))
			return false;
		if (role != other.role)
			return false;
		if (status != other.status)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
}
