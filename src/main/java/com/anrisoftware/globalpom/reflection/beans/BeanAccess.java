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

import java.lang.reflect.Field;

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
	 * @return the {@link Field}.
	 * 
	 * @throws NullPointerException
	 *             if the specified field name or parent object is {@code null}.
	 * 
	 */
	Field getField();

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
	 */
	void setValue(Object value);

}
