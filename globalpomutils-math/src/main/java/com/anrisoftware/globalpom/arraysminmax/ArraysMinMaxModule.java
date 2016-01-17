/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.arraysminmax;

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
