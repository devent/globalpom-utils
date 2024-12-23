/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.resources;

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
