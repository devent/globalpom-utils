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
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StandardMeasureTest extends ValueTestBase {

    @Test
    void "serialize"() {
        def value = standardValueFactory.create(123, 4, 3, 1)
        def measure = standardMeasureFactory.create value, SI.METER
        def measureB = standardMeasureFactory.create reserialize(measure)
        assertDecimalEquals measureB.getValue(), 1230
        def v = measureB.mul standardValueFactory.create(1, 1, 1, 0)
    }

    @Test
    void "xstream, serialize"() {
        def xstream = new XStream()
        def value = standardValueFactory.create(123, 4, 3, 1)
        def measure = standardMeasureFactory.create value, SI.METER
        def xml = xstream.toXML measure
        def measureB = standardMeasureFactory.create xstream.fromXML(xml)
        assertDecimalEquals measureB.getValue(), 1230
        def v = measureB.mul standardValueFactory.create(1, 1, 1, 0)
    }
}
