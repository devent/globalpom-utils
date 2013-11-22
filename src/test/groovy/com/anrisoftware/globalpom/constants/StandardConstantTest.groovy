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
package com.anrisoftware.globalpom.constants

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.apache.commons.math3.util.FastMath.*
import groovy.util.logging.Slf4j

import javax.measure.unit.SI

import org.junit.Test

/**
 * @see StandardConstant
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StandardConstantTest extends ConstantTestBase {

	@Test
	void "standard constant"() {
		def factor = exact.create(2.0)
		def value = value.create(5.0, 1, 0.1, 1)
		def constant = standardConstantFactory.create(value, SI.METERS_PER_SECOND)
		assert constant.toString() == "StandardConstant[5.0(0.1);1;1;m/s;]"
		constant = constant.add factor
		assert constant.toString() == "StandardConstant[7.0(0.1);1;1;m/s;]"
		constant = constant.mul factor
		assert constant.toString() == "StandardConstant[14.0(0.2);1;1;m/s;]"
		constant = constant.sub factor
		assert constant.toString() == "StandardConstant[12.0(0.2);1;1;m/s;]"
		constant = constant.div factor
		assert constant.toString() == "StandardConstant[6.0(0.1);1;1;m/s;]"
		constant = constant.log()
		assert constant.toString() == "StandardConstant[1.8(0.02);1;1;m/s;]"
		constant = constant.exp()
		assert constant.toString() == "StandardConstant[6.0(0.1);1;1;m/s;]"
	}

	@Test
	void "standard exact constant"() {
		def factor = exact.create(2.0)
		def value = exact.create(5.0)
		def constant = standardConstantFactory.create(value, SI.METERS_PER_SECOND)
		assert constant.toString() == "StandardConstant[5;m/s;]"
		constant = constant.add factor
		assert constant.toString() == "StandardConstant[7;m/s;]"
		constant = constant.mul factor
		assert constant.toString() == "StandardConstant[14;m/s;]"
		constant = constant.sub factor
		assert constant.toString() == "StandardConstant[12;m/s;]"
		constant = constant.div factor
		assert constant.toString() == "StandardConstant[6;m/s;]"
		constant = constant.log()
		assert constant.toString() == "StandardConstant[1.791759469;m/s;]"
		constant = constant.exp()
		assert constant.toString() == "StandardConstant[6;m/s;]"
	}
}
