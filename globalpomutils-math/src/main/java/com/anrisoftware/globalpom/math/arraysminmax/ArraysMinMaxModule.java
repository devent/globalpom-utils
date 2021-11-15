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
package com.anrisoftware.globalpom.math.arraysminmax;

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

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the min/max array search factories.
 *
 * @see IntArrayMinMaxPairsFactory
 * @see LongArrayMinMaxPairsFactory
 * @see DoubleArrayMinMaxPairsFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
public class ArraysMinMaxModule extends AbstractModule {

    /**
     * Returns the {@code int} array min/max pairs factory.
     *
     * @return the {@link IntArrayMinMaxPairsFactory}.
     */
    public static IntArrayMinMaxPairsFactory getIntArrayMinMaxPairsFactory() {
        return InjectorInstance.intArrayMinMaxPairsFactory;
    }

    /**
     * Returns the {@code long} array min/max pairs factory.
     *
     * @return the {@link LongArrayMinMaxPairsFactory}.
     */
    public static LongArrayMinMaxPairsFactory getLongArrayMinMaxPairsFactory() {
        return InjectorInstance.longArrayMinMaxPairsFactory;
    }

    /**
     * Returns the {@code double} array min/max pairs factory.
     *
     * @return the {@link DoubleArrayMinMaxPairsFactory}.
     */
    public static DoubleArrayMinMaxPairsFactory getDoubleArrayMinMaxPairsFactory() {
        return InjectorInstance.doubleArrayMinMaxPairsFactory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new ArraysMinMaxModule());

        static final IntArrayMinMaxPairsFactory intArrayMinMaxPairsFactory = injector
                .getInstance(IntArrayMinMaxPairsFactory.class);

        static final LongArrayMinMaxPairsFactory longArrayMinMaxPairsFactory = injector
                .getInstance(LongArrayMinMaxPairsFactory.class);

        static final DoubleArrayMinMaxPairsFactory doubleArrayMinMaxPairsFactory = injector
                .getInstance(DoubleArrayMinMaxPairsFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(IntArrayMinMaxPairs.class,
                IntArrayMinMaxPairs.class).build(
                IntArrayMinMaxPairsFactory.class));
        install(new FactoryModuleBuilder().implement(
                LongArrayMinMaxPairs.class, LongArrayMinMaxPairs.class).build(
                LongArrayMinMaxPairsFactory.class));
        install(new FactoryModuleBuilder().implement(
                DoubleArrayMinMaxPairs.class, DoubleArrayMinMaxPairs.class)
                .build(DoubleArrayMinMaxPairsFactory.class));
    }

}
