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

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.apache.commons.math3.util.FastMath.*
import groovy.util.logging.Slf4j

import org.junit.Test

import com.thoughtworks.xstream.XStream

/**
 * @see StandardValue
 * @see StandardValueData
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StandardValueTest extends ValueTestBase {

    @Test
    void "equals"() {
        def testCases = [
            [
                a: standardValueFactory.create(5, 1, 1, 0),
                b: standardValueFactory.create(5, 1, 1, 0),
                expected: true
            ],
            [
                a: standardValueFactory.create(6, 1, 1, 0),
                b: standardValueFactory.create(5, 1, 1, 0),
                expected: false
            ],
        ]
        testCases.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            assert testCase.a.equals(testCase.b) == testCase.expected
        }
    }

    @Test
    void "calculate value"() {
        def testCases = [
            [expected: 1230d, value: standardValueFactory.create(123, 4, 3, 1)],
            [expected: 1030d, value: standardValueFactory.create(103, 4, 3, 1)],
            [expected: 123d, value: standardValueFactory.create(123, 3, 3, 0)],
            [expected: 1d, value: standardValueFactory.create(1, 1, 1, 0)],
            [expected: 0.123E2d, value: standardValueFactory.create(123, 2, 3, -1)],
            [expected: 0.7d, value: standardValueFactory.create(7, 0, 1, -1)],
            [expected: 1.23d, value: standardValueFactory.create(123, 1, 3, -2)],
            [expected: 0.123d, value: standardValueFactory.create(123, 0, 3, -3)],
            [expected: 0.0123d, value: standardValueFactory.create(123, -1, 3, -4)],
            [expected: 0.00123d, value: standardValueFactory.create(123, -2, 3, -5)],
            [expected: 0.123E-2d, value: standardValueFactory.create(123, -2, 3, -5)],
            [expected: 12.123E-2d, value: standardValueFactory.create(12123, 0, 5, -5)],
            [expected: 12.0123E-2d, value: standardValueFactory.create(120123, 0, 6, -6)],
        ]
        epsilon = 10**-9
        testCases.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            assertDecimalEquals testCase.value.getValue(), testCase.expected
        }
    }

    @Test
    void "round value"() {
        def testCases = [
            [expected: 1230d, value: standardValueFactory.create(123, 4, 3, 1)],
            [expected: 1030d, value: standardValueFactory.create(103, 4, 3, 1)],
            [expected: 123d, value: standardValueFactory.create(123, 3, 3, 0)],
            [expected: 1d, value: standardValueFactory.create(1, 1, 1, 0)],
            [expected: 0.123E2d, value: standardValueFactory.create(123, 2, 3, -1)],
            [expected: 1.23d, value: standardValueFactory.create(123, 1, 3, -2)],
            [expected: 0.123d, value: standardValueFactory.create(123, 0, 3, -3)],
            [expected: 0.0123d, value: standardValueFactory.create(123, -1, 3, -4)],
            [expected: 0.00123d, value: standardValueFactory.create(123, -2, 3, -5)],
            [expected: 0.123E-2d, value: standardValueFactory.create(123, -2, 3, -5)],
            [expected: 12.123E-2d, value: standardValueFactory.create(12123, 0, 5, -5)],
            [expected: 12.0123E-2d, value: standardValueFactory.create(120123, 0, 6, -6)],
        ]
        epsilon = 10**-9
        testCases.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            assertDecimalEquals testCase.value.getRoundedValue(), testCase.expected
        }
    }

    @Test
    void "value of from double"() {
        def testCases = [
            [value: 1230d, dec: 3, unc: Double.NaN, expected: standardValueFactory.create(1230000, 4, 7, -3)],
            [value: 1030d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(1030000, 4, 7, -3)],
            [value: 123d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(123000, 3, 6, -3)],
            [value: 1d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(1000, 1, 4, -3)],
            [value: 0.123E2d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(12300, 2, 5, -3)],
            [value: 1.23d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(1230, 1, 4, -3)],
            [value: 0.123d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(123, 0, 3, -3)],
            [value: 0.0123d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(12, -1, 2, -3)],
            [value: 0.00123d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(1, -2, 1, -3)],
            [value: 0.123E-2d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(1, -2, 1, -3)],
            [value: 12.123E-2d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(121, 0, 3, -3)],
            [value: 12.0123E-2d, dec: 3, unc: Double.NaN, , expected: standardValueFactory.create(120, 0, 3, -3)],
        ]
        epsilon = 10**-9
        def oneValue = standardValueFactory.create(1, 1, 1, 0)
        testCases.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            def value = oneValue.valueOf(testCase.value, testCase.dec, testCase.unc)
            log.info "{}. value: {}", k, value
            def expected = testCase.expected as Value
            assert value == expected
            assert value.mantissa == expected.mantissa
            assert value.order == expected.order
            assert value.significant == expected.significant
            assert value.decimal == expected.decimal
        }
    }

    @Test
    void "standard value"() {
        List data = new StandardValueData().create(createValueFormat(), standardValueFactory)
        data.each {
            epsilon = it.epsilon
            def x = it.x
            def y = it.y
            def f = it.f(x, y)
            log.info "$it.name x:=$x; y:=$y; $it.func=$f"
            it.result(f)
            it.rounded(f)
        }
    }

    @Test
    void "standard value, german locale"() {
        def oldLocale = Locale.getDefault()
        def locale = Locale.GERMAN
        Locale.setDefault locale
        List data = new StandardValueData().create(createValueFormat(), standardValueFactory)
        data.each {
            epsilon = it.epsilon
            def x = it.x
            def y = it.y
            def f = it.f(x, y)
            log.info "$it.name x:=$x; y:=$y; $it.func=$f"
            it.result(f)
            it.rounded(f)
        }
        Locale.setDefault oldLocale
    }

    @Test
    void "serialize, inject members"() {
        def value = standardValueFactory.create(123, 4, 3, 1)
        StandardValue valueB = reserialize(value)
        injector.injectMembers valueB
        assertDecimalEquals valueB.value, 1230.0d
        def v = valueB.mul standardValueFactory.create(1, 1, 1, 0)
    }

    @Test
    void "serialize, create"() {
        def value = standardValueFactory.create(123, 4, 3, 1)
        StandardValue valueB = standardValueFactory.create reserialize(value)
        assertDecimalEquals valueB.value, 1230.0d
        def v = valueB.mul standardValueFactory.create(1, 1, 1, 0)
    }

    @Test
    void "xstream, serialize"() {
        def xstream = new XStream()
        def value = standardValueFactory.create(123, 4, 3, 1)
        def xml = xstream.toXML value
        StandardValue valueB = standardValueFactory.create xstream.fromXML(xml)
        assertDecimalEquals valueB.value, 1230.0d
        def v = valueB.mul standardValueFactory.create(1, 1, 1, 0)
    }
}
