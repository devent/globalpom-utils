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
package com.anrisoftware.globalpom.format.latlong;

import static com.google.inject.Guice.createInjector;

import com.anrisoftware.globalpom.format.degree.DegreeFormatModule;
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
