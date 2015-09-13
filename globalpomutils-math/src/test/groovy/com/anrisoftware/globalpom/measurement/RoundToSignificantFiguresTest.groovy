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

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.Test

/**
 * @see RoundToSignificantFigures
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Slf4j
class RoundToSignificantFiguresTest {

    @Test
    void "round to significant"() {
        epsilon = 10**-9
        def testCases = [
            [value: 0.0d, sig: 2, result: 0.0d],
            [value: 2.0d, sig: 2, result: 2.0d],
            [value: 1239451d, sig: 3, result: 1240000d],
            [value: 12.1257d, sig: 3, result: 12.1d],
            [value: 0.0681d, sig: 3, result: 0.0681d],
            [value: 5d, sig: 3, result: 5d],
            [value: -2.0d, sig: 2, result: -2.0d],
            [value: -1239451d, sig: 3, result: -1240000d],
            [value: -12.1257d, sig: 3, result: -12.1d],
            [value: -0.0681d, sig: 3, result: -0.0681d],
            [value: -5d, sig: 3, result: -5d],
        ]
        testCases.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            double res = roundToSignificant testCase.value, testCase.sig
            assertDecimalEquals res, testCase.result
        }
    }

    @Test
    void "round to decimal"() {
        epsilon = 10**-9
        def testCases = [
            [value: 0.0d, dec: 2, result: 0.0d],
            [value: 2.0d, dec: 2, result: 2.0d],
            [value: 1239451d, dec: 3, result: 1239451.0d],
            [value: 12.1257d, dec: 3, result: 12.126d],
            [value: 0.0681d, dec: 3, result: 0.068d],
            [value: 5d, dec: 3, result: 5.000d],
            [value: -2.0d, dec: 2, result: -2.0d],
            [value: -1239451d, dec: 3, result: -1239451d],
            [value: -12.1257d, dec: 3, result: -12.126d],
            [value: -0.0681d, dec: 3, result: -0.068d],
            [value: -5d, dec: 3, result: -5d],
        ]
        testCases.eachWithIndex { testCase, int k ->
            log.info "{}. case: {}", k, testCase
            double res = roundToDecimal testCase.value, testCase.dec
            assertDecimalEquals res, testCase.result
        }
    }
}
