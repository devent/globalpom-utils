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
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.measure.unit.SI

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.measurement.MeasureFormatFactory
import com.anrisoftware.globalpom.format.measurement.MeasurementFormatModule
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
@CompileStatic
class MeasureFormatTest {

    @Test
    void "format value"() {
        def formats = [
            [expected: "1230 m", value: measureFactory.create(valueFactory.create(123, 4, 3, 1), SI.METER)],
            [expected: "1030 m", value: measureFactory.create(valueFactory.create(103, 4, 3, 1), SI.METER)],
            [expected: "123 m", value: measureFactory.create(valueFactory.create(123, 3, 3, 0), SI.METER)],
            [expected: "1 m", value: measureFactory.create(valueFactory.create(1, 1, 1, 0), SI.METER)],
            [expected: "-1 m", value: measureFactory.create(valueFactory.create(-1, 1, 1, 0), SI.METER)],
            [expected: "0 m", value: measureFactory.create(valueFactory.create(0, 0, 1, 0), SI.METER)],
            [expected: "0 m", value: measureFactory.create(valueFactory.create(-0, 0, 1, 0), SI.METER)],
            [expected: "12.3 m", value: measureFactory.create(valueFactory.create(123, 2, 3, -1), SI.METER)],
            [expected: "-12.3 m", value: measureFactory.create(valueFactory.create(-123, 2, 3, -1), SI.METER)],
            [expected: "1.23 m", value: measureFactory.create(valueFactory.create(123, 1, 3, -2), SI.METER)],
            [expected: "0.123 m", value: measureFactory.create(valueFactory.create(123, 0, 3, -3), SI.METER)],
            [expected: "0.0123 m", value: measureFactory.create(valueFactory.create(123, -1, 3, -4), SI.METER)],
            [expected: "0.00123 m", value: measureFactory.create(valueFactory.create(123, -2, 3, -5), SI.METER)],
            [expected: "0.12123 m", value: measureFactory.create(valueFactory.create(12123, 0, 5, -5), SI.METER)],
            [expected: "0.120123 m", value: measureFactory.create(valueFactory.create(120123, 0, 6, -6), SI.METER)],
        ]
        def format = formatFactory.create(locale, valueFactory, measureFactory)
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "parse value"() {
        def parses = [
            [input: "-1230 m", expected: measureFactory.create(valueFactory.create(-123, 4, 3, 1), SI.METER)],
            [input: "1230 m", expected: measureFactory.create(valueFactory.create(123, 4, 3, 1), SI.METER)],
            [input: "1030 m", expected: measureFactory.create(valueFactory.create(103, 4, 3, 1), SI.METER)],
            [input: "123 m", expected: measureFactory.create(valueFactory.create(123, 3, 3, 0), SI.METER)],
            [input: "5.6445E2 m", expected: measureFactory.create(valueFactory.create(56445, 3, 5, -2), SI.METER)],
            [input: "1 m", expected: measureFactory.create(valueFactory.create(1, 1, 1, 0), SI.METER)],
            [input: "-1 m", expected: measureFactory.create(valueFactory.create(-1, 1, 1, 0), SI.METER)],
            [input: "0 m", expected: measureFactory.create(valueFactory.create(0, 0, 1, 0), SI.METER)],
            [input: "-0 m", expected: measureFactory.create(valueFactory.create(0, 0, 1, 0), SI.METER)],
            [input: "0.123E2 m", expected: measureFactory.create(valueFactory.create(123, 2, 3, -1), SI.METER)],
            [input: "-0.123E2 m", expected: measureFactory.create(valueFactory.create(-123, 2, 3, -1), SI.METER)],
            [input: "1.23 m", expected: measureFactory.create(valueFactory.create(123, 1, 3, -2), SI.METER)],
            [input: "0.123 m", expected: measureFactory.create(valueFactory.create(123, 0, 3, -3), SI.METER)],
            [input: "0.0123 m", expected: measureFactory.create(valueFactory.create(123, -1, 3, -4), SI.METER)],
            [input: "0.00123 m", expected: measureFactory.create(valueFactory.create(123, -2, 3, -5), SI.METER)],
            [input: "0.123E-2 m", expected: measureFactory.create(valueFactory.create(123, -2, 3, -5), SI.METER)],
            [input: "12.123E-2 m", expected: measureFactory.create(valueFactory.create(12123, 0, 5, -5), SI.METER)],
            [input: "12.0123E-2 m", expected: measureFactory.create(valueFactory.create(120123, 0, 6, -6), SI.METER)],
        ]
        parses.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = formatFactory.create(locale, valueFactory, measureFactory).parse(testCase.input as String)
            assert value == testCase.expected
        }
    }

    static Injector injector

    static MeasureFormatFactory formatFactory

    static MeasureFactory measureFactory

    static ValueFactory valueFactory

    static Locale locale = Locale.US

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new MeasurementStandardModule(), new MeasurementFormatModule())
        formatFactory = injector.getInstance(MeasureFormatFactory)
        measureFactory = injector.getInstance(StandardMeasureFactory)
        valueFactory = injector.getInstance(StandardValueFactory)
    }
}
