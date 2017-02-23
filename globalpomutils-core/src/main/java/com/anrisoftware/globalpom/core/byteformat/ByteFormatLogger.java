/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.core.byteformat;

import static com.anrisoftware.globalpom.core.byteformat.ByteFormatLogger._.unparseable;
import static com.anrisoftware.globalpom.core.byteformat.ByteFormatLogger._.unparseable_message;

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
