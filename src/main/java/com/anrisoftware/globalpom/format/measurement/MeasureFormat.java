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
package com.anrisoftware.globalpom.format.measurement;

import static org.apache.commons.lang3.StringUtils.remove;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;

import com.anrisoftware.globalpom.measurement.Measure;
import com.anrisoftware.globalpom.measurement.MeasureFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueToString;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses physical {@link Measure} measurement.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class MeasureFormat extends Format {

    private static final String SEP = ";";

    private final Pattern VALUE_PATTERN = Pattern.compile("^(.*?;)+((.*?);)$");

    private final Format valueFormat;

    private final NumberFormat numberFormat;

    private final MeasureFactory measureFactory;

    @Inject
    private ValueToString valueToString;

    @Inject
    private MeasureFormatLogger log;

    /**
     * @see MeasureFormatFactory#create(MeasureFactory, Format)
     */
    @AssistedInject
    MeasureFormat(@Assisted MeasureFactory measureFactory,
            @Assisted Format valueFormat) {
        this(measureFactory, valueFormat, new DecimalFormat("#.#########"));
    }

    /**
     * @see MeasureFormatFactory#create(MeasureFactory, Format, NumberFormat)
     */
    @AssistedInject
    MeasureFormat(@Assisted MeasureFactory measureFactory,
            @Assisted Format valueFormat, @Assisted NumberFormat format) {
        this.measureFactory = measureFactory;
        this.valueFormat = valueFormat;
        this.numberFormat = format;
    }

    /**
     * Formats the specified measurement.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;[(&lt;uncertainty&gt;);&lt;significant&gt;;&lt;decimal&gt;;&lt;unit&gt;;]
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123;m/s}
     * <li>uncertain value: {@code 5.0(0.2);1;1;m/s;}
     * </ul>
     *
     * @param obj
     *            the {@link Measure}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Measure) {
            formatMeasure((Measure<?>) obj, buff);
        }
        return buff;
    }

    private void formatMeasure(Measure<?> measure, StringBuffer buff) {
        valueToString.format(buff, measure, numberFormat);
        buff.append(measure.getUnit().toString());
        buff.append(SEP);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Parses the specified string to physical measurement.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * &lt;value&gt;[(&lt;uncertainty&gt;);&lt;significant&gt;;&lt;decimal&gt;;&lt;unit&gt;;]
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123;m/s}
     * <li>uncertain value: {@code 5.0(0.2);1;1;m/s;}
     * </ul>
     *
     * @return the parsed {@link Measure}.
     *
     * @param <UnitType>
     *            the {@link Quantity} of the unit.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     *
     * @since 1.11
     */
    public <UnitType extends Quantity> Measure<UnitType> parse(String source)
            throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Measure<?> result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParseValue(source, pos);
        }
        return toUnitType(result);
    }

    @SuppressWarnings("unchecked")
    private <UnitType extends Quantity> Measure<UnitType> toUnitType(
            Measure<?> result) {
        return (Measure<UnitType>) result;
    }

    /**
     * @see #parse(String)
     *
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
     */
    public Measure<?> parse(String source, ParsePosition pos) {
        source = source.substring(pos.getIndex());
        try {
            Measure<?> address = parseValue(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return address;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private Measure<?> parseValue(String string, ParsePosition pos)
            throws ParseException {
        Matcher matcher = VALUE_PATTERN.matcher(string);
        log.checkString(matcher, string, pos);
        String valuestr = matcher.group(1);
        String unitstr = matcher.group(2);
        valuestr = remove(string, unitstr);
        Value value = (Value) valueFormat.parseObject(valuestr);
        unitstr = matcher.group(3);
        Unit<?> unit = (Unit<?>) UnitFormat.getInstance().parseObject(unitstr);
        return measureFactory.create(value, unit);
    }
}
