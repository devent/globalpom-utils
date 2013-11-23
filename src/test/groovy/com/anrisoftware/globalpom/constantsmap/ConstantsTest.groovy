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
import static org.apache.commons.math3.util.FastMath.*
import groovy.util.logging.Slf4j

import javax.measure.unit.SI

import org.junit.Test

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

		assertConstant constants, name: "speed_light", epsilon: 10e-3, value: 299792458.0d, unit: METERS_PER_SECOND
		assertConstant constants, name: "planck_constant", epsilon: 10e-42, value: 6.62606957E-34, unit: JOULE.times(SECOND)
		assertConstant constants, name: "planck_constant_reduced", epsilon: 10e-34, value: 1.054571726E-34, unit: JOULE.times(SECOND)
		assertConstant constants, name: "electron_charge", epsilon: 10e-28, value: 1.602176565E-19, unit: COULOMB
		assertConstant constants, name: "atomic_mass", epsilon: 10e-36, value: 1.660538921E-27, unit: KILOGRAM
		assertConstant constants, name: "electron_mass", epsilon: 10e-39, value: 9.10938291E-31, unit: KILOGRAM
		assertConstant constants, name: "proton_mass", epsilon: 10e-36, value: 1.672621777E-27, unit: KILOGRAM
	}

	void assertConstant(Map args, Constants constants) {
		def c = constants.getConstant args.name
		log.info "Loaded constant: {}", c
		epsilon = args.epsilon
		assertDecimalEquals c.value, args.value
		assert c.unit == args.unit
	}
}
