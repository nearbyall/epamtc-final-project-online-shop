package by.epamtc.melnikov.onlineshop.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import by.epamtc.melnikov.onlineshop.bean.type.UserType;
import by.epamtc.melnikov.onlineshop.controller.AttributeNameStorage;

/**
 * Application Lifecycle Listener implementation class SessionListener.
 * Needed to initialize and clear data when creating and destroying a session.
 *
 * @author nearbyall
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute(AttributeNameStorage.USER_ROLE, UserType.GUEST.getName());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		se.getSession().removeAttribute(AttributeNameStorage.USER_DATA);
		se.getSession().removeAttribute(AttributeNameStorage.USER_EMAIL);
		se.getSession().removeAttribute(AttributeNameStorage.USER_ROLE);
		se.getSession().removeAttribute(AttributeNameStorage.USER_ID);
	}
	
}
