/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.measurement

import groovy.transform.CompileStatic

import org.junit.BeforeClass

import com.anrisoftware.globalpom.format.measurement.MeasurementFormatModule
import com.anrisoftware.globalpom.format.measurement.ValueFormat
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Creates the value factories.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@CompileStatic
class ValueTestBase {

    static Injector injector

    static StandardValueFactory standardValueFactory

    static StandardMeasureFactory standardMeasureFactory

    static ValueFormatFactory valueFormatFactory

    static List standardMeasureData

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector new MeasurementStandardModule(), new MeasurementFormatModule()
        standardValueFactory = injector.getInstance StandardValueFactory
        valueFormatFactory = injector.getInstance ValueFormatFactory
        standardMeasureFactory = injector.getInstance StandardMeasureFactory
    }

    static ValueFormat createValueFormat() {
        valueFormatFactory.create(Locale.ENGLISH, standardValueFactory)
    }
}
