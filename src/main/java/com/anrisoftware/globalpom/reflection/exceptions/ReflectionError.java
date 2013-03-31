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
package com.anrisoftware.globalpom.reflection.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

/**
 * Error while accessing fields or methods with reflection.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
@SuppressWarnings("serial")
public class ReflectionError extends ContextedRuntimeException {

	/**
	 * For serialization.
	 */
	public ReflectionError() {
		super();
	}

	/**
	 * Sets the message and the cause of the error.
	 * 
	 * @param message
	 *            the message of the error.
	 * 
	 * @param cause
	 *            the {@link Throwable} cause of the error.
	 */
	public ReflectionError(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Sets the message of the error, but with no cause.
	 * 
	 * @param message
	 *            the message of the error.
	 */
	public ReflectionError(String message) {
		super(message);
	}

	@Override
	public ReflectionError addContextValue(String label, Object value) {
		return (ReflectionError) super.addContextValue(label, value);
	}
}
