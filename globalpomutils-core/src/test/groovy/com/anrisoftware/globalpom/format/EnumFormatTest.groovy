/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import java.text.Format

import org.junit.Before
import org.junit.Test

import com.anrisoftware.globalpom.format.enums.EnumFormat

/**
 * @see EnumFormat
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class EnumFormatTest {

	static enum TestEnum {
		FOO, BAR
	}

	@Test
	void "parse enum"() {
		inputs.each {
			assert format.parseObject(it.str) == it.item
		}
	}

	@Test
	void "format enum"() {
		inputs.each {
			assert format.format(it.item) == it.format
		}
	}

	Format format

	@Before
	void setupFormat() {
		format = new EnumFormat(TestEnum)
	}

	static inputs = [
		[str: "FOO", format: "FOO", item: TestEnum.FOO],
		[str: "BAR", format: "BAR", item: TestEnum.BAR],
		[str: "foo", format: "FOO", item: TestEnum.FOO],
	]
}
