package by.epamtc.melnikov.onlineshop.dao.sql;

public class SQLQueriesStorage {

	public static final String REGISTER_USER = "INSERT INTO users " +
			"(name, surname, mobile, email, passwordEncrypted, registeredAt, lastLoginAt, balance, statusId, roleId) " +
			"value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
	public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = (?)";
	public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
	public static final String FIND_ALL_USERS = "SELECT * FROM users";
	public static final String UPDATE_USER_PROFILE_DATA = "UPDATE users SET passwordEncrypted = (?), name = (?), surname = (?)," +
			"  mobile = (?) WHERE id = (?)";
    
	public static final String UPDATE_USER_BAN_STATUS = "UPDATE user SET is_banned = (?) WHERE id = (?)";

	public static final String INSERT_PRODUCT = "INSERT INTO book (uuid, genre_uuid, language_uuid, publisher_uuid, " +
			"author_uuid, title, publish_year, pages_quantity, description) value (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private SQLQueriesStorage() {}
	
}
