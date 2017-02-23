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

import static com.anrisoftware.globalpom.math.format.degree.DegreeSexagesimalFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.measure.unit.NonSI.*

import org.jscience.physics.amount.Amount
import org.junit.Test

/**
 * @see DegreeSexagesimalFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class DegreeFormatTest {

    static formats = [
        [format: "40°11'15\"", value: Amount.valueOf(40.1875d, DEGREE_ANGLE)],
        [format: "40°11'15.13\"", value: Amount.valueOf(40.18753611d, DEGREE_ANGLE)],
        [format: "40°12'", value: Amount.valueOf(40.2d, DEGREE_ANGLE)],
    ]

    static parses = [
        [input: "40°11'15\"", value: Amount.valueOf(40.1875d, DEGREE_ANGLE), decimal: 4],
        [input: "40°11'15.13\"", value: Amount.valueOf(40.18753611d, DEGREE_ANGLE), decimal: 8],
        [input: "40°12'", value: Amount.valueOf(40.2d, DEGREE_ANGLE), decimal: 1],
        [input: "40°11'15\" N", value: Amount.valueOf(40.1875d, DEGREE_ANGLE), decimal: 4],
        [input: "40°11'15\" S", value: Amount.valueOf(-40.1875d, DEGREE_ANGLE), decimal: 4],
        [input: "40°11'15\" E", value: Amount.valueOf(40.1875d, DEGREE_ANGLE), decimal: 4],
        [input: "40°11'15\" W", value: Amount.valueOf(-40.1875d, DEGREE_ANGLE), decimal: 4],
    ]

    @Test
    void "format degree sexagesimal"() {
        def format = create(4)
        formats.each {
            def str = format.format it.value
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse degree sexagesimal"() {
        parses.each {
            def value = create(it.decimal).parse it.input
            assert isValidFormat(it.input)
            assert value == it.value
        }
    }
}
