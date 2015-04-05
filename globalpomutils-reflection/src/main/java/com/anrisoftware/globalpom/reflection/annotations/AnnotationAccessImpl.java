/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.inject.assistedinject.Assisted;

/**
 * Read access to the elements of an annotation via reflection.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationAccessImpl implements AnnotationAccess {

	private static final String ACCESSIBLE_OBJECT = "accessible object";

	private static final String ANNOTATION = "annotation";

	private final AnnotationAccessImplLogger log;

	private final Class<? extends Annotation> annotationClass;

	private final AccessibleObject accessible;

	/**
	 * @see AnnotationAccessFactory#create(Class, AccessibleObject)
	 * 
	 * @since 1.5
	 */
	@Inject
	AnnotationAccessImpl(AnnotationAccessImplLogger logger,
			@Assisted Class<? extends Annotation> annotationClass,
			@Assisted AccessibleObject accessible) {
		this.log = logger;
		this.annotationClass = annotationClass;
		this.accessible = accessible;
	}

	@Override
	public <T> T getValue() {
		return getValue("value");
	}

	@Override
	public <T> T getValue(String name) {
		Annotation a = getAnnotation();
		log.checkAnnotation(a, annotationClass, accessible);
		try {
			return asType(name, a);
		} catch (NoSuchMethodException e) {
			throw log.noSuchMethodError(e, annotationClass, accessible, name);
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, annotationClass, accessible, name);
		} catch (InvocationTargetException e) {
			throw log.invocationTargetError(e, annotationClass, accessible,
					name);
		}
	}

	@Override
	public boolean haveValue(String name) {
		Annotation a = getAnnotation();
		log.checkAnnotation(a, annotationClass, accessible);
		return MethodUtils.getAccessibleMethod(annotationClass, name) != null;
	}

	@Override
	public Annotation getAnnotation() {
		return accessible.getAnnotation(annotationClass);
	}

	@SuppressWarnings("unchecked")
	private <T> T asType(String name, Annotation a)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return (T) MethodUtils.invokeMethod(a, name);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(ANNOTATION, annotationClass)
				.append(ACCESSIBLE_OBJECT, accessible).toString();
	}
}
