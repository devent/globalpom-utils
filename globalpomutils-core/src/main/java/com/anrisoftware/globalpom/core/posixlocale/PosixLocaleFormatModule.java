/*
 * Copyright 2013-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.core.posixlocale;

import static com.google.inject.Guice.createInjector;

import com.anrisoftware.globalpom.core.localeformat.LocaleFormatModule;
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
     *
     * @return the {@link PosixLocaleFormatFactory}.
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

        static final Injector injector = createInjector(new PosixLocaleFormatModule(), new PosixLocaleModule(),
                new LocaleFormatModule());

        static final PosixLocaleFormatFactory factory = injector.getInstance(PosixLocaleFormatFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PosixLocaleFormat.class, PosixLocaleFormat.class)
                .build(PosixLocaleFormatFactory.class));
    }

}
