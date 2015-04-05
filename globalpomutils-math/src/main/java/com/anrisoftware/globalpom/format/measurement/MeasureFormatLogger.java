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
package com.anrisoftware.globalpom.format.measurement;

import static com.anrisoftware.globalpom.format.measurement.MeasureFormatLogger._.unparseable;
import static com.anrisoftware.globalpom.format.measurement.MeasureFormatLogger._.unparseable_message;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link MeasureFormat}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class MeasureFormatLogger extends AbstractLogger {

    enum _ {

        unparseable("Unparseable physical measure"),

        unparseable_message("Unparseable physical measure: '{}'.");

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
     * Create logger for {@link MeasureFormat}.
     */
    public MeasureFormatLogger() {
        super(MeasureFormat.class);
    }

    ParseException errorParseValue(String source, ParsePosition pos) {
        return logException(
                new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }

    void checkString(Matcher matcher, String source, ParsePosition pos)
            throws ParseException {
        if (matcher.matches()) {
            return;
        }
        throw logException(
                new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }
}
