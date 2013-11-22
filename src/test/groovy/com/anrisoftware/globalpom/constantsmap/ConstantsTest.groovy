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
		def c = constants.getConstant("speed_light")
		log.info "Loaded constant: {}", c
		assertDecimalEquals c.value, 299792458.0d
		assert c.unit == SI.METERS_PER_SECOND
		c = constants.getConstant("atomic_mass")
		log.info "Loaded constant: {}", c
		epsilon = 1e-27
		assertDecimalEquals c.value, 1.660538921E-27
		assert c.unit == SI.KILOGRAM
	}
}
