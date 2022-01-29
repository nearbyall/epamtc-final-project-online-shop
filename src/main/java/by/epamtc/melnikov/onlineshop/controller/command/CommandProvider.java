package by.epamtc.melnikov.onlineshop.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.melnikov.onlineshop.controller.command.impl.user.*;
import by.epamtc.melnikov.onlineshop.controller.command.impl.admin.*;
import by.epamtc.melnikov.onlineshop.controller.command.impl.guest.*;
import by.epamtc.melnikov.onlineshop.controller.command.impl.page.*;

/**
 * The class serves as a repository of commands the {@link Command} that are placed in the HashMap collection.
 * 
 * @author nearbyall
 *
 */
public class CommandProvider {
	
	/** Collection which includes {@link Command}s */
	Map<String, Command> commands = new HashMap<>();
	
	/**
	 * The constructor in which the {@link Command} interface implementations are added to the {@link Map} commands
	 */
	public CommandProvider() {
		
		/*
		 * Guest commands
		 */
		commands.put("logIn", new CommandLogIn());
		commands.put("registration", new CommandRegistration());
		commands.put("switchLanguage", new CommandSwitchLanguage());
		
		/*
		 * User commands
		 */
		commands.put("logOut", new CommandLogOut());
		commands.put("profilePage", new CommandOpenProfilePage());
		commands.put("updateUserInfo", new CommandUpdateUserInfo());
		commands.put("topUpBalance", new CommandTopUpBalance());
		commands.put("writeReview", new CommandWriteReview());
		commands.put("addProductToCart", new CommandAddProductToCart());
		commands.put("constructOrder", new CommandConstructOrder());
		commands.put("deleteProductFromCart", new CommandDeleteProductFromCart());
		
		/*
		 * Admin commands
		 */
		commands.put("banUser", new CommandBanUser());
		commands.put("addProduct", new CommandAddProduct());
		commands.put("addProductCategory", new CommandAddProductCategory());
		commands.put("confirmOrder", new CommandConfirmOrder());
		commands.put("editProduct", new CommandEditProduct());
		
		/*
		 * Page commands
		 */
		commands.put("openMainPage", new CommandOpenMainPage());
		commands.put("allUsersList", new CommandOpenAllUsersList());
		commands.put("addProductPage", new CommandOpenAddProductPage());
		commands.put("addProductCategoryPage", new CommandOpenAddProductCategoryPage());
		commands.put("topUpBalancePage", new CommandOpenTopUpBalancePage());
		commands.put("openCatalogPage", new CommandOpenCatalogPage());
		commands.put("openCatalogByCategoryPage", new CommandOpenCatalogByCategoryPage());
		commands.put("openProductPage", new CommandOpenProductPage());
		commands.put("openEditProductPage", new CommandOpenEditProductPage());

	}
	
	/**
	 * Finds a command matching the given name
	 * 
	 * @param commandName 
	 * @return <tt>command</tt> the implementation of interface {@link Command}
	 * that matches the given <tt>commandName</tt>. 
	 * Command {@link CommandOpenMainPage} if command was not found by given <tt>commandName</tt>
	 */
	public Command getCommand(String commandName) {
		Command command = commands.get(commandName);
		if (command == null) {
			command = new CommandOpenMainPage();
		}
		return command;
	}
	
}