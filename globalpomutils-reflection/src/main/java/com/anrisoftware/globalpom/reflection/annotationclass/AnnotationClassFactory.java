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
package com.anrisoftware.globalpom.reflection.annotationclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Factory to create the annotation class builder.
 * 
 * @see AnnotationClass
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface AnnotationClassFactory {

	/**
	 * Creates the annotation class builder.
	 * 
	 * @param parent
	 *            the parent {@link Object} where the field can be found.
	 * 
	 * @param annotation
	 *            the {@link Class} type of the annotation.
	 * 
	 * @param object
	 *            the {@link Field} or the {@link Method} where the annotation
	 *            was added.
	 * 
	 * @return the {@link AnnotationClass}.
	 */
	AnnotationClass<?> create(Object parent,
			Class<? extends Annotation> annotation, AccessibleObject object);
}
