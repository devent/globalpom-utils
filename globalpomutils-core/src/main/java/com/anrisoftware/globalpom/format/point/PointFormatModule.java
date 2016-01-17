/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.point;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the point format factory.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
public class PointFormatModule extends AbstractModule {

	/**
	 * @see #create()
	 */
	public static PointFormatFactory createPointFormatFactory() {
		return create();
	}

	/**
	 * Creates the point format factory.
	 * 
	 * @return the {@link PointFormatFactory}.
	 */
	public static PointFormatFactory create() {
		return InjectorInstance.injector.getInstance(PointFormatFactory.class);
	}

	private static class InjectorInstance {
		static final Injector injector = createInjector(new PointFormatModule());
	}

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(PointFormat.class,
				PointFormat.class).build(PointFormatFactory.class));
	}

}
