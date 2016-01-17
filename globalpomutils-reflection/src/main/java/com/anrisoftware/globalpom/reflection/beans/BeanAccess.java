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

import java.beans.PropertyVetoException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Access the fields of an bean object.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface BeanAccess {

	/**
	 * Returns the field.
	 * 
	 * @return the {@link Field} or {@code null} if there is no field.
	 */
	Field getField();

	/**
	 * Returns the setter method of the bean. The setter method is a method with
	 * the signature:
	 * 
	 * <pre>
	 * public void setAttribute(Type value);
	 * </pre>
	 * 
	 * @return the {@link Method} of {@code null} if there is no setter method.
	 */
	Method getSetter();

	/**
	 * Returns the getter method of the bean. The getter method is a method with
	 * the signature:
	 * 
	 * <pre>
	 * public Type getAttribute();
	 * </pre>
	 * 
	 * @return the {@link Method} of {@code null} if there is no getter method.
	 */
	Method getGetter();

	/**
	 * Returns the getter method or the field.
	 * 
	 * @return the {@link AccessibleObject}.
	 * 
	 * @since 1.5
	 */
	AccessibleObject getGettterObject();

	/**
	 * Returns the type of the bean. The type is either the return type of the
	 * getter method or the field type.
	 * 
	 * @return the {@link Class} type.
	 * 
	 * @since 1.5
	 */
	Class<?> getType();

	/**
	 * Return the value from a field with the specified name.
	 * <p>
	 * If a getter for this field is defined it tries to use the getter first. A
	 * getter is a method with the pattern <code>FieldType getFieldName()</code>
	 * where <code>FieldType</code> the type of the field and
	 * <code>FieldName</code> the name of the field is.
	 * <p>
	 * If no such getter is defined, the value of the field will be returned by
	 * using reflection.
	 * 
	 * @return the value of the field.
	 * 
	 * @throws NullPointerException
	 *             if the specified field name or parent object is {@code null}.
	 * 
	 * @throws ReflectionError
	 *             if the field can not be found in the parent object, the field
	 *             can not be accessed or the getter of the field throws an
	 *             exception.
	 */
	<T> T getValue();

	/**
	 * Sets the value to the field.
	 * 
	 * @param value
	 *            the value to be set.
	 * 
	 * @throws PropertyVetoException
	 *             if the value is unacceptable by the bean.
	 */
	void setValue(Object value) throws PropertyVetoException;

}
