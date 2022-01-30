package by.epamtc.melnikov.onlineshop.controller;

/**
 * The class needed to store URLs of jsp pages.
 * Includes one private constructor.
 * 
 * @author nearbyall
 *
 */
public class PageStorage {

	private PageStorage() {}
	
	public final static String ROOT = "./";
	
	public final static String HOME = "/jsp/guest/main.jsp";
	public final static String LOG_IN = "/jsp/guest/login.jsp";
	public final static String REGISTRATION = "/jsp/guest/registration.jsp";
	public final static String CATALOG = "/jsp/guest/catalog.jsp";
	public final static String CATALOG_BY_CATEGORY = "/jsp/guest/catalogByCategory.jsp";
	public final static String PRODUCT = "/jsp/guest/product.jsp";
	public final static String ADD_PRODUCT = "/jsp/admin/addProduct.jsp";
	public final static String ADD_PRODUCT_CATEGORY = "/jsp/admin/addProductCategory.jsp";
	public final static String USERS_LIST = "/jsp/admin/usersList.jsp";
	public final static String PROFILE = "/jsp/user/profile.jsp";
	public final static String TOP_UP_BALANCE = "/jsp/user/topUpBalance.jsp";
	public static final String CART = "/jsp/user/cart.jsp";
	
}
