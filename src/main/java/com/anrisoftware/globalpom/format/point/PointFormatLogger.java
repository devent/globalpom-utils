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
package com.anrisoftware.globalpom.format.point;

import static java.lang.String.format;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractSerializedLogger;

/**
 * Logging messages for {@link PointFormat.class}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
class PointFormatLogger extends AbstractSerializedLogger {

	/**
	 * Create logger for {@link PointFormat.class}.
	 */
	public PointFormatLogger() {
		super(PointFormat.class);
	}

	ParseException errorParsePoint(String source, ParsePosition pos) {
		String message = format("Unparseable point: '%s'", source);
		ParseException ex = new ParseException(message, pos.getErrorIndex());
		logException(message, ex);
		return ex;
	}

	ParseException errorTwoPointsNeeded(String string) {
		String message = format("Point needs two dimensions: '%s'", string);
		ParseException ex = new ParseException(message, 0);
		logException(message, ex);
		return ex;
	}
}
