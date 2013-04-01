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
package com.anrisoftware.globalpom.reflection.annotations;

import java.lang.annotation.Annotation;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Read access to the elements of an annotation.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationAccess {

	/**
	 * Returns the value of the annotation element "value".
	 * 
	 * <pre>
	 * &#064;SomeAnnotation(&quot;some-value&quot;)
	 * FomeField
	 * 
	 * String value = access.getValue();
	 * assert value == &quot;some-value&quot;;
	 * </pre>
	 * 
	 * @return the value of the annotation field.
	 * 
	 * @throws ReflectionError
	 *             if the element was not found in the annotation, the element
	 *             can not be accessed or the element throws an exception.
	 */
	<T> T getValue();

	/**
	 * Returns the value of an annotation element.
	 * 
	 * <pre>
	 * &#064;SomeAnnotation(element = &quot;some-value&quot;)
	 * FomeField
	 * 
	 * String value = access.getValue("element");
	 * assert value == &quot;some-value&quot;;
	 * </pre>
	 * 
	 * @param name
	 *            the name of the annotation element.
	 * 
	 * @return the value of the annotation field.
	 * 
	 * @throws ReflectionError
	 *             if the element was not found in the annotation, the element
	 *             can not be accessed or the element throws an exception.
	 */
	<T> T getValue(String name);

	/**
	 * Returns the annotation.
	 * 
	 * @return the {@link Annotation}.
	 * 
	 * @since 1.5
	 */
	Annotation getAnnotation();
}
