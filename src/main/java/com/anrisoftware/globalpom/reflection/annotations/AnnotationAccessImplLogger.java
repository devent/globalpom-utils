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
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Logging messages for {@link AnnotationAccessImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
@Singleton
class AnnotationAccessImplLogger extends AbstractLogger {

	private static final String ACCESSIBLE_OBJECT = "accessible object";

	private static final String ANNOTATION = "annotation";

	private static final String NAME = "name";

	private static final String EXCEPTION_THROWN = "Exception thrown in element";

	private static final String ILLEGAL_ACCESS = "Illegal access to element";

	private static final String NO_SUCH_ELEMENT = "No such element found";

	private static final String EXCEPTION_THROWN_MESSAGE = "Exception thrown in element '%s' in @%s %s.";

	private static final String ILLEGAL_ACCESS_MESSAGE = "Illegal access to element '%s' in @%s/%s.";

	private static final String NO_SUCH_ELEMENT_MESSAGE = "No such element '%s' found in @%s/%s.";

	/**
	 * Creates logger for {@link AnnotationAccessImpl}.
	 */
	@Inject
	AnnotationAccessImplLogger() {
		super(AnnotationAccessImpl.class);
	}

	ReflectionError noSuchMethodError(NoSuchMethodException e,
			Class<? extends Annotation> annotationClass,
			AccessibleObject accessible, String name) {
		return logException(
				new ReflectionError(NO_SUCH_ELEMENT, e)
						.addContextValue(NAME, name)
						.addContextValue(ANNOTATION, annotationClass)
						.addContextValue(ACCESSIBLE_OBJECT, accessible),
				NO_SUCH_ELEMENT_MESSAGE, name, annotationClass.getName(),
				accessible);
	}

	ReflectionError illegalAccessError(IllegalAccessException e,
			Class<? extends Annotation> annotationClass,
			AccessibleObject accessible, String name) {
		return logException(
				new ReflectionError(ILLEGAL_ACCESS, e)
						.addContextValue(NAME, name)
						.addContextValue(ANNOTATION, annotationClass)
						.addContextValue(ACCESSIBLE_OBJECT, accessible),
				ILLEGAL_ACCESS_MESSAGE, name, annotationClass.getName(),
				accessible);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Class<? extends Annotation> annotationClass,
			AccessibleObject accessible, String name) {
		return logException(
				new ReflectionError(EXCEPTION_THROWN, e)
						.addContextValue(NAME, name)
						.addContextValue(ANNOTATION, annotationClass)
						.addContextValue(ACCESSIBLE_OBJECT, accessible),
				EXCEPTION_THROWN_MESSAGE, name, annotationClass.getName(),
				accessible);
	}
}
