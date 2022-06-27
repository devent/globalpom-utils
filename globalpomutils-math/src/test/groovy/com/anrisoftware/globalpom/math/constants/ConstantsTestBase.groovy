/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.constants

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.measure.unit.SI.*

import org.junit.jupiter.api.BeforeAll

import com.anrisoftware.globalpom.math.format.measurement.MeasureFormatFactory
import com.anrisoftware.globalpom.math.format.measurement.MeasurementFormatModule
import com.anrisoftware.globalpom.math.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.math.measurement.MeasurementStandardModule
import com.anrisoftware.globalpom.math.measurement.StandardMeasureFactory
import com.anrisoftware.globalpom.math.measurement.StandardValueFactory
import com.anrisoftware.globalpom.math.measurement.Value
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.transform.CompileStatic

/**
 * Creates the constants factories.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@CompileStatic
class ConstantsTestBase {

    static Injector injector

    static ConstantsFactory constantsFactory

    static MeasureFormatFactory formatFactory

    static ValueFormatFactory valueFormatFactory

    static StandardMeasureFactory measureFactory

    static StandardValueFactory valueFactory

    @BeforeAll
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector new ConstantsMapModule(),
                new MeasurementFormatModule(), new MeasurementStandardModule()
        constantsFactory = injector.getInstance ConstantsFactory
        formatFactory = injector.getInstance MeasureFormatFactory
        measureFactory = injector.getInstance StandardMeasureFactory
        valueFactory = injector.getInstance StandardValueFactory
        valueFormatFactory = injector.getInstance ValueFormatFactory
    }

    static assertConstants(Constants constants) {
        assertConstantName constants, name: "speed_light", epsilon: 10e-3, value: 299792458.0d, unit: METERS_PER_SECOND
        assertConstantName constants, name: "planck_constant", epsilon: 10e-42, value: 6.62606957E-34, unit: JOULE.times(SECOND)
        assertConstantName constants, name: "planck_constant_reduced", epsilon: 10e-34, value: 1.054571726E-34, unit: JOULE.times(SECOND)
        assertConstantName constants, name: "electron_charge", epsilon: 10e-28, value: 1.602176565E-19, unit: COULOMB
        assertConstantName constants, name: "atomic_mass", epsilon: 10e-36, value: 1.660538921E-27, unit: KILOGRAM
        assertConstantName constants, name: "electron_mass", epsilon: 10e-39, value: 9.10938291E-31, unit: KILOGRAM
        assertConstantName constants, name: "proton_mass", epsilon: 10e-36, value: 1.672621777E-27, unit: KILOGRAM
    }

    static assertConstantName(Map args, Constants constants) {
        def c = constants.getConstant args.name as String
        assertConstant args, c
    }

    static assertConstant(Map args, Value c) {
        epsilon = args.epsilon
        assertDecimalEquals c.value, args.value as double
    }
}
