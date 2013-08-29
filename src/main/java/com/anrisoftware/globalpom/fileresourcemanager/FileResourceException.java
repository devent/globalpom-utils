/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
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
