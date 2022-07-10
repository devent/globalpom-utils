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
package com.anrisoftware.globalpom.core.durationformat;


import static com.anrisoftware.globalpom.core.durationformat.DurationFormatLogger.m.errormparse;
import static com.anrisoftware.globalpom.core.durationformat.DurationFormatLogger.m.errormparse1;
import static com.anrisoftware.globalpom.core.durationformat.DurationFormatLogger.m.errormparsemnumber;
import static java.lang.String.format;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link DurationFormat}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class DurationFormatLogger extends AbstractLogger {

    enum m {

        errormparse("Error parse source as ISO 8601 duration"),

        errormparse1("Error parse source '{}' as ISO 8601 duration."),

        errormparsemnumber("Error parse source '{}' as number.");

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
     * Create logger for {@link DurationFormat}.
     */
    DurationFormatLogger() {
        super(DurationFormat.class);
    }

    ParseException errorParse(String source, ParsePosition pos) {
        return logException(new ParseException(format(errormparse.toString(), source), pos.getErrorIndex()),
                errormparse1, source);
    }

    void checkMatches(boolean matches, String source, ParsePosition pos) throws ParseException {
        if (!matches) {
            throw logException(new ParseException(format(errormparse.toString(), source), pos.getErrorIndex()),
                    errormparse1, source);
        }
    }

    void errorParseNumber(NumberFormatException e, String source) {
        logException(e, errormparsemnumber, source);
    }
}
