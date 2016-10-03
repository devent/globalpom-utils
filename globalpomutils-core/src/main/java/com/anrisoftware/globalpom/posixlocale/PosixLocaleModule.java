/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
