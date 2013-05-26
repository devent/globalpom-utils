package com.anrisoftware.globalpom.resources;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Exception if the input can not be converted to URL or URI.
 * 
 * @see StringToURI
 * @see ToURL
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ConvertException extends RuntimeException {

	private final Context<ConvertException> context;

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public ConvertException(String message, Throwable cause) {
		super(message, cause);
		this.context = new Context<ConvertException>(this);
	}

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public ConvertException(String message) {
		super(message);
		this.context = new Context<ConvertException>(this);
	}

	/**
	 * @see Context#addContext(String, Object)
	 */
	public ConvertException addContext(String name, Object value) {
		return context.addContext(name, value);
	}

	/**
	 * @see Context#getContext()
	 */
	public Map<String, Object> getContext() {
		return context.getContext();
	}

	@Override
	public String toString() {
		return context.toString();
	}

}
