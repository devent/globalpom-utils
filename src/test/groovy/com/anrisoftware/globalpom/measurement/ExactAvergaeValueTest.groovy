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
package com.anrisoftware.globalpom.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.Test

import com.anrisoftware.globalpom.measurement.Value;

/**
 * @see ExactAverageValue
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ExactAvergaeValueTest extends ValueTestBase {

	@Test
	void "add exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.add valueb
		Value z = valuec.roundedValue
		assert valuec.value == 11
		assert valuec.exact
		assert z.value == 11
		assert z.exact
	}

	@Test
	void "sub exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.sub valueb
		assert valuec.value == -1
		assert valuec.exact
	}

	@Test
	void "mul exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.mul valueb
		assertDecimalEquals valuec.value, 5d*6d
		assert valuec.exact
	}

	@Test
	void "div exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.div valueb
		assertDecimalEquals valuec.value, 5d/6d
		assert valuec.exact
	}

	@Test
	void "exact string"() {
		Value value = exactAverageValueFactory.create 5
		assert value.toString() == "ExactAverageValue[5.000000]"
	}
}
