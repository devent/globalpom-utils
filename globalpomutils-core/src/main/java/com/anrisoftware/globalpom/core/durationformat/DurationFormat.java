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
package com.anrisoftware.globalpom.core.durationformat;

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.google.inject.Guice.createInjector;
import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static java.util.regex.Pattern.compile;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.joda.time.Duration;

import com.google.inject.Injector;

/**
 * Parses ISO 8601 durations.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class DurationFormat extends Format {

    /**
     * Create the duration format.
     *
     * @return the {@link DurationFormat}.
     */
    public static DurationFormat createDurationFormat() {
        return create();
    }

    /**
     * Create the duration format.
     *
     * @return the {@link DurationFormat}.
     */
    public static DurationFormat create() {
        return InjectorInstance.injector.getInstance(DurationFormat.class);
    }

    private static class InjectorInstance {
        static final Injector injector = createInjector();
    }

    private static final Pattern PATTERN = compile("^P(([0-9]+)Y)?(([0-9]+)M)?(([0-9]+)D)?(T(([0-9]+)H)?(([0-9]+)M)?(([0-9\\.]+)S)?)?$");

    private static final double DAYS_MONTH = ((365 * 4) + 1) / 48;

    private static final double YEARS = DAYS_MONTH * 12 * 24 * 60 * 60 * 1000;

    private static final double MONTH = DAYS_MONTH * 24 * 60 * 60 * 1000;

    private static final double DAYS = 24 * 60 * 60 * 1000;

    private static final double HOURS = 60 * 60 * 1000;

    private static final double MINUTES = 60 * 1000;

    private static final double SECONDS = 1000;

    @Inject
    private DurationFormatLogger log;

    /**
     * Formats the specified duration.
     *
     * @param obj
     *            the {@link Duration}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Duration) {
            formatDuration(buff, (Duration) obj);
        }
        return buff;
    }

    private void formatDuration(StringBuffer buff, Duration duration) {
        buff.append(duration.toString());
    }

    /**
     * Parses the specified string to a duration.
     * <p>
     * <h2>Format</h2>
     * <p>
     * <ul>
     * <li>{@code "P[yY][mM][dD][T[hH][mM][s[.s]S]]"}
     * </ul>
     *
     * @return the parsed {@link Duration}.
     *
     * @see {@code http://www.ostyn.com/standards/scorm/samples/ISOTimeForSCORM.htm}
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        try {
            return parse(source, pos);
        } catch (ParseException e) {
            pos.setErrorIndex(pos.getIndex() + e.getErrorOffset());
            return null;
        }
    }

    /**
     * @see #parse(String, ParsePosition)
     */
    public Duration parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Duration result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParse(source, pos);
        }
        return result;
    }

    /**
     * @see #parseObject(String)
     *
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
     *
     * @throws ParseException
     *             if the string is not in the correct format.
     */
    public Duration parse(String source, ParsePosition pos)
            throws ParseException {
        try {
            source = source.substring(pos.getIndex());
            Duration duration = decodeDuration(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(pos.getIndex() + source.length());
            return duration;
        } catch (NumberFormatException e) {
            log.errorParseNumber(e, source);
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private Duration decodeDuration(String source, ParsePosition pos)
            throws ParseException {
        Matcher matcher = PATTERN.matcher(source);
        log.checkMatches(matcher.find(), source, pos);
        long y = (long) (parseLongSave(matcher.group(2)) * YEARS);
        long m = (long) (parseLongSave(matcher.group(4)) * MONTH);
        long d = (long) (parseLongSave(matcher.group(6)) * DAYS);
        long h = (long) (parseLongSave(matcher.group(9)) * HOURS);
        long min = (long) (parseLongSave(matcher.group(11)) * MINUTES);
        long s = (long) (parseDoubleSave(matcher.group(13)) * SECONDS);
        return new Duration(y + m + d + h + min + s);
    }

    private long parseLongSave(String string) {
        return string == null ? 0 : string.isEmpty() ? 0 : parseLong(string);
    }

    private double parseDoubleSave(String string) {
        return string == null ? 0 : string.isEmpty() ? 0 : parseDouble(string);
    }
}
