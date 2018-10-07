/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.anrisoftware.globalpom.math.constants

import static com.anrisoftware.globalpom.math.constants.ConstantsTestBase.*
import static javax.measure.unit.SI.*
import static org.apache.commons.math3.util.FastMath.*

import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.math.measurement.Measure

import groovy.util.logging.Slf4j

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
        def format = formatFactory.create(valueFactory, measureFactory)
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
        log.info "u := {} {} {}", u.value, u.roundedValue, u
        Measure mp = injector.getInstance(StandardProtonMassProvider).get()
        def u12 = u.mul 12.0d
        log.info "C12 := {} {} {}", u12.value, u12.roundedValue, u12
        def c12 = u12.div mp
        def logc12 = c12.log()
        def logc12value = logc12.roundedValue
        log.info "log(C12) := {} {}", logc12value, logc12
        assertConstant logc12, epsilon: 10e-9, value: 2.477656529E00, unit: KILOGRAM
    }

    @Test
    void "carbon 12 compact"() {
        Measure u = injector.getInstance(StandardAtomicMassProvider).get()
        Measure mp = injector.getInstance(StandardProtonMassProvider).get()
        def logc12 = u.mul 12.0 div mp log()
        def logc12value = logc12.roundedValue
        log.info "log(C12) := {} {}", logc12value, logc12
        assertConstant logc12, epsilon: 10e-9, value: 2.477656529E00, unit: KILOGRAM
    }

    @Test
    void "C12 mass provider"() {
        def c = injector.getInstance(StandardC12MassProvider).get()
        assertConstant c, epsilon: 10e-35, value: 1.992646705E-26, unit: KILOGRAM
    }
}
