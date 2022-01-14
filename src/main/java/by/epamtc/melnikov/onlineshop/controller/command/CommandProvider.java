package by.epamtc.melnikov.onlineshop.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandAllUsersList;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandBanUser;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandLogIn;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandLogOut;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandOpenMainPage;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandOpenProfilePage;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandRegistration;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandSwitchLanguage;
import by.epamtc.melnikov.onlineshop.controller.command.impl.CommandUpdateUserInfo;

public class CommandProvider {

	Map<String, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		// put commands
		commands.put("openMainPage", new CommandOpenMainPage());
		commands.put("registration", new CommandRegistration());
		commands.put("logOut", new CommandLogOut());
		commands.put("switchLanguage", new CommandSwitchLanguage());
		commands.put("profilePage", new CommandOpenProfilePage());
		commands.put("logIn", new CommandLogIn());
		commands.put("updateUserInfo", new CommandUpdateUserInfo());
		commands.put("allUsersList", new CommandAllUsersList());
		commands.put("banUser", new CommandBanUser());
	}
	
	public Command getCommand(String commandName) {
		
		Command command = commands.get(commandName);
		
		if (command == null) {
			command = new CommandOpenMainPage();
		}
		
		return command;
	}
	
}



