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

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.*
import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.Test

/**
 * @see RoundToSignificantFigures
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class RoundToSignificantFiguresTest {

	List inputs = [
		[value: 0.0d, sig: 2, result: 0.0d],
		[value: 2.0d, sig: 2, result: 2.0d],
		[value: 1239451d, sig: 3, result: 1240000d],
		[value: 12.1257d, sig: 3, result: 12.1d],
		[value: 0.0681d, sig: 3, result: 0.0681d],
		[value: 5d, sig: 3, result: 5d],
		[value: -2.0d, sig: 2, result: -2.0d],
		[value: -1239451d, sig: 3, result: -1240000d],
		[value: -12.1257d, sig: 3, result: -12.1d],
		[value: -0.0681d, sig: 3, result: -0.0681d],
		[value: -5d, sig: 3, result: -5d],
	]

	@Test
	void "round"() {
		epsilon = 10**-9
		inputs.each {
			double res = roundToSignificant it.value, it.sig
			assertDecimalEquals res, it.result
		}
	}
}
