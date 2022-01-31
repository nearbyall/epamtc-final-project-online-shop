package by.epamtc.melnikov.onlineshop.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 * @author nearbyall
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, UserType.GUEST.getName());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		se.getSession().removeAttribute(JSPAttributeStorage.USER_DATA);
		se.getSession().removeAttribute(JSPAttributeStorage.USER_EMAIL);
		se.getSession().removeAttribute(JSPAttributeStorage.USER_ROLE);
		se.getSession().removeAttribute(JSPAttributeStorage.USER_ID);
	}
	
}
