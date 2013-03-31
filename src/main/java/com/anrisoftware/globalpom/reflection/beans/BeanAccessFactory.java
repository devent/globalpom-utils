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

import java.lang.reflect.Field;

/**
 * Factory to create a new access to a Java bean.
 * 
 * @see BeanAccess
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface BeanAccessFactory {

	/**
	 * Creates the bean access to the specified field.
	 * 
	 * @param name
	 *            the field name. The access will be either through the
	 *            getter/setter methods or through the field directly if no
	 *            getter/setter methods are defined. Must have a field in the
	 *            bean to identify the type.
	 * 
	 * @param bean
	 *            the bean {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the name or the parent object is {@code null}.
	 */
	BeanAccess create(String name, Object bean);

	/**
	 * Creates the bean access to the specified field
	 * 
	 * @param name
	 *            the field name. The access will be either through the
	 *            getter/setter methods or through the field directly if no
	 *            getter/setter methods are defined.
	 * 
	 * @param type
	 *            the {@link Class} type of the field.
	 * 
	 * @param bean
	 *            the bean {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the name or the parent object is {@code null}.
	 */
	BeanAccess create(String name, Class<?> type, Object bean);

	/**
	 * Creates the bean access to the specified field.
	 * 
	 * @param field
	 *            the {@link Field}. The access will be either through the
	 *            getter/setter methods of the field or through the field
	 *            directly if no getter/setter methods are defined.
	 * 
	 * @param bean
	 *            the bean {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the field or the parent object is {@code null}.
	 */
	BeanAccess create(Field field, Object bean);
}
