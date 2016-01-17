/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.posixlocale;

import static com.anrisoftware.globalpom.posixlocale.PosixLocaleFormatLogger._.unparseable;
import static com.anrisoftware.globalpom.posixlocale.PosixLocaleFormatLogger._.unparseable_message;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link PosixLocaleFormat.class}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.6
 */
class PosixLocaleFormatLogger extends AbstractLogger {

    enum _ {

        unparseable("Unparseable string to POSIX locale"),

        unparseable_message("Unparseable string to POSIX locale: '{}'.");

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
     * Create logger for {@link PosixLocaleFormat.class}.
     */
    public PosixLocaleFormatLogger() {
        super(PosixLocaleFormat.class);
    }

    ParseException errorParse(String source, ParsePosition pos) {
        return logException(
                new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }
}
