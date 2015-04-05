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

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Search an object's fields and methods for annotations and if an annotation is
 * found it will inform the listeners.
 * 
 * @see AnnotationFilter
 * @see AnnotationListener
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationDiscovery extends
		Callable<Collection<AnnotationBean>> {

	/**
	 * Returns the filter that tests if the annotation have the correct type.
	 * 
	 * @return the {@link AnnotationFilter}.
	 */
	AnnotationFilter getFilter();

	/**
	 * Adds a new listener that is informed if a field or method is found with
	 * the expected annotation.
	 * 
	 * @param l
	 *            the {@link AnnotationListener}.
	 */
	void addListener(AnnotationListener l);

	/**
	 * Removed a listener that was informed if a field or method is found with
	 * the expected annotation.
	 * 
	 * @param l
	 *            the {@link AnnotationListener}.
	 */
	void removeListener(AnnotationListener l);

	/**
	 * Searched for annotations and returns the fields or methods that are
	 * annotated.
	 * 
	 * @return the {@link Collection} of the annotation bean. The collection
	 *         should not contain duplicates and the order of the fields and
	 *         methods should be in that they are found.
	 */
	@Override
	Collection<AnnotationBean> call();

}
