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
package com.anrisoftware.globalpom.reflection.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotation discovered.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationBean {

	/**
	 * Returns the annotation that was discovered.
	 * 
	 * @return the {@link Annotation}.
	 */
	Annotation getAnnotation();

	/**
	 * Returns the bean member where the annotation was found.
	 * 
	 * @return the {@link AccessibleObject}.
	 */
	AccessibleObject getMember();

	/**
	 * Returns the bean field where the annotation was found.
	 * 
	 * @return the {@link Field}.
	 * 
	 * @throws ClassCastException
	 *             if the bean member is not a field.
	 */
	Field getField();

	/**
	 * Returns the bean method where the annotation was found.
	 * 
	 * @return the {@link Method}.
	 * 
	 * @throws ClassCastException
	 *             if the bean member is not a method.
	 */
	Method getMethod();

	/**
	 * Returns the value that the annotation is found.
	 * 
	 * @return the {@link Object} value or {@code null}.
	 */
	Object getValue();

}