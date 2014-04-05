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

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Factory to create an annotation filter from a collection of annotation types.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationSetFilterFactory {

	/**
	 * Creates a new filter from the specified annotation type.
	 * 
	 * @param annotation
	 *            the annotation {@link Class} type.
	 * 
	 * @return the {@link AnnotationFilter}.
	 * 
	 * @since 1.5
	 */
	AnnotationFilter create(Class<? extends Annotation> annotation);

	/**
	 * Creates a new filter from the specified annotation types.
	 * 
	 * @param annotations
	 *            {@link Collection} that returns the annotation types.
	 * 
	 * @return the {@link AnnotationFilter}.
	 */
	AnnotationFilter create(
			Collection<? extends Class<? extends Annotation>> annotations);
}
