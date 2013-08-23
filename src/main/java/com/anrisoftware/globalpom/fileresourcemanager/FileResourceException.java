package com.anrisoftware.globalpom.fileresourcemanager;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Errors from file resource manager.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
@SuppressWarnings("serial")
public class FileResourceException extends Exception {

	private final Context<FileResourceException> context;

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public FileResourceException(String message, Throwable cause) {
		super(message, cause);
		this.context = new Context<FileResourceException>(this);
	}

	/**
	 * @see Exception#Exception(String)
	 */
	public FileResourceException(String message) {
		super(message);
		this.context = new Context<FileResourceException>(this);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public FileResourceException(Object message, Throwable cause) {
		super(message.toString(), cause);
		this.context = new Context<FileResourceException>(this);
	}

	/**
	 * @see Exception#Exception(String)
	 */
	public FileResourceException(Object message) {
		super(message.toString());
		this.context = new Context<FileResourceException>(this);
	}

	/**
	 * @see Context#addContext(String, Object)
	 */
	public FileResourceException add(String name, Object value) {
		context.addContext(name, value);
		return this;
	}

	/**
	 * @see Context#addContext(String, Object)
	 */
	public FileResourceException add(Object name, Object value) {
		context.addContext(name.toString(), value);
		return this;
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
