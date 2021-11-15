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
package com.anrisoftware.globalpom.core.durationsimpleformat;

import static com.anrisoftware.globalpom.core.durationsimpleformat.DurationSimpleFormatLogger.m.unparseable;
import static com.anrisoftware.globalpom.core.durationsimpleformat.DurationSimpleFormatLogger.m.unparseablemmessage;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link DurationSimpleFormat}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
class DurationSimpleFormatLogger extends AbstractLogger {

    enum m {

        unparseable("Unparseable value"),

        unparseablemmessage("Unparseable value: '{}'.");

        private String name;

        private m(String name) {
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
        return logException(new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseablemmessage.toString(), source);
    }

    void checkString(String string, Matcher matcher) throws ParseException {
        if (!matcher.matches()) {
            throw logException(new ParseException(unparseable.toString(), 0), unparseablemmessage.toString(), matcher);
        }
    }

    void checkMultiplier(UnitMultiplier multiplier, String source) throws ParseException {
        if (multiplier == null) {
            throw logException(new ParseException(unparseable.toString(), 0), unparseablemmessage.toString(), source);
        }
    }
}
