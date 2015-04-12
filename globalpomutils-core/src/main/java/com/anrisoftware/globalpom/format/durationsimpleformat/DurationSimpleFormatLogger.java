/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.format.durationsimpleformat;

import static com.anrisoftware.globalpom.format.durationsimpleformat.DurationSimpleFormatLogger._.unparseable;
import static com.anrisoftware.globalpom.format.durationsimpleformat.DurationSimpleFormatLogger._.unparseable_message;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link DurationSimpleFormat.class}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
class DurationSimpleFormatLogger extends AbstractLogger {

    enum _ {

        unparseable("Unparseable value"),

        unparseable_message("Unparseable value: '{}'.");

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
     * Create logger for {@link DurationSimpleFormat.class}.
     */
    public DurationSimpleFormatLogger() {
        super(DurationSimpleFormat.class);
    }

    ParseException errorParseValue(String source, ParsePosition pos) {
        return logException(
                new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }

    void checkString(String string, Matcher matcher) throws ParseException {
        if (!matcher.matches()) {
            throw logException(new ParseException(unparseable.toString(), 0),
                    unparseable_message.toString(), matcher);
        }
    }

    void checkMultiplier(UnitMultiplier multiplier, String source)
            throws ParseException {
        if (multiplier == null) {
            throw logException(new ParseException(unparseable.toString(), 0),
                    unparseable_message.toString(), source);
        }
    }
}
