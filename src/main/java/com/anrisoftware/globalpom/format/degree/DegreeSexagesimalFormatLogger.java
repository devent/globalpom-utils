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
package com.anrisoftware.globalpom.format.degree;

import static com.anrisoftware.globalpom.format.degree.DegreeSexagesimalFormatLogger._.error_parse;
import static com.anrisoftware.globalpom.format.degree.DegreeSexagesimalFormatLogger._.error_parse1;
import static com.anrisoftware.globalpom.format.degree.DegreeSexagesimalFormatLogger._.error_parse_number;
import static java.lang.String.format;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link DegreeSexagesimalFormat}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class DegreeSexagesimalFormatLogger extends AbstractLogger {

	enum _ {

		error_parse("Error parse source degree sexagesimal"),

		error_parse1("Error parse source '{}' as degree sexagesimal."),

		error_parse_number("Error parse source '{}' as number.");

		private String name;

		private _(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	/**
	 * Create logger for {@link DegreeSexagesimalFormat}.
	 */
	DegreeSexagesimalFormatLogger() {
		super(DegreeSexagesimalFormat.class);
	}

	ParseException errorParse(String source, ParsePosition pos) {
		return logException(
				new ParseException(format(error_parse.toString(), source),
						pos.getErrorIndex()), error_parse1, source);
	}

	void checkMatches(boolean matches, String source, ParsePosition pos)
			throws ParseException {
		if (!matches) {
			throw logException(
					new ParseException(format(error_parse.toString(), source),
							pos.getErrorIndex()), error_parse1, source);
		}
	}

	void errorParseNumber(NumberFormatException e, String source) {
		logException(e, error_parse_number, source);
	}
}
