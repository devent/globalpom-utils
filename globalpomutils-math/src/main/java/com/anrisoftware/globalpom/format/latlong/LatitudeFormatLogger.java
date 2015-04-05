/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.latlong;

import static com.anrisoftware.globalpom.format.latlong.LatitudeFormatLogger._.latitude_error;
import static com.anrisoftware.globalpom.format.latlong.LatitudeFormatLogger._.latitude_error_message;
import static com.anrisoftware.globalpom.format.latlong.LatitudeFormatLogger._.parse_error;
import static com.anrisoftware.globalpom.format.latlong.LatitudeFormatLogger._.parse_error_message;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.containsAny;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Pattern;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link LatitudeFormat}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class LatitudeFormatLogger extends AbstractLogger {

    enum _ {

        parse_error_message("Unparseable latitude: '{}'"),

        parse_error("Unparseable latitude: '%s'"),

        latitude_error("Wrong format for latitude"),

        latitude_error_message("Wrong format for latitude '{}'.");

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

    private static final char SOUTH = 'S';

    private static final char NORTH = 'N';

    /**
     * Create logger for {@link LatitudeFormat}.
     */
    LatitudeFormatLogger() {
        super(LatitudeFormat.class);
    }

    ParseException errorParseLatitude(String source, ParsePosition pos) {
        ParseException ex = new ParseException(format(parse_error.toString(),
                source), pos.getErrorIndex());
        return logException(ex, parse_error_message, source);
    }

    void checkLatitude(String source, ParsePosition pos) throws ParseException {
        if (WORD_PATTERN.matcher(source).matches()
                && !containsAny(source, SOUTH, NORTH)) {
            ParseException ex = new ParseException(format(
                    latitude_error.toString(), source), pos.getErrorIndex());
            throw logException(ex, latitude_error_message, source);
        }
    }

}
