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
package com.anrisoftware.globalpom.reflection.beans;

import java.lang.reflect.InvocationTargetException;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Logging messages for {@link BeanAccessImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanFactoryImplLogger extends AbstractLogger {

	private static final String NAME = "name";
	private static final String FIND_MESSAGE = "Cannot find the class '{}'.";
	private static final String FIND = "Cannot find the class";
	private static final String INSTANTIATE_MESSAGE = "Can not instantiate {}.";
	private static final String INSTANTIATE = "Can not instantiate";
	private static final String NO_STANDARD_MESSAGE = "No standard constructor found for {}.";
	private static final String NO_STANDARD = "No standard constructor found";
	private static final String EXCEPTION_THROWN_MESSAGE = "Exception thrown in the standard constructor of {}.";
	private static final String EXCEPTION_THROWN = "Exception thrown in the standard constructor";
	private static final String TYPE = "type";
	private static final String ILLEGAL_ACCESS = "Illegal access to the standard constructor";
	private static final String ILLEGAL_ACCESS_MESSAGE = "Illegal access to the standard constructor of {}.";

	/**
	 * Creates logger for {@link BeanAccessImpl}.
	 */
	BeanFactoryImplLogger() {
		super(BeanAccessImpl.class);
	}

	ReflectionError illegalAccessError(IllegalAccessException e, Class<?> type) {
		return logException(
				new ReflectionError(ILLEGAL_ACCESS, e).addContextValue(TYPE,
						type), ILLEGAL_ACCESS_MESSAGE, type);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Class<?> type) {
		return logException(
				new ReflectionError(EXCEPTION_THROWN, e.getCause()).addContextValue(
						TYPE, type), EXCEPTION_THROWN_MESSAGE, type);
	}

	ReflectionError noSuchCtorError(NoSuchMethodException e, Class<?> type) {
		return logException(
				new ReflectionError(NO_STANDARD, e.getCause()).addContextValue(
						TYPE, type), NO_STANDARD_MESSAGE, type);
	}

	ReflectionError instantiationError(InstantiationException e, Class<?> type) {
		return logException(
				new ReflectionError(INSTANTIATE, e.getCause()).addContextValue(
						TYPE, type), INSTANTIATE_MESSAGE, type);
	}

	ReflectionError classNotFoundError(ClassNotFoundException e, String name) {
		return logException(
				new ReflectionError(FIND, e).addContextValue(NAME, name),
				FIND_MESSAGE, name);
	}

}
