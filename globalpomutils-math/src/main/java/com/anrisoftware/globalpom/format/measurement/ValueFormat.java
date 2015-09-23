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

import static com.anrisoftware.globalpom.math.MathUtils.calculateValue;
import static com.anrisoftware.globalpom.math.MathUtils.isFraction;
import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToSignificant;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.math3.util.FastMath.abs;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.FastMath;

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

    @Inject
    private ValueFormatLogger log;

    private boolean scientificNotation;

    private Integer sig;

    private Integer dec;

    private final DecimalFormatSymbols symbols;

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
        this(valueFactory, DecimalFormatSymbols.getInstance(locale));
    }

    /**
     * @see ValueFormatFactory#create(DecimalFormatSymbols, ValueFactory)
     */
    @AssistedInject
    ValueFormat(@Assisted ValueFactory valueFactory,
            @Assisted DecimalFormatSymbols symbols) {
        this.sig = null;
        this.dec = null;
        this.valueFactory = valueFactory;
        this.symbols = symbols;
    }

    /**
     * Set to use the scientific notation for formatting the value.
     *
     * @param scientificNotation
     *            set to {@code true} to use the scientific notation.
     */
    public void setUseScientificNotation(boolean scientificNotation) {
        this.scientificNotation = scientificNotation;
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
            double v = value.getValue();
            double unc = value.getRoundedUncertainty();
            int order = value.getOrder();
            int sig = this.sig == null ? value.getSignificant() : this.sig;
            int dec = value.getDecimal();
            if (this.dec != null) {
                dec = FastMath.max(-this.dec, dec);
            }
            if (scientificNotation) {
                buff.append(formatScientificValue(v, order, sig, dec));
                if (!value.isExact()) {
                    buff.append('(');
                    buff.append(formatScienceUnc(v, unc, order, sig, dec));
                    buff.append(')');
                }
            } else {
                buff.append(formatNumber(v, order, sig, dec));
                if (!value.isExact()) {
                    buff.append('(');
                    buff.append(formatUncertainty(unc, order, sig, dec));
                    buff.append(')');
                }
            }
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
        StringBuilder pattern = new StringBuilder("0");
        dec = abs(dec);
        double rvalue = roundValue(value, sig, dec);
        if (dec > 0) {
            pattern.append('.');
            for (int i = order; i < 0; i++) {
                pattern.append('0');
            }
            for (int i = 0; i < sig; i++) {
                pattern.append('#');
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        return format.format(rvalue);
    }

    private String formatUncertainty(double unc, int order, int sig, int dec) {
        StringBuilder pattern = new StringBuilder("0");
        if (dec < 0) {
            pattern.append('.');
            for (int i = dec; i < 0; i++) {
                pattern.append('0');
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        return format.format(unc);
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
        StringBuilder pattern = new StringBuilder("0");
        dec = abs(dec);
        double avalue = abs(value);
        double rvalue = roundValue(value, sig, dec);
        if (dec != 0 && avalue < 1 || avalue > 9) {
            pattern.append('.');
            int s = avalue < 0 ? 0 : 1;
            for (int i = s; i < sig; i++) {
                pattern.append('0');
            }
            pattern.append("E0");
        } else if (isFraction(avalue)) {
            pattern.append('.');
            for (int i = 1; i < sig; i++) {
                pattern.append('#');
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        return format.format(rvalue);
    }

    private String formatScienceUnc(double value, double unc, int order,
            int sig, int dec) {
        if (unc == 0) {
            return "0";
        }
        double aunc = abs(unc);
        int oorder = order - 1;
        double vunc = unc / FastMath.pow(10, oorder);
        StringBuilder pattern = new StringBuilder("0");
        if (dec != 0) {
            pattern.append('.');
            int s = aunc < 0 ? 0 : 1;
            for (int i = s; i < sig; i++) {
                pattern.append('0');
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        String str = format.format(vunc);
        if (oorder != 0) {
            str = String.format("%sE%d", str, oorder);
        }
        return str;
    }

    private double roundValue(double value, int sig, int dec) {
        value = roundToSignificant(value, sig);
        value = roundToDecimal(value, dec);
        return value;
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
            Value address = parseValue(source, null, pos);
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
            Value value = parseValue(source, unc, pos);
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

    private static final Pattern VALUE_GROUP_PATTERN = Pattern
            .compile("(.*?)(\\(.*?\\))?");

    private Value parseValue(String string, Double unc, ParsePosition pos)
            throws ParseException {
        Matcher matcher = VALUE_GROUP_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw log.errorParseValue(string, pos);
        }
        String valueStr = matcher.group(1);
        String uncStr = matcher.group(2);
        valueStr = setupDecimal(valueStr, dec);
        String value = valueStr;
        ParseValue parseValue = new ParseValue(value);
        int order = parseValue.parseOrder();
        String mantissa = parseValue.parseMantissa();
        int sig = mantissa.length();
        int dec = order - sig;
        BigInteger man = new BigInteger(mantissa);
        man = parseValue.isNegative() ? man.negate() : man;
        if (unc == null) {
            unc = Double.NaN;
            if (uncStr != null) {
                unc = parseUncertainty(uncStr, calculateValue(man, dec));
            }
        }
        return valueFactory.create(man, order, sig, dec, unc);
    }

    private String setupDecimal(String str, Integer dec) {
        if (dec == null) {
            return str;
        }
        StringBuilder pattern = new StringBuilder("0");
        if (dec > 0) {
            pattern.append('.');
            for (int i = 0; i < dec; i++) {
                pattern.append('0');
            }
        }
        double value = Double.valueOf(str);
        return new DecimalFormat(pattern.toString(), symbols).format(value);
    }

    private double parseUncertainty(String str, double value) {
        char percent = symbols.getPercent();
        str = StringUtils.substringBetween(str, "(", ")");
        if (StringUtils.contains(str, percent)) {
            int idx = str.indexOf(percent);
            str = str.substring(0, idx);
            return Double.valueOf(str) / 100 * value;
        } else {
            return Double.valueOf(str);
        }
    }

    class ParseValue {

        private final List<String> valueSplit;

        private final char decimalSeparator;

        private final String exponentSeparator;

        private final boolean negative;

        private final char minusSign;

        ParseValue(String value) {
            this.valueSplit = new ArrayList<String>(3);
            this.decimalSeparator = symbols.getDecimalSeparator();
            this.exponentSeparator = symbols.getExponentSeparator();
            this.minusSign = symbols.getMinusSign();
            this.negative = value.charAt(0) == minusSign;
            splitValue(value);
        }

        private void splitValue(String value) {
            String[] vsplit = split(value, exponentSeparator);
            String[] dsplit = split(vsplit[0], decimalSeparator);
            // number
            valueSplit.add(dsplit[0].substring(negative ? 1 : 0));
            // decimal
            if (dsplit.length == 2) {
                valueSplit.add(dsplit[1]);
            } else {
                valueSplit.add(null);
            }
            // exponent
            if (vsplit.length == 2) {
                valueSplit.add(vsplit[1]);
            }
        }

        public boolean isNegative() {
            return negative;
        }

        public int parseOrder() {
            int order = 0;
            // exponent order
            if (valueSplit.size() > 2) {
                String exp = valueSplit.get(2);
                order = Integer.valueOf(exp);
            }
            // number order
            if (valueSplit.size() > 0) {
                String number = valueSplit.get(0);
                boolean firstNotNull = false;
                for (int i = 0; i < number.length(); i++) {
                    char c = number.charAt(i);
                    switch (c) {
                    case '0':
                        if (firstNotNull) {
                            order++;
                        }
                        break;
                    default:
                        firstNotNull = true;
                        order++;
                        break;
                    }
                }
            }
            // decimal order
            if (order < 1 && valueSplit.size() == 2
                    && valueSplit.get(1) != null) {
                String decimal = valueSplit.get(1);
                boolean onlyNull = true;
                int oorder = 0;
                for (int i = 0; i < decimal.length(); i++) {
                    char c = decimal.charAt(i);
                    switch (c) {
                    case '0':
                        oorder--;
                        break;
                    default:
                        onlyNull = false;
                        i = decimal.length();
                        break;
                    }
                }
                if (!onlyNull) {
                    order += oorder;
                }
            }
            return order;
        }

        public String parseMantissa() {
            StringBuilder buff = new StringBuilder();
            appendNumber(buff);
            if (valueSplit.size() > 1) {
                appendDecimal(buff.length() == 0, buff);
            }
            if (buff.length() == 0) {
                buff.append('0');
            }
            return buff.toString();
        }

        private StringBuilder appendNumber(StringBuilder buff) {
            String number = valueSplit.get(0);
            if (number.length() == 1 && number.charAt(0) == '0') {
                return buff;
            }
            StringBuilder b = new StringBuilder();
            boolean firstNotNull = valueSplit.size() > 1
                    && valueSplit.get(1) != null;
            for (int i = number.length() - 1; i >= 0; i--) {
                char c = number.charAt(i);
                switch (c) {
                case '0':
                    if (firstNotNull) {
                        b.append(c);
                    }
                    break;
                default:
                    firstNotNull = true;
                    b.append(c);
                    break;
                }
            }
            buff.append(b.reverse());
            return buff;
        }

        private StringBuilder appendDecimal(boolean isNull, StringBuilder buff) {
            String decimal = valueSplit.get(1);
            if (decimal == null) {
                return buff;
            }
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < decimal.length(); i++) {
                char c = decimal.charAt(i);
                switch (c) {
                case '0':
                    if (!isNull) {
                        b.append(c);
                    }
                    break;
                default:
                    isNull = false;
                    b.append(c);
                    break;
                }
            }
            buff.append(b);
            return buff;
        }

    }

}
