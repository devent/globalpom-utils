/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.accessible_object;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.annotation;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.exception_thrown;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.exception_thrown_message;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.illegal_access;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.illegal_access_message;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.name_;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.no_annotation;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.no_such_element;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessImplLogger._.no_such_element_message;
import static org.apache.commons.lang3.Validate.notNull;

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

	enum _ {

		no_annotation("Accessible object %s must have annotation %s."),

		accessible_object("accessible object"),

		annotation("annotation"),

		name_("name"),

		exception_thrown("Exception thrown in element"),

		illegal_access("Illegal access to element"),

		no_such_element("No such element found"),

		exception_thrown_message("Exception thrown in element '{}' in @{} {}."),

		illegal_access_message("Illegal access to element '{}' in @{}/{}."),

		no_such_element_message("No such element '{}' found in @{}/{}.");

		private String name;

		private _(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

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
				new ReflectionError(no_such_element, e).add(name_, name)
						.add(annotation, annotationClass)
						.add(accessible_object, accessible),
				no_such_element_message, name, annotationClass.getName(),
				accessible);
	}

	ReflectionError illegalAccessError(IllegalAccessException e,
			Class<? extends Annotation> annotationClass,
			AccessibleObject accessible, String name) {
		return logException(
				new ReflectionError(illegal_access, e).add(name_, name)
						.add(annotation, annotationClass)
						.add(accessible_object, accessible),
				illegal_access_message, name, annotationClass.getName(),
				accessible);
	}

	ReflectionError invocationTargetError(InvocationTargetException e,
			Class<? extends Annotation> annotationClass,
			AccessibleObject accessible, String name) {
		return logException(
				new ReflectionError(exception_thrown, e).add(name_, name)
						.add(annotation, annotationClass)
						.add(accessible_object, accessible),
				exception_thrown_message, name, annotationClass.getName(),
				accessible);
	}

	void checkAnnotation(Annotation a,
			Class<? extends Annotation> annotationClass,
			AccessibleObject accessible) {
		notNull(a, no_annotation.toString(), accessible, annotationClass);
	}
}
