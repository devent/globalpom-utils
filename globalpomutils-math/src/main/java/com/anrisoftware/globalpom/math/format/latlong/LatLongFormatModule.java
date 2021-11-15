/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format.latlong;

/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.google.inject.Guice.createInjector;

import com.anrisoftware.globalpom.math.format.degree.DegreeFormatModule;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the latitude/longitude format factory.
 * 
 * @see LatitudeFormatFactory
 * @see LongitudeFormatFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class LatLongFormatModule extends AbstractModule {

    /**
     * Returns the latitude format factory.
     * 
     * @return the {@link LatitudeFormatFactory}.
     */
    public static LatitudeFormatFactory getLatitudeFormatFactory() {
        return InjectorInstance.latitudeFormatfactory;
    }

    /**
     * Returns the longitude format factory.
     * 
     * @return the {@link LongitudeFormatFactory}.
     */
    public static LongitudeFormatFactory getLongitudeFormatFactory() {
        return InjectorInstance.longitudeFormatfactory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(
                new LatLongFormatModule(), new DegreeFormatModule());

        static final LatitudeFormatFactory latitudeFormatfactory = injector
                .getInstance(LatitudeFormatFactory.class);

        static final LongitudeFormatFactory longitudeFormatfactory = injector
                .getInstance(LongitudeFormatFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(LatitudeFormat.class,
                LatitudeFormat.class).build(LatitudeFormatFactory.class));
        install(new FactoryModuleBuilder().implement(LongitudeFormat.class,
                LongitudeFormat.class).build(LongitudeFormatFactory.class));
    }

}
