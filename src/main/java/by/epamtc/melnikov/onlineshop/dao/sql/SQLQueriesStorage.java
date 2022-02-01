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
	
	/*
	 * User queries
	 */
	public static final String REGISTER_USER = "INSERT INTO users " +
			"(name, surname, mobile, email, passwordEncrypted, registeredAt, lastLoginAt, balance, statusId, roleId) " +
			"value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
	public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = (?)";
	
	public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
	
	public static final String FIND_USER_BY_ID_AND_LOG_IN_TOKEN = "SELECT * FROM users WHERE id = (?) AND logInToken = (?)";
	
	public static final String FIND_USER_BALANCE_BY_ID = "SELECT balance FROM users WHERE id = (?)";
	
	public static final String FIND_ALL_USERS = "SELECT * FROM users";
	
	public static final String UPDATE_USER_BALANCE = "UPDATE users SET balance = (?) WHERE id = (?)";
	
	public static final String UPDATE_USER_BAN_STATUS = "UPDATE users SET statusId = (?) WHERE id = (?)";
	
	public static final String UPDATE_USER_LAST_LOGIN_AT = "UPDATE users SET lastLoginAt = (?) WHERE id = (?)";
	
	public static final String UPDATE_USER_LOG_IN_TOKEN = "UPDATE users SET logInToken = (?) WHERE id = (?)";
	
	public static final String UPDATE_USER_LOG_IN_TOKEN_TO_NULL = "UPDATE users SET logInToken = NULL WHERE id = (?)";
	
	public static final String UPDATE_USER_PROFILE_DATA = "UPDATE users SET passwordEncrypted = (?), name = (?), surname = (?)," +
			"  mobile = (?) WHERE id = (?)";
	
	/*
	 * Product queries
	 */
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
	
	public static final String FIND_ALL_PRODUCT_CATEGORIES = "SELECT * FROM product_categories";
	
	public static final String UPDATE_PRODUCT_COUNT_BY_PRODUCT_ID = "UPDATE products SET count = (?), updatedAt = (?) WHERE id = (?)";
	
	public static final String UPDATE_PRODUCT_BY_ID = "UPDATE products SET title = (?), price = (?), count = (?), " +
			"updatedAt = (?), description = (?), imgPath = (?), categoryId = (?) WHERE id = (?)";
	
	/*
	 * Review queries
	 */
	public static final String INSERT_REVIEW = "INSERT INTO reviews " +
			"(productId, userId, review, createdAt, updatedAt) " +
			"value (?, ?, ?, ?, ?)";
	
	public static final String FIND_REVIEWS_BY_PRODUCT_ID = "SELECT * FROM reviews WHERE productId = (?)";
	
	/*
	 * Cart queries
	 */
	public static final String INSERT_CART_ITEM = "INSERT into cart_items " +
			"(userId, productId, count, createdAt, updatedAt) " +
			"value (?, ?, ?, ?, ?)";
	
	public static final String CLEAR_CART_ITEMS_BY_USER_ID = "DELETE FROM cart_items WHERE userId = (?)";
	
	public static final String CLEAR_CART_ITEMS_BY_PRODUCT_ID = "DELETE FROM cart_items WHERE productId = (?)";
	
	public static final String UPDATE_CART_ITEMS_COUNT_BY_USER_ID_AND_PRODUCT_ID = "UPDATE cart_items SET count = (?), updatedAt = (?) " +
			"WHERE (productId = (?)) AND (userId = (?))";
	
	public static final String FIND_CART_ITEMS_COUNT_BY_USER_ID_AND_PRODUCT_ID = "SELECT count FROM cart_items " +
			"WHERE (productId = (?)) AND (userId = (?))";
	
	public static final String FIND_ALL_CART_ITEMS_BY_USER_ID = "SELECT cart_items.id, cart_items.userId, cart_items.productId, " +
			"cart_items.count, cart_items.createdAt, cart_items.updatedAt, " +
			"products.id as \"products.id\", products.title as \"products.title\", products.imgPath as \"products.imgPath\", " +
			"products.count as \"products.count\", products.price as \"products.price\", products.description as \"products.description\", " +
			"products.categoryId as \"products.categoryId\", products.createdAt as \"products.createdAt\", products.updatedAt as \"products.updatedAt\", " +
			"product_categories.id as \"product_categories.id\", product_categories.name as \"product_categories.name\", product_categories.imgPath as \"product_categories.imgPath\" " +
			"FROM cart_items " +
			"LEFT JOIN products ON cart_items.productId = products.id " +
			"LEFT JOIN product_categories ON products.categoryId = product_categories.id " +
			"WHERE cart_items.userId = (?)";
	
	/*
	 * Order queries
	 */
	public static final String INSERT_ORDER = "INSERT INTO orders " +
			"(id, userId, statusId, createdAt, updatedAt) " +
			"value (?, ?, ?, ?, ?)";
	
	public static final String INSERT_ORDER_ITEM = "INSERT INTO order_items " +
			"(productId, orderId, count, totalPrice) " +
			"value (?, ?, ?, ?)";
	
	public static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = (?)";
	
	public static final String DELETE_ORDER_ITEMS_BY_ORDER_ID = "DELETE FROM order_items WHERE id = (?)";
	
	public static final String FIND_ALL_ORDERS = "SELECT orders.id, orders.userId, orders.statusId, orders.createdAt, orders.updatedAt, " +
			"users.id as \"users.id\", users.email as\"users.email\" " +
			"FROM orders " +
			"LEFT JOIN users ON users.id = orders.userId ";
	
	public static final String FIND_ALL_ORDERS_BY_USER_ID = "SELECT orders.id, orders.userId, orders.statusId, orders.createdAt, orders.updatedAt, " +
			"users.id as \"users.id\", users.email as\"users.email\" " +
			"FROM orders " +
			"LEFT JOIN users ON users.id = orders.userId " +
			"WHERE orders.userId = (?) ";
	
	public static final String FIND_ALL_ORDER_ITEMS_BY_ORDER_ID = "SELECT order_items.id, order_items.productId, " +
			"order_items.count, order_items.totalPrice, " +
			"products.id as \"products.id\", products.title as \"products.title\", products.imgPath as \"products.imgPath\", " +
			"products.count as \"products.count\", products.price as \"products.price\", products.description as \"products.description\", " +
			"products.categoryId as \"products.categoryId\", products.createdAt as \"products.createdAt\", products.updatedAt as \"products.updatedAt\", " +
			"product_categories.id as \"product_categories.id\", product_categories.name as \"product_categories.name\", product_categories.imgPath as \"product_categories.imgPath\" " +
			"FROM order_items " +
			"LEFT JOIN products ON order_items.productId = products.id " +
			"LEFT JOIN product_categories ON products.categoryId = product_categories.id " +
			"WHERE order_items.orderId = (?)";
	
	public static final String FIND_ORDER_ITEMS_COUNT_BY_ORDER_ID = "SELECT COUNT(order_items.id) FROM order_items WHERE orderId = (?)";
	
	public static final String UPDATE_ORDER_STATUS = "UPDATE orders SET statusId = (?), updatedAt = (?) WHERE id = (?)";
	
	private SQLQueriesStorage() {}
	
}
