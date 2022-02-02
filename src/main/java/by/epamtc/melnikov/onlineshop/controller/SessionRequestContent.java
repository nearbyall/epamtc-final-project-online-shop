package by.epamtc.melnikov.onlineshop.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class needed to retrieve attributes and parameters from {@link HttpServletRequest} and then store them
 * 
 * @author nearbyall
 *
 */
public class SessionRequestContent {

	private Map<String, Object> requestAttributes;
	private Map<String, String[]> requestParameters;
	private Map<String, Object> sessionAttributes;
	private static final Logger logger = LogManager.getLogger();

	public SessionRequestContent(HttpServletRequest request) {
		sessionAttributes = new HashMap<>();
		requestAttributes = new HashMap<>();
		try {
			requestParameters = request.getParameterMap();
			Enumeration<String> reqAttributeNames = request.getAttributeNames();
			while (reqAttributeNames.hasMoreElements()) {
				String attributeName = reqAttributeNames.nextElement();
				requestAttributes.put(attributeName, request.getAttribute(attributeName));
			}
			HttpSession currentSession = request.getSession(false);
			if (currentSession != null) {
				Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
				while (sessionAttributeNames.hasMoreElements()) {
					String attributeName = sessionAttributeNames.nextElement();
					sessionAttributes.put(attributeName, currentSession.getAttribute(attributeName));
				}
			}
		} catch (NullPointerException e) {
			logger.error(e);
		}
	}

	public boolean checkRequestAttribute(String attrName) {
		return requestAttributes.containsKey(attrName);
	}

	public boolean checkRequestParameter(String attrName) {
		return requestParameters.containsKey(attrName);
	}

	public boolean checkSessionAttribute(String attrName) {
		return sessionAttributes.containsKey(attrName);
	}

	public Object getRequestAttribute(String key) {
		return requestAttributes.get(key);
	}

	public Map<String, Object> getRequestAttributes() {
		return requestAttributes;
	}

	public String[] getRequestParameter(String key) {
		return requestParameters.get(key);
	}

	public Map<String, String[]> getRequestParameters() {
		return requestParameters;
	}

	public Object getSessionAttribute(String key) {
		return sessionAttributes.get(key);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Set<String> keys = requestAttributes.keySet();
		for (String key : keys)
			sb.append("\nRequest attribute: key = " + key + " value = " + getRequestAttribute(key));
		keys = requestParameters.keySet();
		for (String key : keys)
			sb.append("\nRequest parameter: key = " + key + " value = " + getRequestParameter(key)[0]);
		keys = sessionAttributes.keySet();
		for (String key : keys)
			sb.append("\nSession attribute: key = " + key + " value = " + getSessionAttribute(key));
		return sb.toString();
	}
	
}
