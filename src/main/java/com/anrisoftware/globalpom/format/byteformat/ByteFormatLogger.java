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
package com.anrisoftware.globalpom.format.byteformat;

import static com.anrisoftware.globalpom.format.byteformat.ByteFormatLogger._.unparseable;
import static com.anrisoftware.globalpom.format.byteformat.ByteFormatLogger._.unparseable_message;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link ValueFormat.class}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class ByteFormatLogger extends AbstractLogger {

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
     * Create logger for {@link ValueFormat.class}.
     */
    public ByteFormatLogger() {
        super(ByteFormat.class);
    }

    ParseException errorParseValue(String source, ParsePosition pos) {
        return logException(
                new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }

    void checkString(String[] str, String source) throws ParseException {
        if (str.length == 0 || str.length > 2) {
            throw logException(new ParseException(unparseable.toString(), 0),
                    unparseable_message.toString(), source);
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
