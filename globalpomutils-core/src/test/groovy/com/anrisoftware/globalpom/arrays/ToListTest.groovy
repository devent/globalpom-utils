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
package com.anrisoftware.globalpom.arrays

import org.junit.Test

/**
 * @see ToList
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ToListTest {

	@Test
	void "to list"() {
		inputs.each {
			assert ToList.toList(it.obj) == it.list
		}
	}

	static inputs = [
		[obj: ["A", "B"], list: ["A", "B"]],
		[obj: [1.0d, 2.0d] as double[], list: [1.0d, 2.0d]],
		[obj: [1l, 2l] as long[], list: [1l, 2l]],
		[obj: ["A", "B"] as String[], list: ["A", "B"]],
		[obj: "A", list: ["A"]],
	]
}
