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
package com.anrisoftware.globalpom.durationsimpleformat;

import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.DAYS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.HOURS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.MILLISECONDS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.MINUTES;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.MONTHS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.SECONDS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.WEEKS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.YEARS;
import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.parseUnitMultiplier;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
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
     * @param size
     *            the size.
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

    private static final Pattern PARSE_PATTERN = Pattern.compile(String.format(
            "(\\d+)(%s|%s|%s|%s|%s|%s|%s|%s)?", MILLISECONDS.getMetric(),
            SECONDS.getMetric(), MINUTES.getMetric(), HOURS.getMetric(),
            DAYS.getMetric(), WEEKS.getMetric(), MONTHS.getMetric(),
            YEARS.getMetric()));

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
     * Formats the specified simple duration.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;s
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "60s"}
     * </ul>
     *
     * @param obj
     *            the {@link Measurable} or {@link Number}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        return format(obj, buff, pos, SECONDS);
    }

    /**
     * @param multiplier
     *            the unit {@link UnitMultiplier}.
     *
     * @see #format(Object)
     */
    public String format(Object obj, UnitMultiplier multiplier) {
        return format(obj, new StringBuffer(), new FieldPosition(0), multiplier)
                .toString();
    }

    /**
     * Formats the specified simple duration.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;s
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64s"}
     * </ul>
     *
     * @param obj
     *            the {@link Measurable} or {@link Number}.
     *
     * @param multiplier
     *            the unit {@link UnitMultiplier}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public StringBuffer format(Object obj, StringBuffer buff,
            FieldPosition pos, UnitMultiplier multiplier) {
        if (obj instanceof Measurable) {
            Measurable measurable = (Measurable) obj;
            formatMeasurable(measurable.longValue(SI.SECOND), buff, multiplier);
        } else if (obj instanceof Duration) {
            Duration value = (Duration) obj;
            formatMeasurable(value.getMillis(), buff, multiplier);
        } else if (obj instanceof Number) {
            Number value = (Number) obj;
            formatMeasurable(value.longValue(), buff, multiplier);
        }
        return buff;
    }

    private void formatMeasurable(long value, StringBuffer buff,
            UnitMultiplier multiplier) {
        buff.append(numberFormat.format(value)).append(SEP);
        if (multiplier == SECONDS) {
            buff.append(SECONDS.getMetric());
        } else {
            buff.append(multiplier.toString());
        }
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos, MILLISECONDS);
    }

    /**
     * Parses the specified string to simple duration.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;(ms|s|m,h,...,M,y)
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64s"}
     * <li>{@code "1m"}
     * <li>{@code "1h"}
     * </ul>
     *
     * @return the parsed value.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     */
    public long parse(String source) throws ParseException {
        return parse(source, MILLISECONDS);
    }

    /**
     * Parses the specified string to simple duration.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;(ms|s|m,h,...,M,y)
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64s"}
     * <li>{@code "1m"}
     * <li>{@code "1h"}
     * </ul>
     *
     * @param multiplier
     *            the unit {@link UnitMultiplier}.
     *
     * @return the parsed value.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     */
    public long parse(String source, UnitMultiplier multiplier)
            throws ParseException {
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
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
     */
    public Long parse(String source, ParsePosition pos,
            UnitMultiplier multiplier) {
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