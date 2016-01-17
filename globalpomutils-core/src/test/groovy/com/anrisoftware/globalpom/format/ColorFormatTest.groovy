/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format


import static com.anrisoftware.globalpom.format.color.ColorFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.awt.Color

import org.junit.Test

/**
 * @see ColorFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class ColorFormatTest {

	static formats = [
		[format: "#ff00ff", color: new Color(255, 0, 255)],
		[format: "#80ff00ff", color: new Color(255, 0, 255, 128)],
	]

	static outputs = [
		[input: "#ff00ff", color: new Color(255, 0, 255)],
		[input: "#80ff00ff", color: new Color(255, 0, 255, 128)],
	]

	@Test
	void "format color"() {
		def format = createColorFormat()
		formats.each {
			def str = format.format it.color
			assertStringContent str, it.format
		}
	}

	@Test
	void "parse socket address"() {
		def format = createColorFormat()
		outputs.each {
			def color = format.parse it.input
			assert color == it.color
		}
	}
}
