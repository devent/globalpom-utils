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
package com.anrisoftware.globalpom.threads.properties.internal;

import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads;
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory;
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
        static final Injector injector = Guice.createInjector(new PropertiesThreadsModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PropertiesThreads.class, PropertiesThreadsImpl.class)
                .build(PropertiesThreadsFactory.class));
        install(new FactoryModuleBuilder().implement(ThreadingProperties.class, DefaultThreadingProperties.class)
                .build(DefaultThreadingPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(CachedThreadingProperties.class, CachedThreadingProperties.class)
                .build(CachedThreadingPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(FixedThreadingProperties.class, FixedThreadingProperties.class)
                .build(FixedThreadingPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(SingleThreadingProperties.class, SingleThreadingProperties.class)
                .build(SingleThreadingPropertiesFactory.class));
    }
}
