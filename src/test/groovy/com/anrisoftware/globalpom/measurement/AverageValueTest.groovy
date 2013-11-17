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
import static org.apache.commons.math3.util.FastMath.*
import groovy.util.logging.Slf4j

import org.junit.Test

/**
 * @see AverageValue
 * @see AverageValueData
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Slf4j
class AverageValueTest extends ValueTestBase {

	@Test
	void "average value"() {
		averageValueData.each {
			epsilon = it.epsilon
			def x = it.x
			def y = it.y
			def f = it.f(x, y)
			log.info "$it.name x:=$x; y:=$y; $it.func=$f"
			it.result(f)
			it.rounded(f)
		}
	}

	@Test
	void "sub exact average"() {
		Value x = exactAverageValueFactory.create 6
		Value y = averageValueFactory.create 5, 1, 0.2d, 1
		Value z = x.sub y

		epsilon = 10**-9
		assertDecimalEquals z.value, 1d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.2d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 1d
		assertDecimalEquals z.uncertainty, 0.2d
	}

	@Test
	void "mul average average"() {
		Value x = averageValueFactory.create 2, 1, 0.2d, 1
		Value y = averageValueFactory.create 6, 2, 0.12d, 2
		Value z = x.mul y

		epsilon = 10**-5
		assertDecimalEquals z.value, 12d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.12d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 12d
		assertDecimalEquals z.uncertainty, 0.1d
	}

	@Test
	void "mul average exact"() {
		Value x = averageValueFactory.create 2, 1, 0.2d, 1
		Value y = exactAverageValueFactory.create 3
		Value z = x.mul y

		epsilon = 10**-9
		assertDecimalEquals z.value, 6d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.6d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 6d
		assertDecimalEquals z.uncertainty, 0.6d
	}

	@Test
	void "mul average exact big"() {
		Value x = averageValueFactory.create 2, 1, 0.2d, 1
		Value y = exactAverageValueFactory.create 6
		Value z = x.mul y

		epsilon = 10**-9
		assertDecimalEquals z.value, 12d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 1.2d

		z = z.roundedValue
		assert z.significant == 2
		assert z.decimal == 1
		assertDecimalEquals z.value, 12d
		assertDecimalEquals z.uncertainty, 1.2d
	}

	@Test
	void "mul exact average"() {
		Value x = exactAverageValueFactory.create 3
		Value y = averageValueFactory.create 2, 1, 0.2d, 1
		Value z = x.mul y

		epsilon = 10**-9
		assertDecimalEquals z.value, 6d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.6d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 6d
		assertDecimalEquals z.uncertainty, 0.6d
	}

	@Test
	void "div average average"() {
		Value x = averageValueFactory.create 3, 1, 0.2d, 1
		Value y = averageValueFactory.create 6, 2, 0.12d, 2
		Value z = x.div y

		epsilon = 10**-5
		assertDecimalEquals z.value, 0.5d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.08666d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 0.5d
		assertDecimalEquals z.uncertainty, 0.09d
	}

	@Test
	void "div average exact"() {
		Value x = averageValueFactory.create 6, 1, 0.2d, 1
		Value y = exactAverageValueFactory.create 2
		Value z = x.div y

		epsilon = 10**-9
		assertDecimalEquals z.value, 3d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.4d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 3d
		assertDecimalEquals z.uncertainty, 0.4d
	}

	@Test
	void "div exact average"() {
		Value x = exactAverageValueFactory.create 3
		Value y = averageValueFactory.create 6, 1, 0.2d, 1
		Value z = x.div y

		epsilon = 10**-9
		assertDecimalEquals z.value, 0.5d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.6d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 0.5d
		assertDecimalEquals z.uncertainty, 0.6d
	}

	@Test
	void "function add sub average"() {
		Value w = averageValueFactory.create 4.52, 2, 0.02, 2
		Value x = averageValueFactory.create 2.0, 1, 0.2, 1
		Value y = averageValueFactory.create 3.0, 1, 0.6, 1
		Value z = x.add y sub w

		epsilon = 10**-1
		assertDecimalEquals z.value, 2.0d + 3.0d - 4.52d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 0.2d + 0.6d + 0.02d

		z = z.roundedValue
		assert z.significant == 1
		assert z.decimal == 1
		assertDecimalEquals z.value, 0.5d
		assertDecimalEquals z.uncertainty, 0.8d
	}

	@Test
	void "function mul exact"() {
		Value x = averageValueFactory.create 3.0, 1, 0.2, 1
		Value two = exactAverageValueFactory.create 2
		Value y = two.mul π mul x

		epsilon = 10**-3
		assertDecimalEquals y.value, 2.0d * PI * 3.0
		assert !y.exact
		assertDecimalEquals y.uncertainty, 0.2d * PI * 2.0d

		y = y.roundedValue
		assert y.significant == 2
		assert y.decimal == 1
		assertDecimalEquals y.value, 18.8d
		assertDecimalEquals y.uncertainty, 1.3d
	}

	@Test
	void "function sub mul exact"() {
		Value x = averageValueFactory.create 2.0, 1, 0.2, 1
		Value y = averageValueFactory.create 3.0, 1, 0.6, 1
		Value two = exactAverageValueFactory.create 2
		Value z = x.sub({ two.mul y }())

		epsilon = 10**-3
		assertDecimalEquals z.value, -4d
		assert !z.exact
		assertDecimalEquals z.uncertainty, 1.4d

		z = z.roundedValue
		assert z.significant == 2
		assert z.decimal == 1
		assertDecimalEquals z.value, -4d
		assertDecimalEquals z.uncertainty, 1.4d
	}

	@Test
	void "to string"() {
		Value x = averageValueFactory.create 2.0, 1, 0.2, 1
		assert x.toString() == "AverageValue[value=2.00,uncertainty=0.20,significant=1]"
	}
}
