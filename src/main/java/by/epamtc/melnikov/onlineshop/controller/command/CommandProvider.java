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
		commands.put("openMainPage", new CommandOpenMainPage());
		commands.put("registration", new CommandRegistration());
		commands.put("logOut", new CommandLogOut());
		commands.put("switchLanguage", new CommandSwitchLanguage());
		commands.put("profilePage", new CommandOpenProfilePage());
		commands.put("logIn", new CommandLogIn());
		commands.put("updateUserInfo", new CommandUpdateUserInfo());
		commands.put("allUsersList", new CommandAllUsersList());
		commands.put("banUser", new CommandBanUser());
		commands.put("addProductCategory", new CommandAddProductCategory());
		commands.put("addProductPage", new CommandAddProductPage());
		commands.put("addProductCategoryPage", new CommandAddProductCategoryPage());
		commands.put("topUpBalancePage", new CommandTopUpBalancePage());
		commands.put("topUpBalance", new CommandTopUpBalance());
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



