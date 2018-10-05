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
package com.anrisoftware.globalpom.math.format

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.math.format.measurement.MeasurementFormatModule
import com.anrisoftware.globalpom.math.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.math.measurement.MeasurementStandardModule
import com.anrisoftware.globalpom.math.measurement.StandardValueFactory
import com.anrisoftware.globalpom.math.measurement.Value
import com.anrisoftware.globalpom.math.measurement.ValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * @see ValueFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
@CompileStatic
class ValueFormatTest {

    @Test
    void "format value"() {
        def formats = [
            [expected: "1230", value: valueFactory.create(123, 4, 3, 1)],
            [expected: "1030", value: valueFactory.create(103, 4, 3, 1)],
            [expected: "123", value: valueFactory.create(123, 3, 3, 0)],
            [expected: "1", value: valueFactory.create(1, 1, 1, 0)],
            [expected: "-1", value: valueFactory.create(-1, 1, 1, 0)],
            [expected: "0", value: valueFactory.create(0, 0, 1, 0)],
            [expected: "0", value: valueFactory.create(-0, 0, 1, 0)],
            [expected: "12.3", value: valueFactory.create(123, 2, 3, -1)],
            [expected: "-12.3", value: valueFactory.create(-123, 2, 3, -1)],
            [expected: "1.23", value: valueFactory.create(123, 1, 3, -2)],
            [expected: "0.123", value: valueFactory.create(123, 0, 3, -3)],
            [expected: "0.0123", value: valueFactory.create(123, -1, 3, -4)],
            [expected: "0.00123", value: valueFactory.create(123, -2, 3, -5)],
            [expected: "0.12123", value: valueFactory.create(12123, 0, 5, -5)],
            [expected: "0.120123", value: valueFactory.create(120123, 0, 6, -6)],
        ]
        def format = formatFactory.create(locale, valueFactory)
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "format value to sig"() {
        def formats = [
            [expected: "1200", value: valueFactory.create(123, 4, 3, 1)],
            [expected: "1000", value: valueFactory.create(103, 4, 3, 1)],
            [expected: "120", value: valueFactory.create(123, 3, 3, 0)],
            [expected: "1", value: valueFactory.create(1, 1, 1, 0)],
            [expected: "-1", value: valueFactory.create(-1, 1, 1, 0)],
            [expected: "0", value: valueFactory.create(0, 0, 1, 0)],
            [expected: "0", value: valueFactory.create(-0, 0, 1, 0)],
            [expected: "12", value: valueFactory.create(123, 2, 3, -1)],
            [expected: "-12", value: valueFactory.create(-123, 2, 3, -1)],
            [expected: "1.2", value: valueFactory.create(123, 1, 3, -2)],
            [expected: "0.12", value: valueFactory.create(123, 0, 3, -3)],
            [expected: "0.012", value: valueFactory.create(123, -1, 3, -4)],
            [expected: "0.0012", value: valueFactory.create(123, -2, 3, -5)],
            [expected: "0.12", value: valueFactory.create(12123, 0, 5, -5)],
            [expected: "0.12", value: valueFactory.create(120123, 0, 6, -6)],
        ]
        def format = formatFactory.create(locale, valueFactory)
        format.setSignificant 2
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "format value to dec"() {
        def formats = [
            [expected: "1230", value: valueFactory.create(123, 4, 3, 1)],
            [expected: "1030", value: valueFactory.create(103, 4, 3, 1)],
            [expected: "123", value: valueFactory.create(123, 3, 3, 0)],
            [expected: "1", value: valueFactory.create(1, 1, 1, 0)],
            [expected: "-1", value: valueFactory.create(-1, 1, 1, 0)],
            [expected: "0", value: valueFactory.create(0, 0, 1, 0)],
            [expected: "0", value: valueFactory.create(-0, 0, 1, 0)],
            [expected: "12.3", value: valueFactory.create(123, 2, 3, -1)],
            [expected: "-12.3", value: valueFactory.create(-123, 2, 3, -1)],
            [expected: "1.23", value: valueFactory.create(123, 1, 3, -2)],
            [expected: "0.12", value: valueFactory.create(123, 0, 3, -3)],
            [expected: "0.01", value: valueFactory.create(123, -1, 3, -4)],
            [expected: "0.00", value: valueFactory.create(123, -2, 3, -5)],
            [expected: "0.12", value: valueFactory.create(12123, 0, 5, -5)],
            [expected: "0.12", value: valueFactory.create(120123, 0, 6, -6)],
        ]
        def format = formatFactory.create(locale, valueFactory)
        format.setDecimal 2
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "format value scientific"() {
        def formats = [
            [expected: "2.99792458000000000E8", value: valueFactory.create(299792458000000000, 9, 18, -9)],
            [expected: "7.4E-35", value: valueFactory.create(74, -34, 2, -36)],
            [expected: "1.23E3", value: valueFactory.create(123, 4, 3, 1)],
            [expected: "1.03E3", value: valueFactory.create(103, 4, 3, 1)],
            [expected: "1.23E2", value: valueFactory.create(123, 3, 3, 0)],
            [expected: "1", value: valueFactory.create(1, 1, 1, 0)],
            [expected: "-1", value: valueFactory.create(-1, 1, 1, 0)],
            [expected: "0", value: valueFactory.create(0, 0, 1, 0)],
            [expected: "0", value: valueFactory.create(-0, 0, 1, 0)],
            [expected: "1.23E1", value: valueFactory.create(123, 2, 3, -1)],
            [expected: "-1.23E1", value: valueFactory.create(-123, 2, 3, -1)],
            [expected: "1.23", value: valueFactory.create(123, 1, 3, -2)],
            [expected: "1.23E-1", value: valueFactory.create(123, 0, 3, -3)],
            [expected: "1.23E-2", value: valueFactory.create(123, -1, 3, -4)],
            [expected: "1.23E-3", value: valueFactory.create(123, -2, 3, -5)],
            [expected: "1.2123E-1", value: valueFactory.create(12123, 0, 5, -5)],
            [expected: "1.20123E-1", value: valueFactory.create(120123, 0, 6, -6)],
        ]
        def format = formatFactory.create(locale, valueFactory)
        format.setUseScientificNotation true
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "format value with uncertainty"() {
        def formats = [
            [expected: "1230(0)", value: valueFactory.create(123, 4, 3, 1, 0.1d)],
            [expected: "1030(0)", value: valueFactory.create(103, 4, 3, 1, 0.1d)],
            [expected: "123(0)", value: valueFactory.create(123, 3, 3, 0, 0.1d)],
            [expected: "1(0)", value: valueFactory.create(1, 1, 1, 0, 0.1d)],
            [expected: "-1(0)", value: valueFactory.create(-1, 1, 1, 0, 0.1d)],
            [expected: "0(0)", value: valueFactory.create(0, 0, 1, 0, 0.1d)],
            [expected: "0(0)", value: valueFactory.create(-0, 0, 1, 0, 0.1d)],
            [expected: "12.3(0.1)", value: valueFactory.create(123, 2, 3, -1, 0.1d)],
            [expected: "-12.3(0.1)", value: valueFactory.create(-123, 2, 3, -1, 0.1d)],
            [expected: "1.23(0.10)", value: valueFactory.create(123, 1, 3, -2, 0.1d)],
            [expected: "0.123(0.100)", value: valueFactory.create(123, 0, 3, -3, 0.1d)],
            [expected: "0.0123(0.1000)", value: valueFactory.create(123, -1, 3, -4, 0.1d)],
            [expected: "0.00123(0.10000)", value: valueFactory.create(123, -2, 3, -5, 0.1d)],
            [expected: "0.12123(0.10000)", value: valueFactory.create(12123, 0, 5, -5, 0.1d)],
            [expected: "0.120123(0.100000)", value: valueFactory.create(120123, 0, 6, -6, 0.1d)],
        ]
        def format = formatFactory.create(locale, valueFactory)
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "format value scientific with uncertainty"() {
        def formats = [
            [expected: "1.23E3(2.00E3)", value: valueFactory.create(123, 4, 3, 1, 2000.0d)],
            [expected: "1.23E3(0.20E3)", value: valueFactory.create(123, 4, 3, 1, 200.0d)],
            [expected: "1.672621777E-27(0.000000074E-27)", value: valueFactory.create(1672621777, -26, 10, -36, 0.000000074E-27d)],
            [expected: "1.23E3(0)", value: valueFactory.create(123, 4, 3, 1, 0.1d)],
            [expected: "1.23E3(0)", value: valueFactory.create(123, 4, 3, 1, 0.1d)],
            [expected: "1.03E3(0)", value: valueFactory.create(103, 4, 3, 1, 0.1d)],
            [expected: "1.23E2(0)", value: valueFactory.create(123, 3, 3, 0, 0.1d)],
            [expected: "1(0)", value: valueFactory.create(1, 1, 1, 0, 0.1d)],
            [expected: "-1(0)", value: valueFactory.create(-1, 1, 1, 0, 0.1d)],
            [expected: "0(0)", value: valueFactory.create(0, 0, 1, 0, 0.1d)],
            [expected: "0(0)", value: valueFactory.create(-0, 0, 1, 0, 0.1d)],
            [expected: "1.00E-1(0)", value: valueFactory.create(1, 0, 3, -1, 0.0d)],
            [expected: "1.23E1(0.01E1)", value: valueFactory.create(123, 2, 3, -1, 0.1d)],
            [expected: "-1.23E1(0.01E1)", value: valueFactory.create(-123, 2, 3, -1, 0.1d)],
            [expected: "1.23(0.10)", value: valueFactory.create(123, 1, 3, -2, 0.1d)],
            [expected: "1.23E-1(1.00E-1)", value: valueFactory.create(123, 0, 3, -3, 0.1d)],
            [expected: "1.23E-2(10.00E-2)", value: valueFactory.create(123, -1, 3, -4, 0.1d)],
            [expected: "1.23E-3(100.00E-3)", value: valueFactory.create(123, -2, 3, -5, 0.1d)],
            [expected: "1.2123E-1(1.0000E-1)", value: valueFactory.create(12123, 0, 5, -5, 0.1d)],
            [expected: "1.20123E-1(1.00000E-1)", value: valueFactory.create(120123, 0, 6, -6, 0.1d)],
        ]
        def format = formatFactory.create(locale, valueFactory)
        format.setUseScientificNotation true
        formats.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def str = format.format(testCase.value)
            assert str == testCase.expected
        }
    }

    @Test
    void "parse value"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_cases(binding).run()
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            Value value = formatFactory.create(locale, valueFactory).parse(testCase.input as String)
            Value expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
        }
    }

    @Test
    void "parse value, german"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_german_cases(binding).run()
        def locale = Locale.GERMAN
        def valueFormat = formatFactory.create(locale, valueFactory)
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            Value value = valueFormat.parse(testCase.input as String)
            Value expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
        }
    }

