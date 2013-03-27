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
	 * Creates the field access with the specified name in the parent object.
	 * 
	 * @param fieldName
	 *            the field name.
	 * 
	 * @param parentObject
	 *            the parent {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the field name or the parent object is {@code null}.
	 */
	BeanAccess create(String fieldName, Object parentObject);

	/**
	 * Creates the field access with the specified field in the parent object.
	 * 
	 * @param field
	 *            the {@link Field}.
	 * 
	 * @param parentObject
	 *            the parent {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the field or the parent object is {@code null}.
	 */
	BeanAccess create(Field field, Object parentObject);
}
