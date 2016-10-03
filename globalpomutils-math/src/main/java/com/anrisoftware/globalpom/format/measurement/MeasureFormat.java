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
package com.anrisoftware.globalpom.format.measurement;

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
        this.valueFormat = valueFormatFactory.create(locale, valueFactory);
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
