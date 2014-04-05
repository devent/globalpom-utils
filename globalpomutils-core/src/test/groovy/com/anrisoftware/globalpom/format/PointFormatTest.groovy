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

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.awt.Point
import java.awt.geom.Point2D
import java.text.ParseException
import java.text.ParsePosition

import org.junit.Test

import com.anrisoftware.globalpom.format.point.PointFormat

/**
 * Test parsing and formatting of a point.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
class PointFormatTest {

	static inputs = [
		[source: "(1, 2)", point: null],
		[source: "(1,2)", point: null],
		[source: "1, 2", point: null],
		[source: "1,2", point: null],
		[source: "(1.1, 2.1)", point: null],
		[source: "(1.1,2.1)", point: null],
		[source: "1.1, 2.1", point: null],
		[source: "1.1,2.1", point: null],
		[source: "(1, 2)", point: new Point()],
		[source: "(1,2)", point: new Point()],
		[source: "1, 2", point: new Point()],
		[source: "1,2", point: new Point()],
	]

	static outputs = [
		new Point2D.Double(1, 2),
		new Point2D.Double(1, 2),
		new Point2D.Double(1, 2),
		new Point2D.Double(1, 2),
		new Point2D.Double(1.1, 2.1),
		new Point2D.Double(1.1, 2.1),
		new Point2D.Double(1.1, 2.1),
		new Point2D.Double(1.1, 2.1),
		new Point(1, 2),
		new Point(1, 2),
		new Point(1, 2),
		new Point(1, 2),
	]

	@Test
	void "format point, default decimal format"() {
		def format = new PointFormat()
		def str = format.format new Point2D.Double(1, 2)
		assertStringContent "(1, 2)", str

		str = format.format new Point2D.Double(1.1, 2.1)
		assertStringContent "(1.1, 2.1)", str

		str = format.format new Point(1, 2)
		assertStringContent "(1, 2)", str
	}

	@Test
	void "parse point, default decimal format"() {
		def format = new PointFormat()
		inputs.eachWithIndex { input, int i ->
			def point = format.parse input.source, new ParsePosition(0), input.point
			assert point == outputs[i]
		}
	}

	@Test
	void "parse not valid string"() {
		def format = new PointFormat()
		shouldFailWith(ParseException) { format.parse "(1)" }
	}
}
