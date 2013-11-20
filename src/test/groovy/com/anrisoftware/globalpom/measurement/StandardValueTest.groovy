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
 * @see StandardValue
 * @see StandardValueData
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StandardValueTest extends ValueTestBase {

	@Test
	void "standard value"() {
		standardValueData.each {
			epsilon = it.epsilon
			def x = it.x
			def y = it.y
			def f = it.f(x, y)
			log.info "$it.name x:=$x; y:=$y; $it.func=$f"
			it.result(f)
			it.rounded(f)
		}
	}
}
