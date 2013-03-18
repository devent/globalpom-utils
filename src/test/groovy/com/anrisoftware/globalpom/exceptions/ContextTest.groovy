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
package com.anrisoftware.globalpom.exceptions

import org.junit.Test

class ContextTest {

	@Test
	void "context with exception"() {
		def message = "Exception message"
		def ex = new Exception(message)
		def context = new Context(ex)
		context.addContext("A1", "value 1")
		context.addContext("A2", "value 2")
		assert context.toString() ==
		"""$message, context:
A2 := value 2
A1 := value 1"""
	}

	@Test
	void "no context"() {
		def message = "Exception message"
		def ex = new Exception(message)
		def context = new Context(ex)
		assert context.toString() ==
		"""$message"""
	}
}
