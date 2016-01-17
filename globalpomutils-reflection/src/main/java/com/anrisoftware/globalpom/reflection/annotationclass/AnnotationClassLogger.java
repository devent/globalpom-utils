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

import static org.apache.commons.lang3.Validate.notBlank;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link AnnotationClass}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
class AnnotationClassLogger extends AbstractLogger {

	private static final String ONE_CLASS_TYPES = "More then one class types are specified for attribute '{}'.";
	private static final String NAME_NULL = "Attribute name can not be null or empty.";

	/**
	 * Create logger for {@link AnnotationClass}.
	 */
	public AnnotationClassLogger() {
		super(AnnotationClass.class);
	}

	void checkAttributeName(String name) {
		notBlank(name, NAME_NULL);
	}

	void checkClassAttribute(Class<?>[] type, String name) {
		if (type.length > 1) {
			log.warn(ONE_CLASS_TYPES, name);
		}
	}
}
