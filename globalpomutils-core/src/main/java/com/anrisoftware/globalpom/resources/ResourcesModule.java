/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs path to URI or URL converter factories.
 * 
 * @see ToURLFactory
 * @see ToURIFactory
 * @see StringToURIFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class ResourcesModule extends AbstractModule {

    /**
     * Returns the path to a URL converter.
     * 
     * @return the {@link ToURLFactory}.
     */
    public static ToURLFactory getToURLFactory() {
        return Instance.injector.getInstance(ToURLFactory.class);
    }

    /**
     * Returns the path to a URI converter.
     * 
     * @return the {@link ToURIFactory}.
     */
    public static ToURIFactory getToURIFactory() {
        return Instance.injector.getInstance(ToURIFactory.class);
    }

    /**
     * Returns the string path to URI converter.
     * 
     * @return the {@link StringToURIFactory}.
     */
    public static StringToURIFactory getStringToURIFactory() {
        return Instance.injector.getInstance(StringToURIFactory.class);
    }

    private static class Instance {

        public static final Injector injector = Guice
                .createInjector(new ResourcesModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(ToURI.class, ToURI.class)
                .build(ToURIFactory.class));
        install(new FactoryModuleBuilder().implement(ToURL.class, ToURL.class)
                .build(ToURLFactory.class));
        install(new FactoryModuleBuilder().implement(StringToURI.class,
                StringToURI.class).build(StringToURIFactory.class));
    }

}
