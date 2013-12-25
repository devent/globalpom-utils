/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.byteformat;

import static com.anrisoftware.globalpom.format.byteformat.UnitMultiplier.parseUnitMultiplier;
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
     * Formats the specified computer byte.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt; byte
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64 byte"}
     * </ul>
     *
     * @param obj
     *            the {@link Measurable} or {@link Number}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        return format(obj, buff, pos, UnitMultiplier.ONE);
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
     * Formats the specified computer byte.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt; byte
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64 byte"}
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
            formatMeasurable(measurable.longValue(NonSI.BYTE), buff, multiplier);
        } else if (obj instanceof Number) {
            Number value = (Number) obj;
            formatMeasurable(value.longValue(), buff, multiplier);
        }
        return buff;
    }

    private void formatMeasurable(long value, StringBuffer buff,
            UnitMultiplier multiplier) {
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
     * Parses the specified string to computer byte.
     * <p>
     * The format follows the pattern:
     * 
     * <pre>
     * &lt;value&gt;(byte|kB,MB,...,YB|KiB,MiB,...,YiB)
     * </pre>
     * 
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64 byte"}
     * <li>{@code "1 kb"}
     * <li>{@code "1 Kib"}
     * </ul>
     * 
     * @return the parsed value.
     * 
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     */
    public long parse(String source) throws ParseException {
        return parse(source, UnitMultiplier.ONE);
    }

    /**
     * Parses the specified string to computer byte.
     * <p>
     * The format follows the pattern:
     * 
     * <pre>
     * &lt;value&gt;(byte|kB,MB,...,YB|KiB,MiB,...,YiB)
     * </pre>
     * 
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>{@code "64 byte"}
     * <li>{@code "1 kb"}
     * <li>{@code "1 Kib"}
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
