/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import static com.anrisoftware.globalpom.core.byteformat.UnitMultiplier.parseUnitMultiplier;
import static org.apache.commons.lang3.StringUtils.split;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import javax.inject.Inject;
import javax.measure.Measurable;
import javax.measure.unit.NonSI;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses computer byte.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ByteFormat extends Format {

    /**
     * Rounds the size to the smallest SI unit.
     *
     * @param size the size.
     *
     * @return the rounded size with the SI unit.
     *
     * @since 2.3
     */
    public static String roundSizeSI(long size) {
        if (size > UnitMultiplier.ZETTA.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.ZETTA);
        }
        if (size > UnitMultiplier.EXA.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.EXA);
        }
        if (size > UnitMultiplier.PETA.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.PETA);
        }
        if (size > UnitMultiplier.TERA.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.TERA);
        }
        if (size > UnitMultiplier.GIGA.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.GIGA);
        }
        if (size > UnitMultiplier.MEGA.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.MEGA);
        }
        if (size > UnitMultiplier.KILO.getValue()) {
            return formatRoundSizeSI(size, UnitMultiplier.KILO);
        }
        return formatRoundSizeSI(size, UnitMultiplier.ONE);
    }

    private static String formatRoundSizeSI(long size, UnitMultiplier unit) {
        long bytes = (long) (size / unit.getValue());
        String metric = unit.getMetric();
        return String.format("%s%s", Long.toString(bytes), metric);
    }

    private static final String BYTE = "B";

    private static final String SEP = " ";

    private final NumberFormat numberFormat;

    @Inject
    private ByteFormatLogger log;

    /**
     * @see ByteFormatFactory#create()
     */
    @AssistedInject
    ByteFormat() {
        this(NumberFormat.getIntegerInstance());
    }

    /**
     * @see ByteFormatFactory#create(NumberFormat)
     */
    @AssistedInject
    ByteFormat(@Assisted NumberFormat format) {
        this.numberFormat = format;
    }

    /**
     * Formats the specified computer byte. The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt; byte
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64 byte"}
     * </ul>
     *
     * @param obj the {@link Measurable} or {@link Number}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        return format(obj, buff, pos, UnitMultiplier.ONE);
    }

    /**
     * @param multiplier the unit {@link UnitMultiplier}.
     *
     * @see #format(Object)
     */
    public String format(Object obj, UnitMultiplier multiplier) {
        return format(obj, new StringBuffer(), new FieldPosition(0), multiplier).toString();
    }

    /**
     * Formats the specified computer byte. The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt; byte
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64 byte"}
     * </ul>
     *
     * @param obj        the {@link Measurable} or {@link Number}.
     *
     * @param multiplier the unit {@link UnitMultiplier}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos, UnitMultiplier multiplier) {
        if (obj instanceof Measurable) {
            Measurable measurable = (Measurable) obj;
            formatMeasurable(measurable.longValue(NonSI.BYTE), buff, multiplier);
        } else if (obj instanceof Number) {
            Number value = (Number) obj;
            formatMeasurable(value.longValue(), buff, multiplier);
        }
        return buff;
    }

    private void formatMeasurable(long value, StringBuffer buff, UnitMultiplier multiplier) {
        buff.append(numberFormat.format(value)).append(SEP);
        if (multiplier == UnitMultiplier.ONE) {
            buff.append(NonSI.BYTE.toString());
        } else {
            buff.append(multiplier.toString()).append(BYTE);
        }
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos, UnitMultiplier.ONE);
    }

    /**
     * Parses the specified string to computer byte. The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;(byte|kB,MB,...,YB|KiB,MiB,...,YiB)
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64 byte"}
     * <li>{@code "1 kb"}
     * <li>{@code "1 Kib"}
     * </ul>
     *
     * @return the parsed value.
     *
     * @throws ParseException if the string cannot be parsed to a value.
     */
    public long parse(String source) throws ParseException {
        return parse(source, UnitMultiplier.ONE);
    }

    /**
     * Parses the specified string to computer byte. The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;(byte|kB,MB,...,YB|KiB,MiB,...,YiB)
     * </pre>
     *
     * <h2>Examples</h2>
     * <ul>
     * <li>{@code "64 byte"}
     * <li>{@code "1 kb"}
     * <li>{@code "1 Kib"}
     * </ul>
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
     * @param pos the index {@link ParsePosition} position from where to start
     *            parsing.
     *
     * @return the parsed {@link Long} value or {@code null.}
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
        String[] str = split(string, SEP);
        log.checkString(str, string);
        long value = numberFormat.parse(str[0]).longValue();
        if (str.length > 1) {
            if (!str[1].equals(NonSI.BYTE.toString())) {
                str = split(str[1], BYTE);
                UnitMultiplier multiplier = parseUnitMultiplier(str[0]);
                log.checkMultiplier(multiplier, string);
                value *= multiplier.getValue();
            }
        }
        return value;
    }
}
