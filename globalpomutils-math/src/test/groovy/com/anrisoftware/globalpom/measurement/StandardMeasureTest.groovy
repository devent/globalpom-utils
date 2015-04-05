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

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.apache.commons.math3.util.FastMath.*
import groovy.util.logging.Slf4j

import javax.measure.unit.SI

import org.junit.Test

import com.thoughtworks.xstream.XStream

/**
 * @see StandardMeasure
 * @see StandardMeasureData
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StandardMeasureTest extends ValueTestBase {

    @Test
    void "standard measure"() {
        standardMeasureData.each {
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
    void "serialize"() {
        def value = standardValueFactory.create(5.0, 1, 0.1, 1)
        def measure = standardMeasureFactory.create value, SI.METER
        def measureB = standardMeasureFactory.create reserialize(measure)
        assertDecimalEquals measureB.value, 5.0
        def v = measureB.mul 1.0
    }

    @Test
    void "xstream, serialize"() {
        def xstream = new XStream()
        def value = standardValueFactory.create(5.0, 1, 0.1, 1)
        def measure = standardMeasureFactory.create value, SI.METER
        def xml = xstream.toXML measure
        def measureB = standardMeasureFactory.create xstream.fromXML(xml)
        assertDecimalEquals measureB.value, 5.0
        def v = measureB.mul 1.0
    }
}
