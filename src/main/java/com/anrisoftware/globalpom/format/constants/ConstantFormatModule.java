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
package com.anrisoftware.globalpom.format.constants;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the constants format factory.
 * 
 * @see ConstantFormatFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class ConstantFormatModule extends AbstractModule {

	/**
	 * @see #getFactory()
	 */
	public static ConstantFormatFactory getValueFormatFactory() {
		return getFactory();
	}

	/**
	 * Returns the values format factory.
	 * 
	 * @return the {@link ConstantFormatFactory}.
	 */
	public static ConstantFormatFactory getFactory() {
		return InjectorInstance.factory;
	}

	private static class InjectorInstance {

		static final Injector injector = createInjector(new ConstantFormatModule());

		static final ConstantFormatFactory factory = injector
				.getInstance(ConstantFormatFactory.class);
	}

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(ConstantFormat.class,
				ConstantFormat.class).build(ConstantFormatFactory.class));
	}

}
