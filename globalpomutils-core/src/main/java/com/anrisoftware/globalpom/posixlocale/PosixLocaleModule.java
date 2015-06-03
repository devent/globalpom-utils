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
package com.anrisoftware.globalpom.posixlocale;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the POSIX locale factory.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.6
 */
public class PosixLocaleModule extends AbstractModule {

    /**
     * Returns the POSIX locale factory.
     *
     * @return the {@link PosixLocaleFactory}.
     */
    public static PosixLocaleFactory getPosixLocaleFactory() {
        return getFactory();
    }

    /**
     * Returns the POSIX locale factory.
     *
     * @return the {@link PosixLocaleFactory}.
     */
    public static PosixLocaleFactory getFactory() {
        return InjectorInstance.factory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new PosixLocaleModule());

        static final PosixLocaleFactory factory = injector
                .getInstance(PosixLocaleFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PosixLocale.class,
                PosixLocale.class).build(PosixLocaleFactory.class));
    }

}
