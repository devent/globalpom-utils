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

/**
 * Factory to set the filter and the object for which annotations should be
 * found.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationDiscoveryFactory {

	/**
	 * Creates the new annotation search.
	 * 
	 * @param bean
	 *            the {@link Object}.
	 * 
	 * @param filter
	 *            the {@link AnnotationFilter} that will be used to filter the
	 *            annotation types.
	 * 
	 * @return the new {@link AnnotationDiscovery}.
	 */
	AnnotationDiscovery create(Object bean, AnnotationFilter filter);
}
