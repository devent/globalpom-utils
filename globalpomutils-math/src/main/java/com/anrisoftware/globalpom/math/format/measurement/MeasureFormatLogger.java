/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format.measurement;


import static com.anrisoftware.globalpom.math.format.measurement.MeasureFormatLogger.m.unparseable;
import static com.anrisoftware.globalpom.math.format.measurement.MeasureFormatLogger.m.unparseable_message;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link MeasureFormat}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class MeasureFormatLogger extends AbstractLogger {

    enum m {

        unparseable("Unparseable physical measure"),

        unparseable_message("Unparseable physical measure: '{}'.");

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
     * Create logger for {@link MeasureFormat}.
     */
    public MeasureFormatLogger() {
        super(MeasureFormat.class);
    }

    ParseException errorParseValue(String source, ParsePosition pos) {
        return logException(new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }

    void checkString(String[] str, String source, ParsePosition pos) throws ParseException {
        if (str.length == 2) {
            return;
        }
        throw logException(new ParseException(unparseable.toString(), pos.getErrorIndex()),
                unparseable_message.toString(), source);
    }
}
