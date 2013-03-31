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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.inject.assistedinject.Assisted;

/**
 * Read access to the elements of an annotation via reflection.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationAccessImpl implements AnnotationAccess {

	private final AnnotationAccessImplLogger log;

	private final Class<? extends Annotation> annotationClass;

	private final Field field;

	/**
	 * @see AnnotationAccessFactory#create(Class, Field)
	 */
	@Inject
	AnnotationAccessImpl(AnnotationAccessImplLogger logger,
			@Assisted Class<? extends Annotation> annotationClass,
			@Assisted Field field) {
		this.log = logger;
		this.annotationClass = annotationClass;
		this.field = field;
	}

	@Override
	public <T> T getValue() {
		return getValue("value");
	}

	@Override
	public <T> T getValue(String name) {
		Annotation a = field.getAnnotation(annotationClass);
		try {
			return asType(name, a);
		} catch (NoSuchMethodException e) {
			throw log.noSuchMethodError(e, annotationClass, field, name);
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, annotationClass, field, name);
		} catch (InvocationTargetException e) {
			throw log.invocationTargetError(e, annotationClass, field, name);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T asType(String name, Annotation a)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return (T) MethodUtils.invokeMethod(a, name);
	}

}
