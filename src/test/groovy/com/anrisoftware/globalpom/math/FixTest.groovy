/*
 * Copyright 2012-2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of fractions-integer.
 *
 * fractions-integer is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * fractions-integer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * fractions-integer. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.math

import org.junit.Test

/**
 * Test the round to zero algorithm.
 *
 * @see MathUtils
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class FixTest {

	static double[] inputs = [
		-5.2,
		5.2,
		-1.9,
		-0.2,
		3.4,
		5.6,
		7.0
	]

	static double[] outputs = [
		-5.0,
		5.0,
		-1.0,
		0.0,
		3.0,
		5.0,
		7.0
	]

	@Test
	void "fix inputs to outputs"() {
		inputs.eachWithIndex { double value, int i ->
			double result = MathUtils.fix(value)
			assert outputs[i] == result
		}
	}
}
