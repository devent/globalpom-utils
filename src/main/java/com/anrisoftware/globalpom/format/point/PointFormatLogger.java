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
