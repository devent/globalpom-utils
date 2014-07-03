/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.math.MathUtils.decimalPlaces;
import static com.anrisoftware.globalpom.math.MathUtils.sigPlaces;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.anrisoftware.globalpom.measurement.ExactValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;
import com.anrisoftware.globalpom.measurement.ValueToString;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses a {@link Value} value.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ValueFormat extends Format {

    private final String VALUE_GROUP = "(.*?)";

    private final String PARANTHESIS_GROUP = "\\((.*?)\\)";

    private final String PERCENT_GROUP = "\\((.*?)%\\)";

    private final String SIG_GROUP = ";(\\d+);(\\d+);";

    private final Pattern VALUE_PATTERN = Pattern.compile(String.format(
            "^%s(?:%s|%s)?(?:%s)?$", VALUE_GROUP, PARANTHESIS_GROUP,
            PERCENT_GROUP, SIG_GROUP));

    private final NumberFormat valueFormat;

    private final ValueFactory valueFactory;

    private final ExactValueFactory exactValueFactory;

    private final NumberFormat uncFormat;

    @Inject
    private ValueToString valueToString;

    @Inject
    private ValueFormatLogger log;

    public char decimalSeparator;

    public String exponentSeparator;

    /**
     * @see ValueFormatFactory#create(ValueFactory, ExactValueFactory)
     */
    @AssistedInject
    ValueFormat(@Assisted ValueFactory valueFactory,
            @Assisted ExactValueFactory exactValueFactory) {
        this(valueFactory, exactValueFactory, new DecimalFormat("#.#########"));
    }

    /**
     * @see ValueFormatFactory#create(ValueFactory, ExactValueFactory,
     *      NumberFormat)
     */
    @AssistedInject
    ValueFormat(@Assisted ValueFactory valueFactory,
            @Assisted ExactValueFactory exactValueFactory,
            @Assisted NumberFormat format) {
        this(valueFactory, exactValueFactory, format, format);
    }

    /**
     * @see ValueFormatFactory#create(ValueFactory, ExactValueFactory,
     *      NumberFormat, NumberFormat)
     */
    @AssistedInject
    ValueFormat(@Assisted ValueFactory valueFactory,
            @Assisted ExactValueFactory exactValueFactory,
            @Assisted("format") NumberFormat format,
            @Assisted("uncFormat") NumberFormat uncFormat) {
        this.valueFactory = valueFactory;
        this.exactValueFactory = exactValueFactory;
        this.valueFormat = format;
        this.uncFormat = uncFormat;
        if (format instanceof DecimalFormat) {
            DecimalFormat dec = (DecimalFormat) format;
            DecimalFormatSymbols symbols = dec.getDecimalFormatSymbols();
            this.decimalSeparator = symbols.getDecimalSeparator();
            this.exponentSeparator = symbols.getExponentSeparator();
        } else {
            DecimalFormat dec = new DecimalFormat();
            DecimalFormatSymbols symbols = dec.getDecimalFormatSymbols();
            this.decimalSeparator = symbols.getDecimalSeparator();
            this.exponentSeparator = symbols.getExponentSeparator();
        }
    }

    /**
     * Formats the specified point.
     * <p>
     * The format follows the pattern:
     * 
     * <pre>
     * &lt;value&gt;[(&lt;uncertainty&gt;);&lt;significant&gt;;&lt;decimal&gt;;]
     * </pre>
     * 
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123}
     * <li>uncertain value: {@code 5.0(0.2);1;1;}
     * </ul>
     * 
     * @param obj
     *            the {@link Value}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Value) {
            valueToString.format(buff, (Value) obj, valueFormat, uncFormat);
        }
        return buff;
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
     * &lt;value&gt;;&lt;uncertainty%&gt;)[;&lt;significant&gt;;&lt;decimal&gt;;]
     * &lt;value&gt;(&lt;uncertainty&gt;)[;&lt;significant&gt;;&lt;decimal&gt;;]
     * </pre>
     * 
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123}
     * <li>uncertain value: {@code 5.0(0.2);1;1;}
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
            Value address = parseValue(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return address;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private Value parseValue(String string, ParsePosition pos)
            throws ParseException {
        Matcher matcher = VALUE_PATTERN.matcher(string);
        if (matcher.matches()) {
            return parseValue(matcher);
        } else {
            throw log.errorParseValue(string, pos);
        }
    }

    private class ParseValue {

        Double value = null;

        Double unc = null;

        Integer sig = null;

        Integer dec = null;

        String valueStr = null;

        String uncStr = null;

        public void parseValue(Matcher matcher) throws ParseException {
            for (int i = 1; i < matcher.groupCount() + 1; i++) {
                Number number = parseGroup(i, matcher);
                switch (i) {
                case 1:
                    value = number.doubleValue();
                    valueStr = matcher.group(i);
                    break;
                case 2:
                    if (number != null) {
                        unc = number.doubleValue();
                        uncStr = matcher.group(i);
                    }
                    break;
                case 4:
                    if (number == null) {
                        return;
                    }
                    sig = number.intValue();
                    break;
                case 5:
                    if (number == null) {
                        return;
                    }
                    dec = number.intValue();
                    break;
                }
            }
        }

        private Number parseGroup(int i, Matcher matcher) throws ParseException {
            if (matcher.group(i) == null) {
                return null;
            } else {
                return valueFormat.parse(matcher.group(i));
            }
        }

    }

    private Value parseValue(Matcher matcher) throws ParseException {
        ParseValue parseValue = new ParseValue();
        parseValue.parseValue(matcher);
        if (parseValue.unc == null) {
            return createExactValue(parseValue.value);
        } else {
            return createValue(parseValue);
        }
    }

    private Value createExactValue(double value) throws ParseException {
        return exactValueFactory.create(value);
    }

    private Value createValue(ParseValue parseValue) {
        int sig = parseValue.sig != null ? parseValue.sig : sigPlaces(
                parseValue.uncStr, decimalSeparator, exponentSeparator);
        int dec = parseValue.dec != null ? parseValue.dec : decimalPlaces(
                parseValue.valueStr, decimalSeparator, exponentSeparator);
        return valueFactory.create(parseValue.value, sig, parseValue.unc, dec);
    }
}
