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

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Binds the annotation access and annotation discovery.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 * 
 * @see AnnotationAccessFactory
 * @see AnnotationDiscoveryFactory
 * @see AnnotationSetFilterFactory
 */
public class AnnotationsModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(AnnotationAccess.class,
				AnnotationAccessImpl.class)
				.build(AnnotationAccessFactory.class));
		install(new FactoryModuleBuilder().implement(AnnotationDiscovery.class,
				AnnotationDiscoveryImpl.class).build(
				AnnotationDiscoveryFactory.class));
		install(new FactoryModuleBuilder().implement(AnnotationFilter.class,
				AnnotationSetFilter.class).build(
				AnnotationSetFilterFactory.class));
	}

}
