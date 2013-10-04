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

import static com.anrisoftware.globalpom.reflection.annotations.AnnotationDiscoveryImplLogger._.annotation_discovery;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationDiscoveryImplLogger._.getter_message;
import static com.anrisoftware.globalpom.reflection.annotations.AnnotationDiscoveryImplLogger._.method_getter;

import java.lang.reflect.Method;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Logging messages for {@link AnnotationDiscoveryImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationDiscoveryImplLogger extends AbstractLogger {

	enum _ {

		method("method"),

		bean("bean"),

		annotation_discovery("annotation discovery"),

		getter_message("Method is not getter {}#{}."),

		method_getter("Method is not getter");

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
	 * Create logger for {@link AnnotationDiscoveryImpl}.
	 */
	public AnnotationDiscoveryImplLogger() {
		super(AnnotationDiscoveryImpl.class);
	}

	ReflectionError methodNotGetter(AnnotationDiscoveryImpl a, Object bean,
			Method method) {
		return logException(
				new ReflectionError(method_getter).add(annotation_discovery, a)
						.add(bean, bean).add(method, method), getter_message,
				bean, method);
	}
}
