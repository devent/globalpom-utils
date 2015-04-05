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
package com.anrisoftware.globalpom.constants

import static com.anrisoftware.globalpom.constants.ConstantsTestBase.*
import static javax.measure.unit.SI.*
import static org.apache.commons.math3.util.FastMath.*
import groovy.util.logging.Slf4j

import org.junit.Test

import com.anrisoftware.globalpom.measurement.Measure

/**
 * @see Constants
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class ConstantsTest extends ConstantsTestBase {

    @Test
    void "get constants"() {
        def valueFormat = valueFormatFactory.create(value, exact)
        def format = formatFactory.create(constantFactory, valueFormat)
        def constants = constantsFactory.create(format)
        assertConstants constants
    }

    @Test
    void "standard constants provider"() {
        StandardConstantsProvider provider = injector.getInstance StandardConstantsProvider
        def constants = provider.get()
        assertConstants constants
    }

    @Test
    void "standard light speed provider"() {
        def c = injector.getInstance(StandardSpeedLightProvider).get()
        log.info "Loaded constant: {}", c
        assertConstant c, epsilon: 10e-3, value: 299792458.0d, unit: METERS_PER_SECOND
    }

    @Test
    void "standard planck constant provider"() {
        def c = injector.getInstance(StandardPlanckConstantProvider).get()
        log.info "Loaded constant: {}", c
        assertConstant c, epsilon: 10e-42, value: 6.62606957E-34, unit: JOULE.times(SECOND)
    }

    @Test
    void "carbon 12"() {
        Measure u = injector.getInstance(StandardAtomicMassProvider).get()
        Measure mp = injector.getInstance(StandardProtonMassProvider).get()
        def u12 = u.mul 12.0
        log.info "C12 := {}, rounded := {}", u12, u12.roundedValue
        def c12 = u12.div mp
        def logc12 = c12.log()
        def logc12value = logc12.roundedValue logc12.significant, 8
        log.info "log(C12) := {}, rounded := {}", logc12, logc12value
        assertConstant logc12, epsilon: 10e-9, value: 2.477656529E00, unit: KILOGRAM
        assertConstant logc12value, epsilon: 10e-8, value: 2.48E00, unit: KILOGRAM
    }

    @Test
    void "carbon 12 compact"() {
        Measure u = injector.getInstance(StandardAtomicMassProvider).get()
        Measure mp = injector.getInstance(StandardProtonMassProvider).get()
        def logc12 = u.mul 12.0 div mp log()
        def logc12value = logc12.roundedValue logc12.significant, 8
        log.info "log(C12) := {}, rounded := {}", logc12, logc12value
        assertConstant logc12, epsilon: 10e-9, value: 2.477656529E00, unit: KILOGRAM
        assertConstant logc12value, epsilon: 10e-8, value: 2.48E00, unit: KILOGRAM
    }

    @Test
    void "C12 mass provider"() {
        def c = injector.getInstance(StandardC12MassProvider).get()
        assertConstant c, epsilon: 10e-35, value: 1.992646705E-26, unit: KILOGRAM
    }
}
