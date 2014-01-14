/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.measurement

import org.junit.BeforeClass

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Creates the value factories.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class ValueTestBase {

    static Injector injector

    static StandardValueFactory standardValueFactory

    static ExactStandardValueFactory exactStandardValueFactory

    static StandardMeasureFactory standardMeasureFactory

    static List standardValueData

    static List standardMeasureData

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector new MeasurementModule()
        standardValueFactory = injector.getInstance StandardValueFactory
        exactStandardValueFactory = injector.getInstance ExactStandardValueFactory
        standardMeasureFactory = injector.getInstance StandardMeasureFactory
        standardValueData = new StandardValueData().create(standardValueFactory, exactStandardValueFactory)
        standardMeasureData = new StandardMeasureData().create(standardMeasureFactory, standardValueFactory, exactStandardValueFactory)
    }
}
