package com.anrisoftware.globalpom.resources;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link StringToURI}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class StringToURILogger extends AbstractLogger {

	private static final String PATH = "path";
	private static final String ERROR_CONVERT_MESSAGE = "Error convert path '%s' to URL.";
	private static final String ERROR_CONVERT = "Error convert path";

	/**
	 * Create logger for {@link StringToURI}.
	 */
	public StringToURILogger() {
		super(StringToURI.class);
	}

	ConvertException errorConvert(Exception e, String path) {
		return logException(
				new ConvertException(ERROR_CONVERT, e).addContext(PATH,
						path), ERROR_CONVERT_MESSAGE, path);
	}
}
