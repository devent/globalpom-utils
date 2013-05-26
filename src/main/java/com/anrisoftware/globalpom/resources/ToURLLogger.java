package com.anrisoftware.globalpom.resources;

import java.io.File;
import java.net.URI;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link ToURL}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class ToURLLogger extends AbstractLogger {

	private static final String SCHEME = "scheme";
	private static final String FILE = "file";
	private static final String URI = "URI";
	private static final String PATH = "path";
	private static final String ERROR_CONVERT_MESSAGE = "Error convert path '%s' to URL.";
	private static final String ERROR_CONVERT = "Error convert path";
	private static final String ERROR_CONVERT_SCHEME_MESSAGE = "Error convert path '%s%s' to URL.";

	/**
	 * Create logger for {@link ToURL}.
	 */
	public ToURLLogger() {
		super(ToURL.class);
	}

	ConvertException errorConvert(Exception e, String path) {
		return logException(
				new ConvertException(ERROR_CONVERT, e).addContext(PATH, path),
				ERROR_CONVERT_MESSAGE, path);
	}

	ConvertException errorConvert(Exception e, String path, String scheme) {
		return logException(
				new ConvertException(ERROR_CONVERT, e).addContext(PATH, path)
						.addContext(SCHEME, scheme),
				ERROR_CONVERT_SCHEME_MESSAGE, scheme, path);
	}

	ConvertException errorConvert(Exception e, URI uri) {
		return logException(
				new ConvertException(ERROR_CONVERT, e).addContext(URI, uri),
				ERROR_CONVERT_MESSAGE, uri);
	}

	ConvertException errorConvert(Exception e, File file) {
		return logException(
				new ConvertException(ERROR_CONVERT, e).addContext(FILE, file),
				ERROR_CONVERT_MESSAGE, file);
	}

}
