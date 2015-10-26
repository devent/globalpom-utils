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

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToSignificant;
import static org.apache.commons.lang3.Validate.isTrue;

import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;

import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses a {@link Value} value.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
@SuppressWarnings("serial")
public class ValueFormat extends Format {

    private final ValueFactory valueFactory;

    private final DecimalFormatSymbols symbols;

    private final Locale locale;

    @Inject
    private ValueFormatLogger log;

    private ValueFormatWorker formatWorker;

    private ValueParserWorkerFactory parserWorkerFactory;

    private boolean scientificNotation;

    private Integer sig;

    private Integer dec;

    /**
     * @see ValueFormatFactory#create(ValueFactory)
     */
    @AssistedInject
    ValueFormat(@Assisted ValueFactory valueFactory) {
        this(valueFactory, Locale.getDefault());
    }

    /**
     * @see ValueFormatFactory#create(Locale, ValueFactory)
     */
    @AssistedInject
    ValueFormat(@Assisted ValueFactory valueFactory, @Assisted Locale locale) {
        this.sig = null;
        this.dec = null;
        this.scientificNotation = false;
        this.valueFactory = valueFactory;
        this.symbols = DecimalFormatSymbols.getInstance(locale);
        this.locale = locale;
        this.formatWorker = createValueFormatWorker();
        this.parserWorkerFactory = createValueParserWorkerFactory();
    }

    /**
     * Set to use the scientific notation for formatting the value.
     *
     * @param scientificNotation
     *            set to {@code true} to use the scientific notation.
     */
    public void setUseScientificNotation(boolean scientificNotation) {
        this.scientificNotation = scientificNotation;
        this.formatWorker = createValueFormatWorker();
    }

    /**
     * Sets the significant figures for formatting the value.
     *
     * @param sig
     *            the {@link Integer} significant figures.
     */
    public void setSignificant(int sig) {
        isTrue(sig > 0, "%d>0", sig);
        this.sig = sig;
        this.formatWorker = createValueFormatWorker();
        this.parserWorkerFactory = createValueParserWorkerFactory();
    }

    /**
     * Sets the least significant decimal for formatting the value.
     *
     * @param dec
     *            the {@link Integer} least significant decimal.
     */
    public void setDecimal(int dec) {
        isTrue(dec > 0, "%d>0", dec);
        this.dec = dec;
        this.formatWorker = createValueFormatWorker();
        this.parserWorkerFactory = createValueParserWorkerFactory();
    }

    /**
     * Formats the specified value.
     * <p>
     * The format follows the pattern:
     *
     * <pre>
     * value[(uncertainty)]
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123}
     * <li>uncertain value: {@code 5.0(0.2)}
     * </ul>
     *
     * @param obj
     *            the {@link Value}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Value) {
            Value value = (Value) obj;
            formatWorker.valueToString(value, buff);
        }
        return buff;
    }

    /**
     * Formats the value according to the significant figure and least
     * significant decimal.
     *
     * @param value
     *            the {@link Double} value.
     *
     * @param order
     *            the {@link Integer} order.
     *
     * @param sig
     *            the {@link Integer} significant figure.
     *
     * @param dec
     *            the {@link Integer} least significant decimal,
     *
     * @return the formatted {@link String}.
     */
    public String formatNumber(double value, int order, int sig, int dec) {
        ValueFormatWorker w = new NormalValueFormatWorker(symbols, sig, dec);
        return w.formatNumber(value, order, sig, dec);
    }

    /**
     * Formats the value according to the significant figure and least
     * significant decimal in a scientific format.
     *
     * @param value
     *            the {@link Double} value.
     *
     * @param order
     *            the {@link Integer} order.
     *
     * @param sig
     *            the {@link Integer} significant figure.
     *
     * @param dec
     *            the {@link Integer} least significant decimal,
     *
     * @return the formatted {@link String}.
     */
    public String formatScientificValue(double value, int order, int sig,
            int dec) {
        ValueFormatWorker w = new ScientificValueFormatWorker(symbols, sig, dec);
        return w.formatNumber(value, order, sig, dec);
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
     * value[(uncertain)]
     * </pre>
     *
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123}
     * <li>uncertain value: {@code 5.0(0.2)}
     * <li>uncertain value: {@code 5.0(2)}
     * <li>uncertain value: {@code 5.0(2)e-12}
     * <li>uncertain value: {@code 5.0(2)E-12}
     * </ul>
     *
     * @return the parsed {@link Value}.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     */
    public Value parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Value result = parse(source, pos);
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
    public Value parse(String source, ParsePosition pos) {
        source = source.substring(pos.getIndex());
        try {
            Value address = parserWorkerFactory.getValueParserWorker(source,
                    pos).stringToValue(source, null, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return address;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        } catch (NumberFormatException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    /**
     * Parses the value with the specified uncertainty.
     *
     * @param source
     *            the {@link String} source.
     *
     * @param unc
     *            the {@link Double} uncertainty or {@link Double#NaN}.
     *
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
     *
     * @return the {@link Value}.
     */
    public Value parse(String source, double unc, ParsePosition pos) {
        try {
            Value value = parserWorkerFactory.getValueParserWorker(source, pos)
                    .stringToValue(source, unc, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return value;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        } catch (NumberFormatException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    /**
     * Parses the value with the specified uncertainty.
     *
     * @param source
     *            the {@link String} source.
     *
     * @param unc
     *            the {@link Double} uncertainty.
     *
     * @return the {@link Value}.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     */
    public Value parse(String source, double unc) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Value result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParseValue(source, pos);
        }
        return result;
    }

    static double roundValue(double value, int sig, int dec) {
        value = roundToSignificant(value, sig);
        value = roundToDecimal(value, dec);
        return value;
    }

    private ValueFormatWorker createValueFormatWorker() {
        if (scientificNotation) {
            return new ScientificValueFormatWorker(symbols, sig, dec);
        } else {
            return new NormalValueFormatWorker(symbols, sig, dec);
        }
    }

    private ValueParserWorkerFactory createValueParserWorkerFactory() {
        return new ValueParserWorkerFactory(symbols, locale, sig, dec,
                valueFactory);
    }

}
