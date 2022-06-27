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
package com.anrisoftware.globalpom.math.format.measurement;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the value format factory.
 *
 * @see ValueFormatFactory
 * @see MeasureFormatFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class MeasurementFormatModule extends AbstractModule {

    /**
     * Returns the values format factory.
     *
     * @return the {@link ValueFormatFactory}.
     */
    public static ValueFormatFactory getValueFormatFactory() {
        return InjectorInstance.valueFactory;
    }

    /**
     * Returns the measure format factory.
     *
     * @return the {@link MeasureFormatFactory}.
     */
    public static MeasureFormatFactory getMeasureFormatFactory() {
        return InjectorInstance.measureFactory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new MeasurementFormatModule());

        static final ValueFormatFactory valueFactory = injector.getInstance(ValueFormatFactory.class);

        static final MeasureFormatFactory measureFactory = injector.getInstance(MeasureFormatFactory.class);

    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(ValueFormat.class, ValueFormat.class)
                .build(ValueFormatFactory.class));
        install(new FactoryModuleBuilder().implement(MeasureFormat.class, MeasureFormat.class)
                .build(MeasureFormatFactory.class));
    }

}
