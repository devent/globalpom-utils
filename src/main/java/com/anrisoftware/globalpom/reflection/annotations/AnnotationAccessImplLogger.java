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
package com.anrisoftware.globalpom.reflection.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

	private static final String EXCEPTION_THROWN = "Exception thrown in element '%s' in @%s %s.";

	private static final String ILLEGAL_ACCESS = "Illegal access to element '%s' in @%s/%s.";

	private static final String NO_SUCH_ELEMENT = "No such element '%s' found in @%s/%s.";

	/**
	 * Creates logger for {@link AnnotationAccessImpl}.
	 */
	@Inject
	AnnotationAccessImplLogger() {
		super(AnnotationAccessImpl.class);
	}

	ReflectionError noSuchMethodError(NoSuchMethodException e,
			Class<? extends Annotation> annotationClass, Field field,
			String name) {
		return logException(
				new ReflectionError("No such element found", e)
						.addContextValue("name", name)
						.addContextValue("annotation", annotationClass)
						.addContextValue("field", field), NO_SUCH_ELEMENT,
				name, annotationClass.getName(), field);
	}

	ReflectionError illegalAccessError(IllegalAccessException e,
			Class<? extends Annotation> annotationClass, Field field,
			String name) {
		return logException(
				new ReflectionError("Illegal access to element", e)
						.addContextValue("name", name)
						.addContextValue("annotation", annotationClass)
						.addContextValue("field", field), ILLEGAL_ACCESS, name,
				annotationClass.getName(), field);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Class<? extends Annotation> annotationClass, Field field,
			String name) {
		return logException(
				new ReflectionError("Exception thrown in element", e)
						.addContextValue("name", name)
						.addContextValue("annotation", annotationClass)
						.addContextValue("field", field), EXCEPTION_THROWN,
				name, annotationClass.getName(), field);
	}
}
