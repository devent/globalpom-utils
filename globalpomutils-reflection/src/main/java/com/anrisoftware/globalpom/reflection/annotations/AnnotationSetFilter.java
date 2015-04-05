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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * A filter that only accepts predefined {@link Annotation}s from a given
 * collection.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationSetFilter implements AnnotationFilter {

	private final Set<Class<? extends Annotation>> annotations;

	/**
	 * @see AnnotationSetFilterFactory#create(Class)
	 */
	@AssistedInject
	AnnotationSetFilter(@Assisted Class<? extends Annotation> annotation) {
		this.annotations = new HashSet<Class<? extends Annotation>>();
		annotations.add(annotation);
	}

	/**
	 * @see AnnotationSetFilterFactory#create(Iterable)
	 */
	@AssistedInject
	AnnotationSetFilter(
			@Assisted Collection<? extends Class<? extends Annotation>> annotations) {
		this.annotations = new HashSet<Class<? extends Annotation>>(annotations);
	}

	@Override
	public boolean accept(Annotation annotation) {
		for (Class<? extends Annotation> a : annotations) {
			if (a.isInstance(annotation)) {
				return true;
			}
		}
		return false;
	}

}
