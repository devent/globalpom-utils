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
