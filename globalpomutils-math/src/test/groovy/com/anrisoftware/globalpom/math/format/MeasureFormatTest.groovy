/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format

import static com.anrisoftware.globalpom.utils.TestUtils.*

import javax.measure.unit.SI

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.math.format.measurement.MeasureFormatFactory
import com.anrisoftware.globalpom.math.format.measurement.MeasurementFormatModule
import com.anrisoftware.globalpom.math.measurement.MeasureFactory
import com.anrisoftware.globalpom.math.measurement.MeasurementStandardModule
import com.anrisoftware.globalpom.math.measurement.StandardMeasureFactory
import com.anrisoftware.globalpom.math.measurement.StandardValueFactory
import com.anrisoftware.globalpom.math.measurement.ValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

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

    @BeforeAll
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new MeasurementStandardModule(), new MeasurementFormatModule())
        formatFactory = injector.getInstance(MeasureFormatFactory)
        measureFactory = injector.getInstance(StandardMeasureFactory)
        valueFactory = injector.getInstance(StandardValueFactory)
    }
}
