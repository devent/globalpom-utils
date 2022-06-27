/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.inetsocketaddressformat;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the Internet socket format factory.
 *
 * @see InetSocketAddressFormatFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class InetSocketAddressFormatModule extends AbstractModule {

    /**
     * @see #getFactory()
     *
     * @return the {@link InetSocketAddressFormatFactory}
     */
    public static InetSocketAddressFormatFactory getValueFormatFactory() {
        return getFactory();
    }

    /**
     * Returns the values format factory.
     *
     * @return the {@link InetSocketAddressFormatFactory}.
     */
    public static InetSocketAddressFormatFactory getFactory() {
        return InjectorInstance.factory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new InetSocketAddressFormatModule());

        static final InetSocketAddressFormatFactory factory = injector
                .getInstance(InetSocketAddressFormatFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(InetSocketAddressFormat.class, InetSocketAddressFormat.class)
                .build(InetSocketAddressFormatFactory.class));
    }

}
