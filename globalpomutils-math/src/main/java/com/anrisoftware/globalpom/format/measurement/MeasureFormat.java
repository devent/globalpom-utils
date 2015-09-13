/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.measurement;

import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;

import org.apache.commons.lang3.StringUtils;

import com.anrisoftware.globalpom.measurement.Measure;
import com.anrisoftware.globalpom.measurement.MeasureFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses physical {@link Measure} measurement.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
@SuppressWarnings("serial")
public class MeasureFormat extends Format {

    private final ValueFormat valueFormat;

    private final MeasureFactory measureFactory;

    @Inject
    private MeasureFormatLogger log;

    /**
     * @see MeasureFormatFactory#create(ValueFactory, MeasureFactory)
     */
    @AssistedInject
    MeasureFormat(ValueFormatFactory valueFormatFactory,
            @Assisted ValueFactory valueFactory,
            @Assisted MeasureFactory measureFactory) {
        this(valueFormatFactory, Locale.getDefault(), valueFactory,
                measureFactory);
    }

    /**
     * @see MeasureFormatFactory#create(Locale, ValueFactory, MeasureFactory)
     */
    @AssistedInject
    MeasureFormat(ValueFormatFactory valueFormatFactory,
            @Assisted Locale locale, @Assisted ValueFactory valueFactory,
            @Assisted MeasureFactory measureFactory) {
        this(valueFormatFactory, DecimalFormatSymbols.getInstance(locale),
                valueFactory, measureFactory);
    }

    /**
     * @see MeasureFormatFactory#create(DecimalFormatSymbols, ValueFactory,
     *      MeasureFactory)
     */
    @AssistedInject
    MeasureFormat(ValueFormatFactory valueFormatFactory,
            @Assisted DecimalFormatSymbols symbols,
            @Assisted ValueFactory valueFactory,
            @Assisted MeasureFactory measureFactory) {
        this.valueFormat = valueFormatFactory.create(symbols, valueFactory);
        this.measureFactory = measureFactory;
    }

    /**
     * Set to use the scientific notation for formatting the value.
     *
     * @param scientificNotation
     *            set to {@code true} to use the scientific notation.
     */
    public void setUseScientificNotation(boolean scientificNotation) {
        this.valueFormat.setUseScientificNotation(scientificNotation);
    }

    /**
     * Sets the significant figures for formatting the value.
     *
     * @param sig
     *            the {@link Integer} significant figures.
     */
    public void setSignificant(int sig) {
        this.valueFormat.setSignificant(sig);
    }

    /**
     * Sets the least significant decimal for formatting the value.
     *
     * @param dec
     *            the {@link Integer} least significant decimal.
     */
    public void setDecimal(int dec) {
        this.valueFormat.setDecimal(dec);
    }

    /**
     * Formats the specified value.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * value[(uncertainty)] unit
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123 m}
     * <li>uncertain value: {@code 5.0(0.2) m}
     * </ul>
     *
     * @param obj
     *            the {@link Value}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Measure) {
            formatMeasure((Measure<?>) obj, buff, pos);
        }
        return buff;
    }

    private void formatMeasure(Measure<?> measure, StringBuffer buff,
            FieldPosition pos) {
        valueFormat.format(measure, buff, pos);
        buff.append(' ');
        buff.append(measure.getUnit().toString());
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Parses the specified string to value.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * value[(uncertain)] unit
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123 m}
     * <li>uncertain value: {@code 5.0(0.2) m}
     * </ul>
     *
     * @return the parsed {@link Value}.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
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
        String[] str = StringUtils.split(string, ' ');
        log.checkString(str, string, pos);
        Value value = valueFormat.parse(str[0]);
        Unit<?> unit = (Unit<?>) UnitFormat.getInstance().parseObject(str[1]);
        return measureFactory.create(value, unit);
    }
}
