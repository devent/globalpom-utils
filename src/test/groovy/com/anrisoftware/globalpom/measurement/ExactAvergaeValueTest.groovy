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
import groovy.util.logging.Slf4j

import org.junit.Test

/**
 * @see ExactAverageValue
 * @see ExactAverageValueData
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Slf4j
class ExactAvergaeValueTest extends ValueTestBase {

	@Test
	void "exact value"() {
		exactAverageValueData.each {
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
