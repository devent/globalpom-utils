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
package com.anrisoftware.globalpom.core.pointformat;

import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link PointFormat}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.6
 */
class PointFormatLogger extends AbstractLogger {

    private static final String TWO_DIMENSIONS_MESSAGE = "Point needs two dimensions: '{}'";
    private static final String TWO_DIMENSIONS = "Point needs two dimensions";
    private static final String UNPARSEABLE_POINT_MESSAGE = "Unparseable point: '{}'";
    private static final String UNPARSEABLE_POINT = "Unparseable point";

    /**
     * Create logger for {@link PointFormat.class}.
     */
    public PointFormatLogger() {
        super(PointFormat.class);
    }

    ParseException errorParsePoint(String source, ParsePosition pos) {
        return logException(new ParseException(UNPARSEABLE_POINT, pos.getErrorIndex()), UNPARSEABLE_POINT_MESSAGE,
                source);
    }

    ParseException errorTwoPointsNeeded(String string) {
        return logException(new ParseException(TWO_DIMENSIONS, 0), TWO_DIMENSIONS_MESSAGE, string);
    }
}
