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

import static org.apache.commons.lang3.reflect.MethodUtils.getAccessibleMethod;

import java.beans.PropertyVetoException;
import java.lang.reflect.AccessibleObject;
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

	private static final String SETTER_PREFIX = "set";

	private static final String[] GETTER_PREFIXES = { "get", "is" };

	private final BeanAccessImplLogger log;

	private final Field field;

	private final String fieldName;

	private final Object bean;

	private final Method setter;

	private final Method getter;

	private final Class<?> fieldType;

	/**
	 * @see BeanAccessFactory#create(String, Object)
	 */
	@AssistedInject
	BeanAccessImpl(BeanAccessImplLogger logger, @Assisted String fieldName,
			@Assisted Object bean) {
		this.log = logger;
		log.checkFieldName(fieldName);
		log.checkBean(bean);
		this.fieldName = fieldName;
		this.field = findField(fieldName, bean);
		this.bean = bean;
		this.getter = findGetter(fieldName, bean);
		this.fieldType = findFieldType(fieldName, bean, field, getter);
		this.setter = findSetter(fieldName, fieldType, bean);
	}

	private Class<?> findFieldType(String fieldName, Object bean, Field field,
			Method getter) {
		if (getter != null) {
			return getter.getReturnType();
		}
		if (field != null) {
			return field.getType();
		}
		throw log.neitherFieldGetter(bean, fieldName);
	}

	/**
	 * @see BeanAccessFactory#create(Field, Object)
	 */
	@AssistedInject
	BeanAccessImpl(BeanAccessImplLogger logger, @Assisted Field field,
			@Assisted Object bean) {
		this.log = logger;
		log.checkField(field);
		log.checkBean(bean);
		this.fieldName = field.getName();
		this.field = findField(fieldName, bean);
		this.fieldType = field.getType();
		this.bean = bean;
		this.setter = findSetter(fieldName, fieldType, bean);
		this.getter = findGetter(fieldName, bean);
	}

	private Field findField(String fieldName, Object bean) {
		return FieldUtils.getField(bean.getClass(), fieldName, true);
	}

	private Method findSetter(String fieldName, Class<?> type, Object bean) {
		String name = getSetterName(fieldName);
		return getAccessibleMethod(bean.getClass(), name, type);
	}

	private String getSetterName(String name) {
		StringBuilder builder = new StringBuilder();
		char nameChar = Character.toUpperCase(name.charAt(0));
		builder.append(SETTER_PREFIX);
		builder.append(nameChar);
		builder.append(name.substring(1));
		return builder.toString();
	}

	private Method findGetter(String fieldName, Object bean) {
		for (String prefix : GETTER_PREFIXES) {
			String name = getGetterName(fieldName, prefix);
			Method method = getAccessibleMethod(bean.getClass(), name);
			if (method != null) {
				return method;
			}
		}
		return null;
	}

	private String getGetterName(String name, String prefix) {
		StringBuilder builder = new StringBuilder();
		char nameChar = Character.toUpperCase(name.charAt(0));
		builder.append(prefix);
		builder.append(nameChar);
		builder.append(name.substring(1));
		return builder.toString();
	}

	@Override
	public Field getField() {
		return field;
	}

	@Override
	public Method getGetter() {
		return getter;
	}

	@Override
	public Method getSetter() {
		return setter;
	}

	@Override
	public AccessibleObject getGettterObject() {
		return getter != null ? getter : field;
	}

	@Override
	public Class<?> getType() {
		return fieldType;
	}

	@Override
	public <T> T getValue() {
		if (getter != null) {
			return getValueFromGetter(getter, bean);
		} else {
			return getValueFromField(field, bean);
		}
	}

	private <T> T getValueFromGetter(Method getter, Object bean) {
		try {
			return toType(getter.invoke(bean));
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, bean, fieldName, getter);
		} catch (IllegalArgumentException e) {
			throw log.illegalArgumentError(e, bean, fieldName, getter);
		} catch (InvocationTargetException e) {
			throw log.invocationTargetError(e, bean, fieldName, getter);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T toType(Object object) {
		return (T) object;
	}

	private <T> T getValueFromField(Field field, Object bean) {
		try {
			return toType(FieldUtils.readField(field, bean, true));
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, fieldName, bean);
		}
	}

	@Override
	public void setValue(Object value) throws PropertyVetoException {
		boolean set = setValueWithSetter(setter, value);
		if (!set) {
			setValueToField(value, field, bean);
		}
	}

	private boolean setValueWithSetter(Method setter, Object value)
			throws PropertyVetoException {
		if (setter == null) {
			return false;
		}
		try {
			setter.invoke(bean, value);
			return true;
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, bean, fieldName, setter);
		} catch (IllegalArgumentException e) {
			throw log.illegalArgumentError(e, bean, fieldName, setter);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof PropertyVetoException) {
				PropertyVetoException ex = (PropertyVetoException) e.getCause();
				throw log.unacceptableValueError(ex, bean, fieldName, setter);
			}
			throw log.invocationTargetError(e, bean, fieldName, setter);
		}
	}

	private void setValueToField(Object value, Field field, Object bean) {
		try {
			FieldUtils.writeField(field, bean, value, true);
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, fieldName, bean);
		}
	}

}
