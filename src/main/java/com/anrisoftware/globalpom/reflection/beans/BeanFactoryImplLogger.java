/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prefdialog-reflection.
 *
 * prefdialog-reflection is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * prefdialog-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with prefdialog-reflection. If not, see <http://www.gnu.org/licenses/>.
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

	/**
	 * Creates logger for {@link BeanAccessImpl}.
	 */
	BeanFactoryImplLogger() {
		super(BeanAccessImpl.class);
	}

	ReflectionError illegalAccessError(IllegalAccessException e, Class<?> type) {
		ReflectionError ex = new ReflectionError(
				"Illegal access to the standard constructor", e)
				.addContextValue("type", type);
		log.error(ex.getLocalizedMessage());
		return ex;
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Class<?> type) {
		ReflectionError ex = new ReflectionError(
				"Exception thrown in the standard constructor", e.getCause())
				.addContextValue("type", type);
		log.error(ex.getLocalizedMessage());
		return ex;
	}

	ReflectionError noSuchCtorError(NoSuchMethodException e, Class<?> type) {
		ReflectionError ex = new ReflectionError(
				"No standard constructor found", e.getCause()).addContextValue(
				"type", type);
		log.error(ex.getLocalizedMessage());
		return ex;
	}

	ReflectionError instantiationError(InstantiationException e, Class<?> type) {
		ReflectionError ex = new ReflectionError("Can not instantiate",
				e.getCause()).addContextValue("type", type);
		log.error(ex.getLocalizedMessage());
		return ex;
	}

	ReflectionError classNotFoundError(ClassNotFoundException e, String typeName) {
		ReflectionError ex = new ReflectionError("Cannot find the class", e)
				.addContextValue("name", typeName);
		log.error(ex.getLocalizedMessage());
		return ex;
	}

}
