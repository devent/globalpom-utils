/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.anrisoftware.globalpom.measurement.ValueFactory;

public class ValueParserWorkerFactory {

    /**
     * {@code <(1)minus?>\d+}
     */
    private static String NUMBER_PATTERN = "%1$s?\\d+";

    /**
     * {@code <(1)minus?>\d+<(2)decimal?>\d*}
     */
    private static String DEC_PATTERN = "%1$s?\\d+%2$s?\\d*";

    /**
     * {@code <(2)exp><(1)minus?>\d+}
     */
    private static String EXP_PATTERN = "%2$s%1$s?\\d+";

    /**
     * {@code <(1)number><(2)exp?>}
     */
    private static String SIMPLE_PATTERN = "^(%1$s)(%2$s)?$";

    /**
     * {@code <(1)number><(3)exp?>(<(2)decnumber><(3)exp?>)}
     */
    private static String UNCERTAIN_PATTERN = "^(%1$s)(%3$s)?(\\(%2$s(?:%3$s)?\\))$";

    /**
     * {@code <(1)decnumber><(2)exp?>(<(1)decnumber><(2)exp?>)}
     */
    private static String DEC_UNCERTAIN_PATTERN = "^(%1$s)(%2$s)?(\\(%1$s(?:%2$s)?\\))$";

    /**
     * {@code <(1)decnumber><(2)exp?>(<(1)decnumber><(3)percent>)}
     */
    private static String UNCERTAIN_PERCENT_PATTERN = "^(%1$s)(%2$s)?(\\(%1$s%3$s\\))$";

    /**
     * <p>
     * Pattern: {@code "<(2)decnumber>(<(1)number>)<(3)exp?>"}
     * </p>
     * <p>
     * Examples:
     * <ul>
     * <li>{@code 12.12(5)E5}
     * </ul>
     * </p>
     */
    private static String UNCERTAIN_SHORT_PATTERN = "^(%2$s)(\\(%1$s\\))(%3$s)?$";

    private final Pattern simplePattern;

    private final DecimalFormatSymbols symbols;

    private final Locale locale;

    private final Integer significant;

    private final Integer decimal;

    private final ValueFactory valueFactory;

    private final Pattern uncertainPattern;

    private final Pattern uncertainPercentPattern;

    private final Pattern uncertainShortPattern;

    private final Pattern decUncertainPattern;

    public ValueParserWorkerFactory(DecimalFormatSymbols symbols,
            Locale locale, Integer significant, Integer decimal,
            ValueFactory valueFactory) {
        this.symbols = symbols;
        this.locale = locale;
        this.significant = significant;
        this.decimal = decimal;
        this.valueFactory = valueFactory;
        char min = symbols.getMinusSign();
        String ex = symbols.getExponentSeparator();
        char dec = symbols.getDecimalSeparator();
        char percent = symbols.getPercent();
        String decPattern = format(DEC_PATTERN, min, dec);
        String numberPattern = format(NUMBER_PATTERN, min, dec);
        String expPattern = format(EXP_PATTERN, min, ex);
        this.simplePattern = compile(
                format(SIMPLE_PATTERN, decPattern, expPattern),
                Pattern.MULTILINE);
        this.uncertainPattern = compile(
                format(UNCERTAIN_PATTERN, numberPattern, decPattern, expPattern),
                Pattern.MULTILINE);
        this.uncertainPercentPattern = compile(
                format(UNCERTAIN_PERCENT_PATTERN, decPattern, expPattern,
                        percent), Pattern.MULTILINE);
        this.uncertainShortPattern = compile(
                format(UNCERTAIN_SHORT_PATTERN, numberPattern, decPattern,
                        expPattern), Pattern.MULTILINE);
        this.decUncertainPattern = compile(
                format(DEC_UNCERTAIN_PATTERN, decPattern, expPattern),
                Pattern.MULTILINE);
    }

    public ValueParserWorker getValueParserWorker(String source,
            ParsePosition pos) throws ParseException {
        source = source.toUpperCase(locale);
        Matcher matcher = simplePattern.matcher(source);
        String valueStr;
        String exponentStr;
        String uncString;
        if (matcher.matches()) {
            valueStr = matcher.group(1);
            exponentStr = matcher.group(2);
            uncString = null;
            return new SimpleNumberValueParserWorker(symbols, locale,
                    significant, decimal, valueFactory, valueStr, exponentStr,
                    uncString);
        }
        matcher = uncertainPattern.matcher(source);
        if (matcher.matches()) {
            valueStr = matcher.group(1);
            exponentStr = matcher.group(2);
            uncString = matcher.group(3);
            return new SimpleNumberValueParserWorker(symbols, locale,
                    significant, decimal, valueFactory, valueStr, exponentStr,
                    uncString);
        }
        matcher = uncertainPercentPattern.matcher(source);
        if (matcher.matches()) {
            valueStr = matcher.group(1);
            exponentStr = matcher.group(2);
            uncString = matcher.group(3);
            return new SimpleNumberValueParserWorker(symbols, locale,
                    significant, decimal, valueFactory, valueStr, exponentStr,
                    uncString);
        }
        matcher = uncertainShortPattern.matcher(source);
        if (matcher.matches()) {
            valueStr = matcher.group(1);
            exponentStr = matcher.group(3);
            uncString = matcher.group(2);
            return new UncertainShortNumberValueParserWorker(symbols, locale,
                    significant, decimal, valueFactory, valueStr, exponentStr,
                    uncString);
        }
        matcher = decUncertainPattern.matcher(source);
        if (matcher.matches()) {
            valueStr = matcher.group(1);
            exponentStr = matcher.group(2);
            uncString = matcher.group(3);
            return new SimpleNumberValueParserWorker(symbols, locale,
                    significant, decimal, valueFactory, valueStr, exponentStr,
                    uncString);
        }
        throw new ParseException(unparseable.toString(), pos.getErrorIndex());
    }
}
