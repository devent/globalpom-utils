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

import static org.apache.commons.lang3.Validate.notNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Logging messages for {@link BeanAccessImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanAccessImplLogger extends AbstractLogger {

	private static final String FIELD_NULL = "The specified field cannot be null.";

	private static final String PARENT_OBJECT_NULL = "The specified parent object cannot be null.";

	private static final String FIELD_NAME_NULL = "The specified field name cannot be null.";

	private static final String ILLEGAL_ARGUMENT = "Illegal argument for getter";

	private static final String ILLEGAL_ACCESS_FIELD = "Illegal access to field";

	private static final String ILLEGAL_ACCESS_GETTER = "Illegal access to field getter";

	private static final String EXCEPTION_THROWN = "Exception thrown in getter";

	private static final String EXCEPTION_GETTER_MESSAGE = "Exception thrown in getter of %s#%s.";

	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument for getter for %s#%s.";

	private static final String ILLEGAL_ACCESS_FIELD_MESSAGE = "Illegal access to field %s#%s.";

	private static final String ILLEGAL_ACCESS_GETTER_MESSAGE = "Illegal access to field getter %s#%s.";

	/**
	 * Creates logger for {@link BeanAccessImpl}.
	 */
	BeanAccessImplLogger() {
		super(BeanAccessImpl.class);
	}

	ReflectionError illegalAccessError(IllegalAccessException e,
			Object parentObject, String name) {
		return logException(new ReflectionError(ILLEGAL_ACCESS_GETTER, e)
				.addContextValue("parent object", parentObject)
				.addContextValue("name", name), ILLEGAL_ACCESS_GETTER_MESSAGE,
				parentObject, name);
	}

	ReflectionError illegalAccessError(IllegalAccessException e, Field field,
			Object parentObject) {
		return logException(new ReflectionError(ILLEGAL_ACCESS_FIELD, e)
				.addContextValue("parent object", parentObject)
				.addContextValue("field", field), ILLEGAL_ACCESS_FIELD_MESSAGE,
				parentObject, field);
	}

	ReflectionError illegalArgumentError(IllegalArgumentException e,
			Object parentObject, String name) {
		return logException(new ReflectionError(ILLEGAL_ARGUMENT, e)
				.addContextValue("parent object", parentObject)
				.addContextValue("name", name), ILLEGAL_ARGUMENT_MESSAGE,
				parentObject, name);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Object parentObject, String name) {
		return logException(new ReflectionError(EXCEPTION_THROWN, e.getCause())
				.addContextValue("parent object", parentObject)
				.addContextValue("name", name), EXCEPTION_GETTER_MESSAGE,
				parentObject, name);
	}

	void checkFieldName(String fieldName) {
		notNull(fieldName, FIELD_NAME_NULL);
	}

	void checkParentObject(Object parentObject) {
		notNull(parentObject, PARENT_OBJECT_NULL);
	}

	void checkField(Field field) {
		notNull(field, FIELD_NULL);
	}

}
