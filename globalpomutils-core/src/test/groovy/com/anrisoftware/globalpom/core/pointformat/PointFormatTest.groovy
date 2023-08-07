/*
 * Copyright 2013-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.pointformat

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.awt.Point
import java.awt.geom.Point2D
import java.text.ParseException
import java.text.ParsePosition

import org.junit.jupiter.api.Test

import groovy.transform.CompileStatic

/**
 * Test parsing and formatting of a point.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
@CompileStatic
class PointFormatTest {

    @Test
    void "format point, default decimal format"() {
        def format = new PointFormat()
        def str = format.format new Point2D.Double(1, 2)
        assertStringContent "(1, 2)", str

        str = format.format new Point2D.Double((double)1.1, (double)2.1)
        assertStringContent "(1.1, 2.1)", str

        str = format.format new Point(1, 2)
        assertStringContent "(1, 2)", str
    }

    @Test
    void "parse point, default decimal format"() {
        def format = new PointFormat()
        List<Map> outputs = new tests_outputs().run() as List
        List<Map> inputs = new tests_inputs().run() as List
        inputs.eachWithIndex { Map input, int i ->
            def point = format.parse input.source as String, new ParsePosition(0), input.point as Point2D
            assert point == outputs[i]
        }
    }

    @Test
    void "parse not valid string"() {
        def format = new PointFormat()
        shouldFailWith(ParseException) { format.parse "(1)" }
    }
}
