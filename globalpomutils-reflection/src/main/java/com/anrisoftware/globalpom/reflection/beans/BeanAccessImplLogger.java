/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.bean_null;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.exception_getter_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.exception_thrown;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.field;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.field_getter_defined;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.field_getter_defined_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.field_name_null;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.field_null;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.field_type_null;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.illegal_access_field;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.illegal_access_field_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.illegal_access_getter;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.illegal_access_getter_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.illegal_argument;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.illegal_argument_message;
import static com.anrisoftware.globalpom.reflection.beans.BeanAccessImplLogger._.unacceptable_value;
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

	enum _ {

		field("field"),

		bean("bean"),

		getter("getter"),

		field_getter_defined_message(
				"Field or field getter must be defined for {}#{}."),

		field_getter_defined("Field or field getter must be defined"),

		field_type_null("Field type cannot be null."),

		unacceptable_value("Unacceptable value for {}#{} in {}."),

		field_null("The specified field cannot be null."),

		bean_null("The specified bean object cannot be null."),

		field_name_null("The specified field name cannot be null."),

		illegal_argument("Illegal argument for getter"),

		illegal_access_field("Illegal access to field"),

		illegal_access_getter("Illegal access to field getter"),

		exception_thrown("Exception thrown in getter"),

		exception_getter_message("Exception thrown in getter {} of {}#{}."),

		illegal_argument_message("Illegal argument for getter {} for {}#{}."),

		illegal_access_field_message("Illegal access to field {}#{}."),

		illegal_access_getter_message(
				"Illegal access to field getter {} for {}#{}.");

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
	BeanAccessImplLogger() {
		super(BeanAccessImpl.class);
	}

	ReflectionError illegalAccessError(IllegalAccessException e, Object bean,
			String fieldName, Method getter) {
		return logException(
				new ReflectionError(illegal_access_getter, e).add(bean, bean)
						.add(field, fieldName).add(getter, getter),
				illegal_access_getter_message, getter, bean, fieldName);
	}

	ReflectionError illegalAccessError(IllegalAccessException e,
			String fieldName, Object bean) {
		return logException(
				new ReflectionError(illegal_access_field, e).add(bean, bean)
						.add(field, fieldName), illegal_access_field_message,
				bean, fieldName);
	}

	ReflectionError illegalArgumentError(IllegalArgumentException e,
			Object bean, String fieldName, Method getter) {
		return logException(
				new ReflectionError(illegal_argument, e).add(bean, bean)
						.add("name", fieldName).add(getter, getter),
				illegal_argument_message, getter, bean, fieldName);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Object bean, String fieldName, Method getter) {
		return logException(new ReflectionError(exception_thrown, e.getCause())
				.add(bean, bean).add(field, fieldName).add(getter, getter),
				exception_getter_message, getter, bean, fieldName);
	}

	ReflectionError neitherFieldGetter(Object bean, String fieldName) {
		return logException(
				new ReflectionError(field_getter_defined).add(bean, bean).add(
						field, fieldName), field_getter_defined_message, bean,
				fieldName);
	}

	void checkFieldName(String fieldName) {
		notNull(fieldName, field_name_null.toString());
	}

	void checkBean(Object parentObject) {
		notNull(parentObject, bean_null.toString());
	}

	void checkField(Field field) {
		notNull(field, field_null.toString());
	}

	PropertyVetoException unacceptableValueError(PropertyVetoException ex,
			Object bean, String fieldName, Method setter) {
		debug(unacceptable_value, bean, fieldName, setter);
		return ex;
	}

	void checkFieldType(Class<?> fieldType) {
		notNull(fieldType, field_type_null.toString());
	}

}
