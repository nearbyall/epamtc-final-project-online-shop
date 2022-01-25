package by.epamtc.melnikov.onlineshop.controller.command.impl.guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.LocaleStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for language switch process.
 * 
 * @author nearbyall
 *
 */
public class CommandSwitchLanguage implements Command{

	private static final int COOKIE_MAX_AGE_21_DAY = 60*60*24*21;
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();

		String choosenLanguage = request.getParameter(JSPAttributeStorage.LANGUAGE_SWITCH_PARAMETER);
		String resultLanguage = LocaleStorage.getLocaleFromLanguage(choosenLanguage).getLanguage();
		request.getSession().setAttribute(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLanguage);
		
		Cookie languageCookie = new Cookie(JSPAttributeStorage.LANGUAGE_CURRENT_PAGE, resultLanguage);
		languageCookie.setMaxAge(COOKIE_MAX_AGE_21_DAY);
		languageCookie.setPath(request.getContextPath());
		response.addCookie(languageCookie);
		
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
