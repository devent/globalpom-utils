/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format.degree;

import static com.anrisoftware.globalpom.math.format.degree.Direction.N;
import static com.anrisoftware.globalpom.math.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.google.inject.Guice.createInjector;
import static java.lang.Double.parseDouble;
import static java.util.regex.Pattern.compile;
import static javax.measure.unit.NonSI.DEGREE_ANGLE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.math3.util.FastMath.abs;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.measure.quantity.Angle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.FastMath;
import org.jscience.physics.amount.Amount;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Parses angle degree sexagesimal.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class DegreeSexagesimalFormat extends Format {

    private static final String SEC_SUB = "\"";

    private static final String MIN_SUB = "'";

    private static final String DEGREE_SUB = "°";

    /**
     * @see #create()
     *
     * @return {@link DegreeSexagesimalFormat}
     */
    public static DegreeSexagesimalFormat createDegreeSexagesimalFormat() {
        return create();
    }

    /**
     * @see #create(int)
     *
     * @return {@link DegreeSexagesimalFormat}
     */
    public static DegreeSexagesimalFormat create() {
        return InjectorInstance.factory.create(4);
    }

    /**
     * Create the sexagesimal degree format.
     *
     * @param decimal the decimal.
     *
     * @return the {@link DegreeSexagesimalFormat}.
     */
    public static DegreeSexagesimalFormat create(int decimal) {
        return InjectorInstance.factory.create(decimal);
    }

    private static class InjectorInstance {
        static final DegreeSexagesimalFormatFactory factory = createInjector(new DegreeFormatModule())
                .getInstance(DegreeSexagesimalFormatFactory.class);
    }

    /**
     * Tests whether the input string is a valid sexagesimal degree format.
     *
     * @param string the {@link String} to test.
     *
     * @return {@code true} if the input string is a valid sexagesimal degree
     *         format.
     */
    public static boolean isValidFormat(String string) {
        return PATTERN.matcher(string).matches();
    }

    private static final Pattern PATTERN = compile(
            String.format("^((\\d+)%s)((\\d+)%s)?((\\d+(\\.\\d*)?)%s)?(\\s[NSEW])?$", DEGREE_SUB, MIN_SUB, SEC_SUB));

    private static final double MIN = 1d / 60d;

    private static final double SEC = 1d / 3600d;

    @Inject
    private DegreeSexagesimalFormatLogger log;

    private final int decimal;

    private final double epsilon;

    /**
     * @see DegreeSexagesimalFormatFactory#create()
     */
    @AssistedInject
    DegreeSexagesimalFormat() {
        this(3);
    }

    /**
     * @see DegreeSexagesimalFormatFactory#create(int)
     */
    @AssistedInject
    DegreeSexagesimalFormat(@Assisted int decimal) {
        this.decimal = decimal;
        this.epsilon = FastMath.pow(10, -(decimal + 1));
    }

    /**
     * Formats the specified degree sexagesimal.
     *
     * @param obj the {@link Amount}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Amount) {
            formatFormat(buff, ((Amount<Angle>) obj).doubleValue(DEGREE_ANGLE));
        }
        if (obj instanceof Number) {
            formatFormat(buff, ((Number) obj).doubleValue());
        }
        return buff;
    }

    private void formatFormat(StringBuffer buff, double value) {
        NumberFormat format = DecimalFormat.getNumberInstance();
        double degree = (long) value;
        double dmin = abs((value - degree) / MIN);
        double min = roundToDecimal((long) dmin, decimal);
        double sec = roundToDecimal((dmin - min) / MIN, decimal);
        buff.append(format.format(degree));
        buff.append(DEGREE_SUB);
        if (min > epsilon) {
            buff.append(format.format(min));
            buff.append(MIN_SUB);
        }
        if (sec > epsilon) {
            buff.append(format.format(sec));
            buff.append(SEC_SUB);
        }
    }

    /**
     * Parses the specified string to degree sexagesimal.
     * <h2>Format</h2>
     * <ul>
     * <li>{@code "D°M'S.s" [NSEW]"}
     * </ul>
     *
     * @return the parsed {@link Amount}.
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        try {
            return parse(source, pos);
        } catch (ParseException e) {
            pos.setErrorIndex(pos.getIndex() + e.getErrorOffset());
            return null;
        }
    }

    /**
     * @see #parse(String, ParsePosition)
     *
     * @param source the {@link String} source.
     *
     * @return the {@link Amount}
     *
     * @throws ParseException if the string is not in the correct format.
     */
    public Amount<Angle> parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Amount<Angle> result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParse(source, pos);
        }
        return result;
    }

    /**
     * @see #parseObject(String)
     *
     * @param source the {@link String} source.
     *
     * @param pos    the index {@link ParsePosition} position from where to start
     *               parsing.
     *
     * @return the {@link Amount}
     *
     * @throws ParseException if the string is not in the correct format.
     */
    public Amount<Angle> parse(String source, ParsePosition pos) throws ParseException {
        try {
            source = source.substring(pos.getIndex());
            Amount<Angle> result = decodeAngle(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(pos.getIndex() + source.length());
            return result;
        } catch (NumberFormatException e) {
            log.errorParseNumber(e, source);
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private Amount<Angle> decodeAngle(String source, ParsePosition pos) throws ParseException {
        Matcher matcher = PATTERN.matcher(source);
        log.checkMatches(matcher.find(), source, pos);
        double degree = parseDoubleSave(matcher.group(2));
        double min = parseDoubleSave(matcher.group(4)) * MIN;
        double sec = parseDoubleSave(matcher.group(6)) * SEC;
        Direction direction = parseDirectionSave(matcher.group(8));
        double value = (degree + min + sec) * direction.getDirection();
        return Amount.valueOf(roundToDecimal(value, decimal), 0, DEGREE_ANGLE);
    }

    private Direction parseDirectionSave(String string) {
        string = StringUtils.trim(string);
        return isEmpty(string) ? N : Direction.valueOf(string.toUpperCase());
    }

    private double parseDoubleSave(String string) {
        return isEmpty(string) ? 0 : parseDouble(string);
    }
}
