/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
