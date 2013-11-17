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
package com.anrisoftware.globalpom.dataimport;

import java.io.IOException;
import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Import exception.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class CsvImportException extends IOException {

	private final Context<CsvImportException> context;

	/**
	 * @see IOException#IOException(String, Throwable)
	 */
	public CsvImportException(String message, Throwable cause) {
		super(message, cause);
		this.context = new Context<CsvImportException>(this);
	}

	/**
	 * @see IOException#IOException(String)
	 */
	public CsvImportException(String message) {
		super(message);
		this.context = new Context<CsvImportException>(this);
	}

	/**
	 * @see IOException#IOException(String, Throwable)
	 */
	public CsvImportException(Object message, Throwable cause) {
		super(message.toString(), cause);
		this.context = new Context<CsvImportException>(this);
	}

	/**
	 * @see IOException#IOException(String)
	 */
	public CsvImportException(Object message) {
		super(message.toString());
		this.context = new Context<CsvImportException>(this);
	}

	/**
	 * @see Context#addContext(String, Object)
	 */
	public CsvImportException add(String name, Object value) {
		context.addContext(name, value);
		return this;
	}

	/**
	 * @see Context#addContext(String, Object)
	 */
	public CsvImportException add(Object name, Object value) {
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
