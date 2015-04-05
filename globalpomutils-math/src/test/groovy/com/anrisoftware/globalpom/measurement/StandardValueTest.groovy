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
    void "standard value"() {
        standardValueData.each {
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
    void "calculation test"() {
        def x = standardValueFactory.create(1.672621777E-27, 2, 7.4E-35, 36)
        def r = x.reciprocal()
        assertDecimalEquals r.uncertainty, 26450642516395147000
        //assertDecimalEquals r.roundedValue.uncertainty,    26000000000000000000
        //assertDecimalEquals r.roundedValue.value,   597863790000000000000000000
        def v = standardValueFactory.create(1.100000000E-01, 2, 0.11, 36)
        def m = r.mul v
        log.info "f(r,v):=r*v={}", m
        def l = m.log()
        log.info "f(m):=log(m)={}", l
        assertDecimalEquals l.roundedValue.uncertainty, 1.0
        assertDecimalEquals l.roundedValue.value, 59.45
    }

    @Test
    void "serialize, inject members"() {
        def value = standardValueFactory.create(5.0, 1, 0.1, 1)
        StandardValue valueB = reserialize(value)
        injector.injectMembers valueB
        assertDecimalEquals valueB.value, 5.0
        def v = valueB.mul 1.0
    }

    @Test
    void "serialize"() {
        def value = standardValueFactory.create(5.0, 1, 0.1, 1)
        StandardValue valueB = standardValueFactory.create reserialize(value)
        assertDecimalEquals valueB.value, 5.0
        def v = valueB.mul 1.0
    }

    @Test
    void "xstream, serialize"() {
        def xstream = new XStream()
        def value = standardValueFactory.create(5.0, 1, 0.1, 1)
        def xml = xstream.toXML value
        StandardValue valueB = standardValueFactory.create xstream.fromXML(xml)
        assertDecimalEquals valueB.value, 5.0
        def v = valueB.mul 1.0
    }

    @Test
    void "serialize exact standard value, inject members"() {
        def value = exactStandardValueFactory.create(5.0)
        ExactStandardValue valueB = reserialize(value)
        injector.injectMembers valueB
        assertDecimalEquals valueB.value, 5.0
        def v = valueB.mul 1.0
    }

    @Test
    void "serialize exact standard value"() {
        def value = exactStandardValueFactory.create(5.0)
        ExactStandardValue valueB = exactStandardValueFactory.create reserialize(value)
        assertDecimalEquals valueB.value, 5.0
        def v = valueB.mul 1.0
    }

    @Test
    void "xstream, serialize exact standard value"() {
        def xstream = new XStream()
        def value = exactStandardValueFactory.create(5.0)
        def xml = xstream.toXML value
        ExactStandardValue valueB = exactStandardValueFactory.create xstream.fromXML(xml)
        assertDecimalEquals valueB.value, 5.0
        def v = valueB.mul 1.0
    }
}
