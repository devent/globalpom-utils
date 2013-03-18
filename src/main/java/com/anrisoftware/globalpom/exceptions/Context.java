package com.anrisoftware.globalpom.exceptions;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

import com.anrisoftware.globalpom.strings.MapToTableString;

/**
 * Context for an exception.
 * <p>
 * The default message of the exception will output the context:
 * 
 * <pre>
 * Message of the exception, context:
 * Aaa := bbb
 * Ccc := ddd
 * </pre>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.3
 */
public class Context<T extends Exception> {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	private final Map<String, Object> context;

	private final T exception;

	/**
	 * Sets the exception of the context.
	 * 
	 * @param exception
	 *            the {@link Exception}.
	 */
	public Context(T exception) {
		this.exception = exception;
		this.context = new HashMap<String, Object>();
	}

	/**
	 * Adds the context with the specified name.
	 * 
	 * @param name
	 *            the name of the context.
	 * 
	 * @param value
	 *            the context value.
	 * 
	 * @return the context {@link Exception}.
	 */
	public T addContext(String name, Object value) {
		context.put(name, value);
		return exception;
	}

	/**
	 * Returns the context of the exception.
	 * 
	 * @return an unmodifiable {@link Map} with the context.
	 */
	public Map<String, Object> getContext() {
		return unmodifiableMap(context);
	}

	/**
	 * Output the exception message and the context:
	 * 
	 * <pre>
	 * Message of the exception, context:
	 * Aaa := bbb
	 * Ccc := ddd
	 * </pre>
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(exception.getMessage());
		appendContext(context, str);
		return str.toString();
	}

	private void appendContext(Map<String, Object> context, StringBuilder str) {
		if (context.size() > 0) {
			str.append(", context:");
			str.append(LINE_SEPARATOR);
			MapToTableString.withDefaults(str).append(context);
		}
	}
}
