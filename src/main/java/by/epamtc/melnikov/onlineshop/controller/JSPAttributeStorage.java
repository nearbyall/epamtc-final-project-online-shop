package by.epamtc.melnikov.onlineshop.controller;

/**
 * The class needed to store attributes of jsp pages.
 * Includes one private constructor.
 * 
 * @author nearbyall
 *
 */
public class JSPAttributeStorage {

	private JSPAttributeStorage(){}
	
	public static final String COMMAND = "action";
	public static final String REDIRECT_PAGE_COMMAND = "redirectPageCommand";

	public static final String LANGUAGE_CURRENT_PAGE = "lang";
	public static final String LANGUAGE_SWITCH_PARAMETER = "language";
	public static final String LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS = "currentParameters";
	public static final String LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL = "currentPageAbsoluteURL";
	public static final String EXCEPTION_MESSAGE = "exception_msg";

	public static final String ONLINE_USERS_LIST = "onlineUsers";
	public static final String ALL_USERS_LIST = "allUsersList";

	public static final String USER_DATA = "user_registration_data";
	public static final String USER_FIRST_NAME = "name";
	public static final String USER_LAST_NAME = "surname";
	public static final String USER_PASSWORD = "password";
	public static final String USER_EMAIL = "email";
	public static final String USER_MOBILE = "mobile";
	public static final String USER_ROLE = "role";
	public static final String USER_ID = "userID";

	public static final String CARD_NUMBER = "cardNumber";
	public static final String CARD_DATE = "cardDate";
	public static final String CARD_CVV = "cardCVV";
	public static final String CARD_SUMM = "cardSumm";
    
	public static final String PRODUCT_CATEGORY_NAME = "categoryName";
    
	public static final String FILE = "file";
    
	public static final String PAGINATION_CURRENT_PAGE = "currentPage";
	public static final String PAGINATION_RECORDS_PER_PAGE = "recordsPerPage";
	public static final String PAGINATION_PAGES_QUANTITY = "pagesQuantity";
	
}
