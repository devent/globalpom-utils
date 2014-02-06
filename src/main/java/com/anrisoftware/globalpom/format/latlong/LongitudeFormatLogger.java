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
package com.anrisoftware.globalpom.format.latlong;

import static com.anrisoftware.globalpom.format.latlong.LongitudeFormatLogger._.longitude_error;
import static com.anrisoftware.globalpom.format.latlong.LongitudeFormatLogger._.longitude_error_message;
import static com.anrisoftware.globalpom.format.latlong.LongitudeFormatLogger._.parse_error;
import static com.anrisoftware.globalpom.format.latlong.LongitudeFormatLogger._.parse_error_message;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.containsAny;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Pattern;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link LongitudeFormat}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class LongitudeFormatLogger extends AbstractLogger {

    enum _ {

        parse_error_message("Unparseable longitude: '{}'"),

        parse_error("Unparseable longitude: '%s'"),

        longitude_error("Wrong format for longitude"),

        longitude_error_message("Wrong format for longitude '{}'.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final Pattern WORD_PATTERN = Pattern.compile("\\w");

    private static final char EAST = 'E';

    private static final char WEST = 'W';

    /**
     * Create logger for {@link LatitudeFormat}.
     */
    LongitudeFormatLogger() {
        super(LatitudeFormat.class);
    }

    ParseException errorParseLatitude(String source, ParsePosition pos) {
        ParseException ex = new ParseException(format(parse_error.toString(),
                source), pos.getErrorIndex());
        return logException(ex, parse_error_message, source);
    }

    void checkLongitude(String source, ParsePosition pos) throws ParseException {
        if (WORD_PATTERN.matcher(source).matches()
                && !containsAny(source, EAST, WEST)) {
            ParseException ex = new ParseException(format(
                    longitude_error.toString(), source), pos.getErrorIndex());
            throw logException(ex, longitude_error_message, source);
        }
    }

}
