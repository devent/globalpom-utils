/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of groovybash-core.
 * 
 * groovybash-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * groovybash-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * groovybash-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Install the threads based on laoded properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class PropertiesThreadsModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(PropertiesThreads.class,
				PropertiesThreads.class).build(PropertiesThreadsFactory.class));
		install(new FactoryModuleBuilder().implement(ThreadingProperties.class,
				ThreadingProperties.class).build(
				ThreadingPropertiesFactory.class));
		install(new CachedThreadingModule());
		install(new FactoryModuleBuilder().implement(
				FixedThreadingProperties.class, FixedThreadingProperties.class)
				.build(FixedThreadingPropertiesFactory.class));
		install(new FactoryModuleBuilder().implement(
				SingleThreadingProperties.class,
				SingleThreadingProperties.class).build(
				SingleThreadingPropertiesFactory.class));
	}
}