/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.math.format.latlong;


import static com.anrisoftware.globalpom.math.format.latlong.LatitudeFormatLogger.m.latitude_error;
import static com.anrisoftware.globalpom.math.format.latlong.LatitudeFormatLogger.m.latitude_error_message;
import static com.anrisoftware.globalpom.math.format.latlong.LatitudeFormatLogger.m.parse_error;
import static com.anrisoftware.globalpom.math.format.latlong.LatitudeFormatLogger.m.parse_error_message;
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

    enum m {

        parse_error_message("Unparseable latitude: '{}'"),

        parse_error("Unparseable latitude: '%s'"),

        latitude_error("Wrong format for latitude"),

        latitude_error_message("Wrong format for latitude '{}'.");

        private String name;

        private m(String name) {
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
        ParseException ex = new ParseException(format(parse_error.toString(), source), pos.getErrorIndex());
        return logException(ex, parse_error_message, source);
    }

    void checkLatitude(String source, ParsePosition pos) throws ParseException {
        if (WORD_PATTERN.matcher(source).matches() && !containsAny(source, SOUTH, NORTH)) {
            ParseException ex = new ParseException(format(latitude_error.toString(), source), pos.getErrorIndex());
            throw logException(ex, latitude_error_message, source);
        }
    }

}
