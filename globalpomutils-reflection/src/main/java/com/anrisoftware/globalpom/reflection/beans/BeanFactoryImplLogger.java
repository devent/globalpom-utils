/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-reflection.
 *
 * globalpomutils-reflection is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-reflection. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.beans;

import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.exception_thrown;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.exception_thrown_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.find;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.find_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.illegal_access;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.illegal_access_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.instantiate;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.instantiate_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.name_;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.no_standard;
import static com.anrisoftware.globalpom.reflection.beans.BeanFactoryImplLogger._.no_standard_message;

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

	enum _ {

		name_("name"),

		find_message("Cannot find the class '{}'."),

		find("Cannot find the class"),

		instantiate_message("Can not instantiate {}."),

		instantiate("Can not instantiate"),

		no_standard_message("No standard constructor found for {}."),

		no_standard("No standard constructor found"),

		exception_thrown_message(
				"Exception thrown in the standard constructor of {}."),

		exception_thrown("Exception thrown in the standard constructor"),

		type("type"),

		illegal_access("Illegal access to the standard constructor"),

		illegal_access_message(
				"Illegal access to the standard constructor of {}.");

		private String name;

		private _(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	/**
	 * Creates logger for {@link BeanAccessImpl}.
	 */
	BeanFactoryImplLogger() {
		super(BeanAccessImpl.class);
	}

	ReflectionError illegalAccessError(IllegalAccessException e, Class<?> type) {
		return logException(
				new ReflectionError(illegal_access, e).add(type, type),
				illegal_access_message, type);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Class<?> type) {
		return logException(
				new ReflectionError(exception_thrown, e.getCause()).add(type,
						type), exception_thrown_message, type);
	}

	ReflectionError noSuchCtorError(NoSuchMethodException e, Class<?> type) {
		return logException(
				new ReflectionError(no_standard, e.getCause()).add(type, type),
				no_standard_message, type);
	}

	ReflectionError instantiationError(InstantiationException e, Class<?> type) {
		return logException(
				new ReflectionError(instantiate, e.getCause()).add(type, type),
				instantiate_message, type);
	}

	ReflectionError classNotFoundError(ClassNotFoundException e, String name) {
		return logException(new ReflectionError(find, e).add(name_, name),
				find_message, name);
	}

}
