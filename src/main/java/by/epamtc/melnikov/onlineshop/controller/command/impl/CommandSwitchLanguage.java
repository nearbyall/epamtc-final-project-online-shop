package by.epamtc.melnikov.onlineshop.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.LocaleStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;

public class CommandSwitchLanguage implements Command{

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CommandResult result = new CommandResult();

		String choosenLanguage = request.getParameter(JSPAttributeStorage.LANGUAGE_SWITCH_PARAMETER);
		String resultLanguage = LocaleStorage.getLocaleFromLanguage(choosenLanguage).getLanguage();
		request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLanguage);
		
		result.setPage(getRedirectPage(request));
		result.setDirection(Direction.REDIRECT);
		
		return result;
	}
	
    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter(JSPAttributeStorage.LANGUAGE_PRE_SWITCH_PAGE_PARAMETERS);
        if (!StringUtils.isBlank(redirectPage)) {
            return request.getContextPath() + request.getServletPath() + "?" + redirectPage;
        }
        return request.getParameter(JSPAttributeStorage.LANGUAGE_PRE_SWITCH_PAGE_ABSOLUTE_URL);
    }

}
