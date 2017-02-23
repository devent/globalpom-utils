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
package com.anrisoftware.globalpom.math.measurement

import static com.anrisoftware.globalpom.math.measurement.RoundToSignificantFigures.*
import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.Test

import groovy.util.logging.Slf4j

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
