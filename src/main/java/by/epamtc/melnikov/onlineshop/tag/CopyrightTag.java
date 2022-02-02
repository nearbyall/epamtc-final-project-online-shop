package by.epamtc.melnikov.onlineshop.tag;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Custom jsp tag for footer.
 * 
 * @author nearbyall
 *
 */
public class CopyrightTag extends TagSupport {
	
	private static final long serialVersionUID = -4801513380481335550L;

	private static final Logger logger = LogManager.getLogger(CopyrightTag.class);

	private static final String COPYRIGHT_TAG = "Copyright 2021 JWD EPAMTC by Vladislav Melnikov. All rights reserved.";

	@Override
	public int doStartTag() {
		try {
			pageContext.getOut().write(COPYRIGHT_TAG);
		} catch (IOException e) {
			logger.warn(e);
		}
		return SKIP_BODY;
	}
}
