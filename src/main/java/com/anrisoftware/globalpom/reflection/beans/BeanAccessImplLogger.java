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

import static org.apache.commons.lang3.Validate.notNull;

import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Logging messages for {@link BeanAccessImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanAccessImplLogger extends AbstractLogger {

	private static final String FIELD_TYPE_NULL = "Field type cannot be null.";

	private static final String UNACCEPTABLE_VALUE = "Unacceptable value for %s#%s in %s.";

	private static final String FIELD_NULL = "The specified field cannot be null.";

	private static final String BEAN_NULL = "The specified bean object cannot be null.";

	private static final String FIELD_NAME_NULL = "The specified field name cannot be null.";

	private static final String ILLEGAL_ARGUMENT = "Illegal argument for getter";

	private static final String ILLEGAL_ACCESS_FIELD = "Illegal access to field";

	private static final String ILLEGAL_ACCESS_GETTER = "Illegal access to field getter";

	private static final String EXCEPTION_THROWN = "Exception thrown in getter";

	private static final String EXCEPTION_GETTER_MESSAGE = "Exception thrown in getter %3$s of %1$s#%2$s.";

	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument for getter %3$s for %1$s#%2$s.";

	private static final String ILLEGAL_ACCESS_FIELD_MESSAGE = "Illegal access to field %s#%s.";

	private static final String ILLEGAL_ACCESS_GETTER_MESSAGE = "Illegal access to field getter %3$s for %1$s#%2$s.";

	/**
	 * Creates logger for {@link BeanAccessImpl}.
	 */
	BeanAccessImplLogger() {
		super(BeanAccessImpl.class);
	}

	ReflectionError illegalAccessError(IllegalAccessException e, Object bean,
			String fieldName, Method getter) {
		return logException(
				new ReflectionError(ILLEGAL_ACCESS_GETTER, e)
						.addContextValue("bean", bean)
						.addContextValue("field", fieldName)
						.addContextValue("getter", getter),
				ILLEGAL_ACCESS_GETTER_MESSAGE, bean, fieldName, getter);
	}

	ReflectionError illegalAccessError(IllegalAccessException e,
			String fieldName, Object bean) {
		return logException(
				new ReflectionError(ILLEGAL_ACCESS_FIELD, e).addContextValue(
						"bean", bean).addContextValue("field", fieldName),
				ILLEGAL_ACCESS_FIELD_MESSAGE, bean, fieldName);
	}

	ReflectionError illegalArgumentError(IllegalArgumentException e,
			Object bean, String fieldName, Method getter) {
		return logException(
				new ReflectionError(ILLEGAL_ARGUMENT, e)
						.addContextValue("bean", bean)
						.addContextValue("name", fieldName)
						.addContextValue("getter", getter),
				ILLEGAL_ARGUMENT_MESSAGE, bean, fieldName, getter);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Object bean, String fieldName, Method getter) {
		return logException(
				new ReflectionError(EXCEPTION_THROWN, e.getCause())
						.addContextValue("bean", bean)
						.addContextValue("field", fieldName)
						.addContextValue("getter", getter),
				EXCEPTION_GETTER_MESSAGE, bean, fieldName, getter);
	}

	void checkFieldName(String fieldName) {
		notNull(fieldName, FIELD_NAME_NULL);
	}

	void checkBean(Object parentObject) {
		notNull(parentObject, BEAN_NULL);
	}

	void checkField(Field field) {
		notNull(field, FIELD_NULL);
	}

	PropertyVetoException unacceptableValueError(PropertyVetoException ex,
			Object bean, String fieldName, Method setter) {
		return logException(ex, UNACCEPTABLE_VALUE, bean, fieldName, setter);
	}

	void checkFieldType(Class<?> fieldType) {
		notNull(fieldType, FIELD_TYPE_NULL);
	}

}
