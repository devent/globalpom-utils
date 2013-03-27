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

import static org.apache.commons.lang3.reflect.MethodUtils.getAccessibleMethod;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Access the fields of an bean object.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanAccessImpl implements BeanAccess {

	private final BeanAccessImplLogger log;

	private final Field field;

	private final Object parentObject;

	/**
	 * @see BeanAccessFactory#create(String, Object)
	 */
	@AssistedInject
	BeanAccessImpl(BeanAccessImplLogger logger, @Assisted String fieldName,
			@Assisted Object parentObject) {
		this.log = logger;
		log.checkFieldName(fieldName);
		log.checkParentObject(parentObject);
		this.parentObject = parentObject;
		this.field = toField(fieldName, parentObject);
	}

	/**
	 * @see BeanAccessFactory#create(Field, Object)
	 */
	@AssistedInject
	BeanAccessImpl(BeanAccessImplLogger logger, @Assisted Field field,
			@Assisted Object parentObject) {
		this.log = logger;
		log.checkField(field);
		log.checkParentObject(parentObject);
		this.parentObject = parentObject;
		this.field = field;
	}

	private Field toField(String fieldName, Object parentObject) {
		return FieldUtils.getField(parentObject.getClass(), fieldName, true);
	}

	@Override
	public Field getField() {
		return field;
	}

	@Override
	public <T> T getValue() {
		T value = getValueFromGetter(field, parentObject);
		if (value == null) {
			value = getValueFromField(field, parentObject);
		}
		return value;
	}

	private <T> T getValueFromGetter(Field field, Object parentObject) {
		String name = getGetterName(field);
		Method method = getAccessibleMethod(parentObject.getClass(), name);
		if (method == null) {
			return null;
		}
		try {
			return toType(method.invoke(parentObject));
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, parentObject, name);
		} catch (IllegalArgumentException e) {
			throw log.illegalArgumentError(e, parentObject, name);
		} catch (InvocationTargetException e) {
			throw log.invocationTargetError(e, parentObject, name);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T toType(Object object) {
		return (T) object;
	}

	private String getGetterName(Field field) {
		StringBuilder builder = new StringBuilder();
		String name = field.getName();
		char nameChar = Character.toUpperCase(name.charAt(0));
		builder.append("get");
		builder.append(nameChar);
		builder.append(name.substring(1));
		return builder.toString();
	}

	private <T> T getValueFromField(Field field, Object parentObject) {
		try {
			return toType(FieldUtils.readField(field, parentObject, true));
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, field, parentObject);
		}
	}

	@Override
	public void setValue(Object value) {
		boolean set = setValueWithSetter(value, field, parentObject);
		if (!set) {
			setValueFromField(value, field, parentObject);
		}
	}

	private boolean setValueWithSetter(Object value, Field field,
			Object parentObject) {
		String name = getSetterName(field);
		Class<?> cls = parentObject.getClass();
		Method method = getAccessibleMethod(cls, name, value.getClass());
		if (method == null) {
			return false;
		}
		try {
			method.invoke(parentObject, value);
			return true;
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, parentObject, name);
		} catch (IllegalArgumentException e) {
			throw log.illegalArgumentError(e, parentObject, name);
		} catch (InvocationTargetException e) {
			throw log.invocationTargetError(e, parentObject, name);
		}
	}

	private String getSetterName(Field field) {
		StringBuilder builder = new StringBuilder();
		String name = field.getName();
		char nameChar = Character.toUpperCase(name.charAt(0));
		builder.append("set");
		builder.append(nameChar);
		builder.append(name.substring(1));
		return builder.toString();
	}

	private void setValueFromField(Object value, Field field,
			Object parentObject) {
		try {
			FieldUtils.writeField(field, parentObject, value, true);
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, field, parentObject);
		}
	}

}
