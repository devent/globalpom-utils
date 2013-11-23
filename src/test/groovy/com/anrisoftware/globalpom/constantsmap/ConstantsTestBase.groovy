/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.constantsmap

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.measure.unit.SI.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass

import com.anrisoftware.globalpom.constants.Constant
import com.anrisoftware.globalpom.constants.ConstantsModule
import com.anrisoftware.globalpom.constants.StandardConstantFactory
import com.anrisoftware.globalpom.format.constants.ConstantFormatFactory
import com.anrisoftware.globalpom.format.constants.ConstantFormatModule
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.format.measurement.ValueFormatModule
import com.anrisoftware.globalpom.measurement.ExactStandardValueFactory
import com.anrisoftware.globalpom.measurement.MeasurementModule
import com.anrisoftware.globalpom.measurement.StandardValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Creates the constants factories.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class ConstantsTestBase {

	static Injector injector

	static ConstantsFactory constantsFactory

	static ConstantFormatFactory formatFactory

	static StandardConstantFactory constantFactory

	static ValueFormatFactory valueFormatFactory

	static StandardValueFactory value

	static ExactStandardValueFactory exact

	@BeforeClass
	static void createFactories() {
		TestUtils.toStringStyle
		injector = Guice.createInjector new ConstantsMapModule(), new ConstantsModule(),
				new ConstantFormatModule(), new ValueFormatModule(),
				new MeasurementModule()
		constantsFactory = injector.getInstance ConstantsFactory
		formatFactory = injector.getInstance ConstantFormatFactory
		constantFactory = injector.getInstance StandardConstantFactory
		valueFormatFactory = injector.getInstance ValueFormatFactory
		value = injector.getInstance StandardValueFactory
		exact = injector.getInstance ExactStandardValueFactory
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
		def c = constants.getConstant args.name
		assertConstant args, c
	}

	static assertConstant(Map args, Constant c) {
		log.info "Loaded constant: {}", c
		epsilon = args.epsilon
		assertDecimalEquals c.value, args.value
		assert c.unit == args.unit
	}
}
