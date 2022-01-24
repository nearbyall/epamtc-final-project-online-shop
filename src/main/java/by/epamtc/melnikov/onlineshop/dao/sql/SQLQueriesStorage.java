package by.epamtc.melnikov.onlineshop.dao.sql;

public class SQLQueriesStorage {

	public static final String REGISTER_USER = "INSERT INTO users " +
			"(name, surname, mobile, email, passwordEncrypted, registeredAt, lastLoginAt, balance, statusId, roleId) " +
			"value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
	public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = (?)";
	public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
	public static final String FIND_ALL_USERS = "SELECT * FROM users";
	
	public static final String UPDATE_USER_BALANCE = "UPDATE users SET balance = () WHERE id = (?)";
	public static final String UPDATE_USER_BAN_STATUS = "UPDATE users SET statusId = (?) WHERE id = (?)";
	public static final String UPDATE_USER_PROFILE_DATA = "UPDATE users SET passwordEncrypted = (?), name = (?), surname = (?)," +
			"  mobile = (?) WHERE id = (?)";

	public static final String INSERT_PRODUCT = "INSERT INTO products () value ()";
	public static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO product_categories (name, imgPath) value (?, ?)";
	
	private SQLQueriesStorage() {}
	
}
