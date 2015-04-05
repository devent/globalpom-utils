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
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import javax.measure.unit.SI

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.measurement.MeasureFormatFactory
import com.anrisoftware.globalpom.format.measurement.MeasurementFormatModule
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.measurement.ExactStandardValueFactory
import com.anrisoftware.globalpom.measurement.ExactValueFactory
import com.anrisoftware.globalpom.measurement.MeasureFactory
import com.anrisoftware.globalpom.measurement.MeasurementStandardModule
import com.anrisoftware.globalpom.measurement.StandardMeasureFactory
import com.anrisoftware.globalpom.measurement.StandardValueFactory
import com.anrisoftware.globalpom.measurement.ValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see MeasureFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class MeasureFormatTest {

    @Test
    void "format measurements"() {
        def valueFormat = valueFormatFactory.create(valueFactory, exactValueFactory)
        formats.each {
            def str = formatFactory.create(measureFactory, valueFormat).format(it.value)
            log.info "Format ${it.value} as '${str}'"
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse measurements"() {
        def valueFormat = valueFormatFactory.create(valueFactory, exactValueFactory)
        parses.each {
            log.info "Parse '{}'", it.input
            def value = formatFactory.create(measureFactory, valueFormat).parse(it.input)
            assert value == it.value
        }
    }

    static formats

    static parses

    static Injector injector

    static MeasureFormatFactory formatFactory

    static ValueFormatFactory valueFormatFactory

    static ExactValueFactory exactValueFactory

    static ValueFactory valueFactory

    static MeasureFactory measureFactory

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(
                new MeasurementStandardModule(), new MeasurementFormatModule())
        formatFactory = injector.getInstance(MeasureFormatFactory)
        valueFormatFactory = injector.getInstance(ValueFormatFactory)
        exactValueFactory = injector.getInstance(ExactStandardValueFactory)
        valueFactory = injector.getInstance(StandardValueFactory)
        measureFactory = injector.getInstance(StandardMeasureFactory)
        formats  = [
            [format: "5;m/s;", value: measureFactory.create(exactValueFactory.create(5.0d), SI.METERS_PER_SECOND)],
            [format: "0.0123;m/s;", value: measureFactory.create(exactValueFactory.create(0.0123d), SI.METERS_PER_SECOND)],
            [format: "5(0.2);1;1;m/s;", value: measureFactory.create(valueFactory.create(5.0d, 1, 0.2d, 1), SI.METERS_PER_SECOND)],
        ]
        parses = [
            [input: "5;m/s;", value: measureFactory.create(exactValueFactory.create(5.0d), SI.METERS_PER_SECOND)],
            [input: "0.0123;m/s;", value: measureFactory.create(exactValueFactory.create(0.0123d), SI.METERS_PER_SECOND)],
            [input: "5.0(0.2);1;1;m/s;", value: measureFactory.create(valueFactory.create(5.0d, 1, 0.2d, 1), SI.METERS_PER_SECOND)],
        ]
    }
}
