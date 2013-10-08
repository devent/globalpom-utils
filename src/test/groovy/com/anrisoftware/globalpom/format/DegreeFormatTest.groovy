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
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.format.degree.DegreeSexagesimalFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.measure.unit.NonSI.*

import org.jscience.physics.amount.Amount
import org.junit.Test

/**
 * @see DegreeSexagesimalFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class DegreeFormatTest {

	static formats = [
		[format: "40°11'15\"", value: Amount.valueOf(40.1875d, DEGREE_ANGLE)],
		[format: "40°11'15.13\"", value: Amount.valueOf(40.18753611d, DEGREE_ANGLE)],
		[format: "40°12'", value: Amount.valueOf(40.2d, DEGREE_ANGLE)],
	]

	static outputs = [
		[input: "40°11'15\"", value: Amount.valueOf(40.1875d, DEGREE_ANGLE), decimal: 4],
		[input: "40°11'15.13\"", value: Amount.valueOf(40.18753611d, DEGREE_ANGLE), decimal: 8],
		[input: "40°12'", value: Amount.valueOf(40.2d, DEGREE_ANGLE), decimal: 1],
	]

	@Test
	void "format degree sexagesimal"() {
		def format = create(4)
		formats.each {
			def str = format.format it.value
			assertStringContent str, it.format
		}
	}

	@Test
	void "parse degree sexagesimal"() {
		outputs.each {
			def value = create(it.decimal).parse it.input
			assert value == it.value
		}
	}
}
