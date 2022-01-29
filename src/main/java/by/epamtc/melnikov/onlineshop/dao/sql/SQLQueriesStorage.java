package by.epamtc.melnikov.onlineshop.dao.sql;

/**
 * The class needed to store sql queries.
 * Includes one private constructor.
 * 
 * @author nearbyall
 *
 */
public class SQLQueriesStorage {

	public static final String LIMIT_OFFSET_STATEMENT = " LIMIT ? OFFSET ? ";
	
	public static final String REGISTER_USER = "INSERT INTO users " +
			"(name, surname, mobile, email, passwordEncrypted, registeredAt, lastLoginAt, balance, statusId, roleId) " +
			"value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
	public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = (?)";
	public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
	public static final String FIND_ALL_USERS = "SELECT * FROM users";
	
	public static final String UPDATE_USER_BALANCE = "UPDATE users SET balance = (?) WHERE id = (?)";
	public static final String UPDATE_USER_BAN_STATUS = "UPDATE users SET statusId = (?) WHERE id = (?)";
	public static final String UPDATE_USER_LAST_LOGIN_AT = "UPDATE users SET lastLoginAt = (?) WHERE id = (?)";
	public static final String UPDATE_USER_PROFILE_DATA = "UPDATE users SET passwordEncrypted = (?), name = (?), surname = (?)," +
			"  mobile = (?) WHERE id = (?)";

	public static final String INSERT_PRODUCT = "INSERT INTO products " +
			"(title, price, count, createdAt, updatedAt, description, imgPath, categoryId) " +
			"value (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO product_categories (name, imgPath) value (?, ?)";
	
	public static final String FIND_PRODUCT_BY_ID = "SELECT products.id, products.title, products.description, products.price, products.imgPath, " +
			"products.createdAt, products.updatedAt, products.count," +
			"product_categories.id as \"product_categories.id\", product_categories.name as \"product_categories.name\", product_categories.imgPath as \"product_categories.imgPath\"" +
			"FROM products " +
			"LEFT JOIN product_categories ON products.categoryId = product_categories.id " +
			"WHERE products.id = (?)";
	public static final String FIND_PRODUCTS_COUNT = "SELECT COUNT(products.id) FROM products";
	public static final String FIND_PRODUCTS_COUNT_BY_CATEGORY_ID = "SELECT COUNT(products.id) FROM products WHERE categoryId = (?)";
	public static final String FIND_ALL_PRODCUTS = "SELECT products.id, products.title, products.description, products.price, products.imgPath, " +
			"products.createdAt, products.updatedAt, products.count," +
			"product_categories.id as \"product_categories.id\", product_categories.name as \"product_categories.name\", product_categories.imgPath as \"product_categories.imgPath\"" +
			"FROM products " +
			"LEFT JOIN product_categories ON products.categoryId = product_categories.id ";
	public static final String FIND_ALL_PRODUCTS_BY_CATEGORY_ID = "SELECT products.id, products.title, products.description, products.price, products.imgPath, " +
			"products.createdAt, products.updatedAt, products.count," +
			"product_categories.id as \"product_categories.id\", product_categories.name as \"product_categories.name\", product_categories.imgPath as \"product_categories.imgPath\"" +
			"FROM products " +
			"LEFT JOIN product_categories ON products.categoryId = product_categories.id " +
			"WHERE products.categoryId = (?)";
	
	
	public static final String INSERT_REVIEW = "INSERT INTO reviews " +
			"(productId, userId, review, createdAt, updatedAt) " +
			"value (?, ?, ?, ?, ?)";
	
	public static final String FIND_REVIEWS_BY_PRODUCT_ID = "SELECT * FROM reviews WHERE productId = (?)";
	
	public static final String FIND_ALL_PRODUCT_CATEGORIES = "SELECT * FROM product_categories";
	
	private SQLQueriesStorage() {}
	
}