    @Test
    void "parse value with decimal"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_with_decimal_cases(binding).run()
        def valueFormat = formatFactory.create(locale, valueFactory)
        valueFormat.setDecimal 3
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = valueFormat.parse(testCase.input as String)
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
        }
    }

    @Test
    void "parse value with decimal, german"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_with_decimal_german_cases(binding).run()
        def locale = Locale.GERMAN
        def valueFormat = formatFactory.create(locale, valueFactory)
        valueFormat.setDecimal 3
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = valueFormat.parse(testCase.input as String)
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
        }
    }

    @Test
    void "parse value with uncertainty"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_with_uncertainty_cases(binding).run()
        def valueFormat = formatFactory.create(locale, valueFactory)
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = valueFormat.parse(testCase.input as String)
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
            assertDecimalEquals value.uncertainty, expected.uncertainty
        }
    }

    @Test
    void "parse value with uncertainty and decimal"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_with_uncertainty_decimal_cases(binding).run()
        def valueFormat = formatFactory.create(locale, valueFactory)
        valueFormat.setDecimal 3
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = valueFormat.parse(testCase.input as String)
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
            assertDecimalEquals value.uncertainty, expected.uncertainty
        }
    }

    @Test
    void "parse value with uncertainty in paranthesis short"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_with_uncertainty_short_cases(binding).run()
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = formatFactory.create(locale, valueFactory).parse(testCase.input as String)
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
            assertDecimalEquals value.uncertainty, expected.uncertainty
        }
    }

    @Test
    void "parse value with uncertainty percent"() {
        def binding = new Binding([valueFactory: valueFactory])
        def parses = new parse_value_with_uncertainty_percent_cases(binding).run()
        parses.eachWithIndex { Map testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = formatFactory.create(locale, valueFactory).parse(testCase.input as String)
            log.info "value: {}", value
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
        }
    }

    static Injector injector

    static ValueFormatFactory formatFactory

    static ValueFactory valueFactory

    static Locale locale = Locale.US

    @BeforeAll
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new MeasurementStandardModule(), new MeasurementFormatModule())
        formatFactory = injector.getInstance(ValueFormatFactory)
        valueFactory = injector.getInstance(StandardValueFactory)
    }
}
