/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.DAYS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.HOURS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.MILLISECONDS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.MINUTES;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.MONTHS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.SECONDS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.WEEKS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.YEARS;
import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.parseUnitMultiplier;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.inject.Inject;
import javax.measure.Measurable;
import javax.measure.unit.SI;

import org.joda.time.Duration;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses simple duration.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
@SuppressWarnings("serial")
public class DurationSimpleFormat extends Format {

    /**
     * Rounds the size to the smallest duration unit.
     *
     * @param size the size.
     *
     * @return the rounded size with the duration unit.
     */
    public static String roundSizeUnit(long size) {
        if (size > YEARS.getValue()) {
            return formatRoundSize(size, YEARS);
        }
        if (size > MONTHS.getValue()) {
            return formatRoundSize(size, MONTHS);
        }
        if (size > WEEKS.getValue()) {
            return formatRoundSize(size, WEEKS);
        }
        if (size > DAYS.getValue()) {
            return formatRoundSize(size, DAYS);
        }
        if (size > HOURS.getValue()) {
            return formatRoundSize(size, HOURS);
        }
        if (size > MINUTES.getValue()) {
            return formatRoundSize(size, MINUTES);
        }
        if (size > SECONDS.getValue()) {
            return formatRoundSize(size, SECONDS);
        }
        return formatRoundSize(size, MILLISECONDS);
    }

    private static String formatRoundSize(long size, UnitMultiplier unit) {
        long bytes = (long) (size / unit.getValue());
        String metric = unit.getMetric();
        return String.format("%s%s", Long.toString(bytes), metric);
    }

    private static final String SEP = "";

    private static final Pattern PARSE_PATTERN = Pattern.compile(String.format("(\\d+)(%s|%s|%s|%s|%s|%s|%s|%s)?",
            MILLISECONDS.getMetric(), SECONDS.getMetric(), MINUTES.getMetric(), HOURS.getMetric(), DAYS.getMetric(),
            WEEKS.getMetric(), MONTHS.getMetric(), YEARS.getMetric()));

    private final NumberFormat numberFormat;

    @Inject
    private DurationSimpleFormatLogger log;

    /**
     * @see DurationSimpleFormatFactory#create()
     */
    @AssistedInject
    DurationSimpleFormat() {
        this(NumberFormat.getIntegerInstance());
    }

    /**
     * @see DurationSimpleFormatFactory#create(NumberFormat)
     */
    @AssistedInject
    DurationSimpleFormat(@Assisted NumberFormat format) {
        this.numberFormat = format;
    }

    /**
     * Formats the specified simple duration. The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;s
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "60s"}
     * </ul>
     *
     * @param obj the {@link Measurable} or {@link Number}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        return format(obj, buff, pos, SECONDS);
    }

    /**
     * @param obj        the {@link Measurable} or {@link Number}.
     *
     * @param multiplier the unit {@link UnitMultiplier}.
     *
     * @see #format(Object)
     *
     * @return the {@link String} formatted simple duration.
     */
    public String format(Object obj, UnitMultiplier multiplier) {
        return format(obj, new StringBuffer(), new FieldPosition(0), multiplier).toString();
    }

    /**
     * Formats the specified simple duration. The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;s
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64s"}
     * </ul>
     *
     * @param obj        the {@link Measurable} or {@link Number}.
     *
     * @param buff       the {@link StringBuffer}.
     *
     * @param pos        the {@link FieldPosition}.
     *
     * @param multiplier the unit {@link UnitMultiplier}.
     *
     * @return the {@link StringBuffer}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos, UnitMultiplier multiplier) {
        if (obj instanceof Measurable) {
            Measurable measurable = (Measurable) obj;
            long value = measurable.longValue(SI.SECOND.divide(1000));
            value /= multiplier.getValue();
            formatMeasurable(value, buff, multiplier);
        } else if (obj instanceof Duration) {
            Duration value = (Duration) obj;
            long millis = value.getMillis();
            millis /= multiplier.getValue();
            formatMeasurable(millis, buff, multiplier);
        } else if (obj instanceof Number) {
            Number value = (Number) obj;
            formatMeasurable(value.longValue(), buff, multiplier);
        }
        return buff;
    }

    private void formatMeasurable(long value, StringBuffer buff, UnitMultiplier multiplier) {
        buff.append(numberFormat.format(value)).append(SEP);
        buff.append(multiplier.toString());
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos, MILLISECONDS);
    }

    /**
     * Parses the specified string to simple duration. The format follows the
     * pattern:
     *
     * <pre>
     * &lt;value&gt;(ms|s|m,h,...,M,y)
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64s"}
     * <li>{@code "1m"}
     * <li>{@code "1h"}
     * </ul>
     *
     * @param source the {@link String} source to parse.
     *
     * @return the parsed value.
     *
     * @throws ParseException if the string cannot be parsed to a value.
     */
    public long parse(String source) throws ParseException {
        return parse(source, MILLISECONDS);
    }

    /**
     * Parses the specified string to simple duration. The format follows the
     * pattern:
     *
     * <pre>
     * &lt;value&gt;(ms|s|m,h,...,M,y)
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64s"}
     * <li>{@code "1m"}
     * <li>{@code "1h"}
     * </ul>
     *
     * @param source     the {@link String} source to parse.
     *
     * @param multiplier the unit {@link UnitMultiplier}.
     *
     * @return the parsed value.
     *
     * @throws ParseException if the string cannot be parsed to a value.
     */
    public long parse(String source, UnitMultiplier multiplier) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Long result = parse(source, pos, multiplier);
        if (pos.getIndex() == 0) {
            throw log.errorParseValue(source, pos);
        }
        return result;
    }

    /**
     * @see #parse(String)
     *
     * @param source     the {@link String} the source.
     *
     * @param pos        the index {@link ParsePosition} position from where to
     *                   start parsing.
     *
     * @param multiplier the {@link UnitMultiplier}
     *
     * @return the {@link Long} duration.
     */
    public Long parse(String source, ParsePosition pos, UnitMultiplier multiplier) {
        source = source.substring(pos.getIndex());
        try {
            long value = parseValue(source);
            value /= multiplier.getValue();
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return value;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private long parseValue(String string) throws ParseException {
        Matcher matcher = PARSE_PATTERN.matcher(string);
        log.checkString(string, matcher);
        String svalue = matcher.group(1);
        String metric = matcher.group(2);
        long value = numberFormat.parse(svalue).longValue();
        if (metric != null && metric.length() > 0) {
            UnitMultiplier multiplier = parseUnitMultiplier(metric);
            log.checkMultiplier(multiplier, string);
            value *= multiplier.getValue();
        }
        return value;
    }
}
