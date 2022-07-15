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
package com.anrisoftware.globalpom.core.version;

import static com.anrisoftware.globalpom.core.version.VersionFormatLogger.m.error_number_format;
import static com.anrisoftware.globalpom.core.version.VersionFormatLogger.m.error_parse;
import static com.anrisoftware.globalpom.core.version.VersionFormatLogger.m.error_parse1;
import static java.lang.String.format;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link VersionFormat}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class VersionFormatLogger extends AbstractLogger {

    enum m {

        error_parse("Error parse source as version number"),

        error_parse1("Error parse source '{}' as version number."),

        error_number_format("Error parse '{}' as number.");

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
     * Create logger for {@link VersionFormat}.
     */
    VersionFormatLogger() {
        super(VersionFormat.class);
    }

    ParseException errorParse(String source, ParsePosition pos) {
        return logException(new ParseException(format(error_parse.toString(), source), pos.getErrorIndex()),
                error_parse1, source);
    }

    void errorParseNumber(NumberFormatException e, String source) {
        logException(e, error_number_format, source);
    }
}
