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
package com.anrisoftware.globalpom.core.pointformat;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the point format factory.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
public class PointFormatModule extends AbstractModule {

    /**
     * @see #create()
     *
     * @return the {@link PointFormatFactory}.
     */
    public static PointFormatFactory createPointFormatFactory() {
        return create();
    }

    /**
     * Creates the point format factory.
     *
     * @return the {@link PointFormatFactory}.
     */
    public static PointFormatFactory create() {
        return InjectorInstance.injector.getInstance(PointFormatFactory.class);
    }

    private static class InjectorInstance {
        static final Injector injector = createInjector(new PointFormatModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PointFormat.class, PointFormat.class)
                .build(PointFormatFactory.class));
    }

}
