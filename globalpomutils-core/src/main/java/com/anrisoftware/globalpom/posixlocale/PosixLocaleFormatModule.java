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
package com.anrisoftware.globalpom.posixlocale;

import static com.google.inject.Guice.createInjector;

import com.anrisoftware.globalpom.localeformat.LocaleFormatModule;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the POSIX locale format factory.
 *
 * @see PosixLocaleFormatFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.6
 */
public class PosixLocaleFormatModule extends AbstractModule {

    /**
     * @see #getFactory()
     */
    public static PosixLocaleFormatFactory getPosixLocaleFormatFactory() {
        return getFactory();
    }

    /**
     * Returns the POSIX locale format factory.
     *
     * @return the {@link PosixLocaleFormatFactory}.
     */
    public static PosixLocaleFormatFactory getFactory() {
        return InjectorInstance.factory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(
                new PosixLocaleFormatModule(), new PosixLocaleModule(),
                new LocaleFormatModule());

        static final PosixLocaleFormatFactory factory = injector
                .getInstance(PosixLocaleFormatFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PosixLocaleFormat.class,
                PosixLocaleFormat.class).build(PosixLocaleFormatFactory.class));
    }

}
