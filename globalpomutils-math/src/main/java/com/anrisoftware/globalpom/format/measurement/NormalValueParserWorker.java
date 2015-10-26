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

import static com.anrisoftware.globalpom.format.measurement.ValueFormatLogger._.unparseable;
import static com.anrisoftware.globalpom.math.MathUtils.calculateValue;
import static org.apache.commons.lang3.StringUtils.split;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;

class NormalValueParserWorker implements ValueParserWorker {

    private final Integer decimal;

    private final DecimalFormatSymbols symbols;

    private final Locale locale;

    private final ValueFactory valueFactory;

    private final Pattern valueGroupsPattern;

    public NormalValueParserWorker(DecimalFormatSymbols symbols, Locale locale,
            Integer significant, Integer decimal, ValueFactory valueFactory) {
        this.symbols = symbols;
        this.locale = locale;
        this.decimal = decimal;
        this.valueFactory = valueFactory;
        this.valueGroupsPattern = Pattern.compile(String.format(
                "(.*)(\\(.*\\))?(%s.*)?", symbols.getExponentSeparator()));
    }

    @Override
    public Value stringToValue(String string, Double unc, ParsePosition pos)
            throws ParseException {
        String astring = string.toUpperCase(locale);
        Matcher matcher = valueGroupsPattern.matcher(astring);
        if (!matcher.matches()) {
            throw new ParseException(unparseable.toString(),
                    pos.getErrorIndex());
        }
        String valueStr = matcher.group(1);
        String uncStr = matcher.group(2);
        String exStr = matcher.group(3);
        valueStr = setupDecimal(valueStr, decimal);
        ParseValue parseValue = new ParseValue(valueStr, exStr);
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

    private String setupDecimal(String str, Integer dec) throws ParseException {
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
        Number parse = DecimalFormat.getInstance(locale).parse(str);
        double value = parse.doubleValue();
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

        ParseValue(String value, String exstr) {
            this.valueSplit = new ArrayList<String>(3);
            this.decimalSeparator = symbols.getDecimalSeparator();
            this.exponentSeparator = symbols.getExponentSeparator();
            this.minusSign = symbols.getMinusSign();
            this.negative = value.charAt(0) == minusSign;
            splitValue(value);
            parseExponent(exstr);
        }

        private void parseExponent(String exstr) {
            if (exstr == null) {
                return;
            }
            String[] vsplit = split(exstr, exponentSeparator);
            if (vsplit.length == 1) {
                valueSplit.add(vsplit[0]);
            }
        }

        private void splitValue(String value) {
            System.out.println(value);// TODO println
            String[] dsplit = split(value, decimalSeparator);
            // number
            valueSplit.add(dsplit[0].substring(negative ? 1 : 0));
            // decimal
            if (dsplit.length == 2) {
                valueSplit.add(dsplit[1]);
            } else {
                valueSplit.add(null);
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
