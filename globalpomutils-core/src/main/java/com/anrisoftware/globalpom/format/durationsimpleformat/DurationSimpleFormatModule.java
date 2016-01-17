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
package com.anrisoftware.globalpom.format.durationsimpleformat;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the simple duration format factory.
 *
 * @see DurationSimpleFormatFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
public class DurationSimpleFormatModule extends AbstractModule {

    /**
     * @see #getFactory()
     */
    public static DurationSimpleFormatFactory getSimpleDurationFormatFactory() {
        return getFactory();
    }

    /**
     * Returns the simple duration format factory.
     *
     * @return the {@link DurationSimpleFormatFactory}.
     */
    public static DurationSimpleFormatFactory getFactory() {
        return InjectorInstance.factory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new DurationSimpleFormatModule());

        static final DurationSimpleFormatFactory factory = injector
                .getInstance(DurationSimpleFormatFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(DurationSimpleFormat.class,
                DurationSimpleFormat.class).build(DurationSimpleFormatFactory.class));
    }

}
