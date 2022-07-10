/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.localeformat;

import static com.anrisoftware.globalpom.core.localeformat.LocaleFormatLogger.m.unparseable;
import static com.anrisoftware.globalpom.core.localeformat.LocaleFormatLogger.m.unparseable_message;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link LocaleFormat}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class LocaleFormatLogger extends AbstractLogger {

    enum m {

        unparseable("Unparseable string to locale"),

        unparseable_message("Unparseable string to locale: '{}'.");

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
     * Create logger for {@link LocaleFormat.class}.
     */
    public LocaleFormatLogger() {
        super(LocaleFormat.class);
    }

    ParseException errorParse(String source, ParsePosition pos) {
        return logException(new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }
}
