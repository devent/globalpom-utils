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
package com.anrisoftware.globalpom.format.measurement;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link PointFormat.class}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
class ValueFormatLogger extends AbstractLogger {

	private static final String TWO_DIMENSIONS_MESSAGE = "Point needs two dimensions: '{}'";
	private static final String TWO_DIMENSIONS = "Point needs two dimensions";
	private static final String UNPARSEABLE_POINT_MESSAGE = "Unparseable point: '{}'";
	private static final String UNPARSEABLE_POINT = "Unparseable point";

	/**
	 * Create logger for {@link PointFormat.class}.
	 */
	public ValueFormatLogger() {
		super(ValueFormat.class);
	}

	ParseException errorParsePoint(String source, ParsePosition pos) {
		return logException(
				new ParseException(UNPARSEABLE_POINT, pos.getErrorIndex()),
				UNPARSEABLE_POINT_MESSAGE, source);
	}

	ParseException errorTwoPointsNeeded(String string) {
		return logException(new ParseException(TWO_DIMENSIONS, 0),
				TWO_DIMENSIONS_MESSAGE, string);
	}
}
