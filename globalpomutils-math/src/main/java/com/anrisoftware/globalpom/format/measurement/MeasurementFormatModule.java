/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.format.measurement;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the value format factory.
 * 
 * @see ValueFormatFactory
 * @see ValueRendererFactory
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

    /**
     * Returns the value renderer factory.
     * 
     * @return the {@link ValueRendererFactory}.
     */
    public static ValueRendererFactory getValueRendererFactory() {
        return InjectorInstance.valueRendererFactory;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new MeasurementFormatModule());

        static final ValueFormatFactory valueFactory = injector
                .getInstance(ValueFormatFactory.class);

        static final MeasureFormatFactory measureFactory = injector
                .getInstance(MeasureFormatFactory.class);

        static final ValueRendererFactory valueRendererFactory = injector
                .getInstance(ValueRendererFactory.class);
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(ValueFormat.class,
                ValueFormat.class).build(ValueFormatFactory.class));
        install(new FactoryModuleBuilder().implement(MeasureFormat.class,
                MeasureFormat.class).build(MeasureFormatFactory.class));
        install(new FactoryModuleBuilder().implement(ValueRenderer.class,
                ValueRenderer.class).build(ValueRendererFactory.class));
    }

}
