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
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.measurement.MeasurementFormatModule
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.measurement.MeasurementStandardModule
import com.anrisoftware.globalpom.measurement.StandardValueFactory
import com.anrisoftware.globalpom.measurement.Value
import com.anrisoftware.globalpom.measurement.ValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

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
        def parses = [
            [input: "-1230", expected: valueFactory.create(-123, 4, 3, 1)],
            [input: "1230", expected: valueFactory.create(123, 4, 3, 1)],
            [input: "1030", expected: valueFactory.create(103, 4, 3, 1)],
            [input: "123", expected: valueFactory.create(123, 3, 3, 0)],
            [input: "10.00", expected: valueFactory.create(1000, 2, 4, -2)],
            [input: "5.6445E2", expected: valueFactory.create(56445, 3, 5, -2)],
            [input: "1", expected: valueFactory.create(1, 1, 1, 0)],
            [input: "-1", expected: valueFactory.create(-1, 1, 1, 0)],
            [input: "0", expected: valueFactory.create(0, 0, 1, 0)],
            [input: "-0", expected: valueFactory.create(0, 0, 1, 0)],
            [input: "7E-1", expected: valueFactory.create(7, 0, 1, -1)],
            [input: "0.7", expected: valueFactory.create(7, 0, 1, -1)],
            [input: "0.07", expected: valueFactory.create(7, -1, 1, -2)],
            [input: "0.050000000", expected: valueFactory.create(50000000, -1, 8, -9)],
            [input: "0.500000000", expected: valueFactory.create(500000000, 0, 9, -9)],
            [input: "0.005050000", expected: valueFactory.create(5050000, -2, 7, -9)],
            [input: "0.123E2", expected: valueFactory.create(123, 2, 3, -1)],
            [input: "-0.123E2", expected: valueFactory.create(-123, 2, 3, -1)],
            [input: "12.3", expected: valueFactory.create(123, 2, 3, -1)],
            [input: "15.0", expected: valueFactory.create(150, 2, 3, -1)],
            [input: "11.02318310912886", expected: valueFactory.create(1102318310912886, 2, 16, -14)],
            [input: "1.23", expected: valueFactory.create(123, 1, 3, -2)],
            [input: "0.123", expected: valueFactory.create(123, 0, 3, -3)],
            [input: "0.0123", expected: valueFactory.create(123, -1, 3, -4)],
            [input: "0.00123", expected: valueFactory.create(123, -2, 3, -5)],
            [input: "0.123E-2", expected: valueFactory.create(123, -2, 3, -5)],
            [input: "12.123E-2", expected: valueFactory.create(12123, 0, 5, -5)],
            [input: "12.0123E-2", expected: valueFactory.create(120123, 0, 6, -6)],
        ]
        parses.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = formatFactory.create(locale, valueFactory).parse(testCase.input as String)
            assert value == testCase.expected
        }
    }

    @Test
    void "parse value with decimal"() {
        def parses = [
            [input: "-1230", expected: valueFactory.create(-1230000, 4, 7, -3)],
            [input: "1230", expected: valueFactory.create(1230000, 4, 7, -3)],
            [input: "1030", expected: valueFactory.create(1030000, 4, 7, -3)],
            [input: "123", expected: valueFactory.create(123000, 3, 6, -3)],
            [input: "10.00", expected: valueFactory.create(10000, 2, 5, -3)],
            [input: "5.6445E2", expected: valueFactory.create(564450, 3, 6, -3)],
            [input: "1", expected: valueFactory.create(1000, 1, 4, -3)],
            [input: "-1", expected: valueFactory.create(-1000, 1, 4, -3)],
            [input: "0", expected: valueFactory.create(0, 0, 1, -1)],
            [input: "-0", expected: valueFactory.create(0, 0, 1, -1)],
            [input: "7E-1", expected: valueFactory.create(700, 0, 3, -3)],
            [input: "0.7", expected: valueFactory.create(700, 0, 3, -3)],
            [input: "0.07", expected: valueFactory.create(70, -1, 2, -3)],
            [input: "0.050000000", expected: valueFactory.create(50, -1, 2, -3)],
            [input: "0.500000000", expected: valueFactory.create(500, 0, 3, -3)],
            [input: "0.005050000", expected: valueFactory.create(5, -2, 1, -3)],
            [input: "0.123E2", expected: valueFactory.create(12300, 2, 5, -3)],
            [input: "-0.123E2", expected: valueFactory.create(-12300, 2, 5, -3)],
            [input: "12.3", expected: valueFactory.create(12300, 2, 5, -3)],
            [input: "15.0", expected: valueFactory.create(15000, 2, 5, -3)],
            [input: "11.02318310912886", expected: valueFactory.create(11023, 2, 5, -3)],
            [input: "1.23", expected: valueFactory.create(1230, 1, 4, -3)],
            [input: "0.123", expected: valueFactory.create(123, 0, 3, -3)],
            [input: "0.0123", expected: valueFactory.create(12, -1, 2, -3)],
            [input: "0.00123", expected: valueFactory.create(1, -2, 1, -3)],
            [input: "0.123E-2", expected: valueFactory.create(1, -2, 1, -3)],
            [input: "12.123E-2", expected: valueFactory.create(121, 0, 3, -3)],
            [input: "12.0123E-2", expected: valueFactory.create(120, 0, 3, -3)],
        ]
        def valueFormat = formatFactory.create(locale, valueFactory)
        valueFormat.setDecimal 3
        parses.eachWithIndex { testCase, int k ->
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
        def parses = [
            [input: "1230(0.5)", expected: valueFactory.create(123, 4, 3, 1, 0.5d)],
            [input: "1030(0.5)", expected: valueFactory.create(103, 4, 3, 1, 0.5d)],
            [input: "123(0.5)", expected: valueFactory.create(123, 3, 3, 0, 0.5d)],
            [input: "1(0.5)", expected: valueFactory.create(1, 1, 1, 0, 0.5d)],
            [input: "0.123E2(0.5)", expected: valueFactory.create(123, 2, 3, -1, 0.5d)],
            [input: "1.23(0.5)", expected: valueFactory.create(123, 1, 3, -2, 0.5d)],
            [input: "0.123(0.5)", expected: valueFactory.create(123, 0, 3, -3, 0.5d)],
            [input: "0.0123(0.5)", expected: valueFactory.create(123, -1, 3, -4, 0.5d)],
            [input: "0.00123(0.5)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
            [input: "0.123E-2(0.5)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
            [input: "12.123E-2(0.5)", expected: valueFactory.create(12123, 0, 5, -5, 0.5d)],
            [input: "12.0123E-2(0.5)", expected: valueFactory.create(120123, 0, 6, -6, 0.5d)],
            [input: "6.62606957E-34(0.00000029E-34)", expected: valueFactory.create(662606957, -33, 9, -42, 2.9E-41)],
        ]
        parses.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = formatFactory.create(locale, valueFactory).parse(testCase.input as String)
            assert value == testCase.expected
        }
    }

    @Test
    void "parse value with uncertainty percent"() {
        def parses = [
            [input: "1230(1%)", expected: valueFactory.create(123, 4, 3, 1, 0.5d)],
            [input: "1030(1%)", expected: valueFactory.create(103, 4, 3, 1, 0.5d)],
            [input: "123(1%)", expected: valueFactory.create(123, 3, 3, 0, 0.5d)],
            [input: "1(1%)", expected: valueFactory.create(1, 1, 1, 0, 0.5d)],
            [input: "0.123E2(1%)", expected: valueFactory.create(123, 2, 3, -1, 0.5d)],
            [input: "1.23(1%)", expected: valueFactory.create(123, 1, 3, -2, 0.5d)],
            [input: "0.123(1%)", expected: valueFactory.create(123, 0, 3, -3, 0.5d)],
            [input: "0.0123(1%)", expected: valueFactory.create(123, -1, 3, -4, 0.5d)],
            [input: "0.00123(1%)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
            [input: "0.123E-2(1%)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
            [input: "12.123E-2(1%)", expected: valueFactory.create(12123, 0, 5, -5, 0.5d)],
            [input: "12.0123E-2(1%)", expected: valueFactory.create(120123, 0, 6, -6, 0.5d)],
        ]
        parses.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = formatFactory.create(locale, valueFactory).parse(testCase.input as String)
            log.info "value: {}", value
            assert value == testCase.expected
        }
    }

    static Injector injector

    static ValueFormatFactory formatFactory

    static ValueFactory valueFactory

    static Locale locale = Locale.US

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new MeasurementStandardModule(), new MeasurementFormatModule())
        formatFactory = injector.getInstance(ValueFormatFactory)
        valueFactory = injector.getInstance(StandardValueFactory)
    }
}
