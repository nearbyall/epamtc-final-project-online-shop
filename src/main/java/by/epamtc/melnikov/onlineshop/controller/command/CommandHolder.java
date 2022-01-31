package by.epamtc.melnikov.onlineshop.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.onlineshop.controller.command.impl.admin.*;
import by.epamtc.melnikov.onlineshop.controller.command.impl.guest.*;
import by.epamtc.melnikov.onlineshop.controller.command.impl.user.*;

/**
 * The class serves as a repository of commands the {@link Command} that are placed in the Enum.
 * 
 * @author nearbyall
 *
 */
public enum CommandHolder {
	/*
	 * Guest commands
	 */
	LOG_IN("logIn", new CommandLogIn()),
	REGISTRATION("registration", new CommandRegistration()),
	SWITCH_LANGUAGE("switchLanguage", new CommandSwitchLanguage()),
	
	/*
	 * User commands
	 */
	LOG_OUT("logOut", new CommandLogOut()),
	OPEN_PROFILE_PAGE("profilePage", new CommandOpenProfilePage()),
	UPDATE_USER_INFO("updateUserInfo", new CommandUpdateUserInfo()),
	TOP_UP_BALANCE("topUpBalance", new CommandTopUpBalance()),
	WRITE_REVIEW("writeReview", new CommandWriteReview()),
	ADD_PRODUCT_TO_CART("addProductToCart", new CommandAddProductToCart()),
	CONSTRUCT_ORDER("constructOrder", new CommandConstructOrder()),
	DELETE_PRODUCT_FROM_CART("deleteProductFromCart", new CommandDeleteProductFromCart()),
	
	/*
	 * Admin commands
	 */
	BAN_USER("banUser", new CommandBanUser()),
	ADD_PRODUCT("addProduct", new CommandAddProduct()),
	ADD_PRODUCT_CATEGORY("addProductCategory", new CommandAddProductCategory()),
	CONFIRM_ORDER("confirmOrder", new CommandConfirmOrder()),
	EDIT_PRODUCT("editProduct", new CommandEditProduct()),
	
	/*
	 * Page commands
	 */
	OPEN_MAIN_PAGE("openMainPage", new CommandOpenMainPage()),
	OPEN_ALL_USERS_LIST("allUsersList", new CommandOpenAllUsersList()),
	OPEN_ADD_PRODUCT_PAGE("addProductPage", new CommandOpenAddProductPage()),
	OPEN_ADD_PRODUCT_CATEGORY_PAGE("addProductCategoryPage", new CommandOpenAddProductCategoryPage()),
	OPEN_TOP_UP_BALANCE_PAGE("topUpBalancePage", new CommandOpenTopUpBalancePage()),
	OPEN_CATALOG_PAGE("openCatalogPage", new CommandOpenCatalogPage()),
	OPEN_CATALOG_BY_CATEGORY_PAGE("openCatalogByCategoryPage", new CommandOpenCatalogByCategoryPage()),
	OPEN_PRODUCT_PAGE("openProductPage", new CommandOpenProductPage()),
	OPEN_EDIT_PRODUCT_PAGE("openEditProductPage", new CommandOpenEditProductPage()),
	OPEN_CART_PAGE("openCartPage", new CommandOpenCartPage()),
	OPEN_ALL_ORDERS_PAGE("openAllOrdersPage", new CommandOpenAllOrdersPage()),
	OPEN_USER_ORDERS_PAGE("openUserOrdersPage", new CommandOpenUserOrdersPage());

	private static final Logger logger = LogManager.getLogger();

	private final Command command;
	private final String commandName;

	CommandHolder(String commandName, Command command) {
		this.command = command;
		this.commandName = commandName;
	}

	public Command getCommand() {
		return command;
	}

	public String getCommandName() {
		return commandName;
	}

	public static Command getCommandByName(String commandName) {
		return getCommandEnumByName(commandName).command;
	}

	public static CommandHolder getCommandEnumByName(String commandName) {
		for (CommandHolder type: CommandHolder.values()) {
			if (type.commandName.equals(commandName)){
				return type;
			}
		}
		logger.warn(String.format("Command %s is not found, forward to HomePage", commandName));
		return OPEN_MAIN_PAGE;
	}
}
