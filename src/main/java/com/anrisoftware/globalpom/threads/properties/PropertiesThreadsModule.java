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
package com.anrisoftware.globalpom.threads.properties;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Install the properties threads factory.
 * 
 * @see PropertiesThreadsFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class PropertiesThreadsModule extends AbstractModule {

    /**
     * Returns the properties threads factory.
     * 
     * @return the {@link PropertiesThreadsFactory}.
     * 
     * @since 1.11
     */
    public static PropertiesThreadsFactory getPropertiesThreadsFactory() {
        return instance.injector.getInstance(PropertiesThreadsFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice
                .createInjector(new PropertiesThreadsModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PropertiesThreads.class,
                PropertiesThreads.class).build(PropertiesThreadsFactory.class));
        install(new FactoryModuleBuilder().implement(ThreadingProperties.class,
                ThreadingProperties.class).build(
                ThreadingPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(
                CachedThreadingProperties.class,
                CachedThreadingProperties.class).build(
                CachedThreadingPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(
                FixedThreadingProperties.class, FixedThreadingProperties.class)
                .build(FixedThreadingPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(
                SingleThreadingProperties.class,
                SingleThreadingProperties.class).build(
                SingleThreadingPropertiesFactory.class));
    }
}
