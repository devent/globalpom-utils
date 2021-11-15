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
package com.anrisoftware.globalpom.math.math;

import static java.math.MathContext.DECIMAL128;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.ceil;
import static org.apache.commons.math3.util.FastMath.floor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormatSymbols;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.FastMath;

/**
 * Various mathematical utilities.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class MathUtils {

    private static final BigDecimal DECIMAL_10 = new BigDecimal("10");

    /**
     * Calculates the value.
     *
     * @param mantissa the {@link BigInteger} mantissa.
     *
     * @param decimal  the decimal place.
     *
     * @return the {@link Double} value.
     *
     * @since 2.4
     */
    public static double calculateValue(BigInteger mantissa, int decimal) {
        return new BigDecimal(mantissa).multiply(DECIMAL_10.pow(decimal, DECIMAL128)).doubleValue();
    }

    /**
     * Rounds the specified value toward zero.
     *
     * @param value the value.
     *
     * @return the rounded value.
     */
    public static double fix(double value) {
        if (value < 0) {
            value = ceil(value);
            if (value == -0.0) {
                value = 0.0;
            }
        } else {
            value = floor(value);
        }
        return value;
    }

    /**
     * Rounds the value to the next mod 3 value. The function breaks for
     * {@link Double#NaN}, {@link Double#POSITIVE_INFINITY} and
     * {@link Double#NEGATIVE_INFINITY}.
     *
     * @param value the value.
     *
     * @return the rounded value.
     *
     * @since 2.1
     */
    public static double roundThree(double value) {
        long n = (long) abs(value);
        if (n % 3 != 0) {
            n++;
            if (n % 3 != 0 && frac(value) == 0) {
                n -= 2;
            }
        }
        n = value < 0 ? -n : n;
        return n;
    }

    /**
     * Returns the values after the decimal of a real value. I.e. for value=5.123 it
     * returns 0.123. The function breaks for {@link Double#NaN},
     * {@link Double#POSITIVE_INFINITY} and {@link Double#NEGATIVE_INFINITY}.
     *
     * @param value the value.
     *
     * @return the frac value.
     *
     * @since 2.1
     */
    public static double frac(double value) {
        long n = (long) value;
        return value - n;
    }

    /**
     * Checks if the value is a fraction.
     *
     * @param value the {@link Double} value.
     *
     * @return {@code true} if the value is a fraction.
     *
     * @since 2.4
     */
    public static boolean isFraction(double value) {
        return value - (long) value != 0;
    }

    /**
     * Returns the number of decimal places from the specified number string.
     *
     * @param str              the {@link String} of the number.
     *
     * @param decimalSeparator the decimal separator character.
     *
     * @param exponentSeparator the {@link String} exponent separator.
     *
     * @return the number of decimal places.
     *
     * @see DecimalFormatSymbols#getDecimalSeparator()
     *
     * @since 2.1
     */
    public static int decimalPlaces(String str, char decimalSeparator, String exponentSeparator) {
        String[] split = StringUtils.split(str, exponentSeparator);
        double exponent = 0.0;
        int decimal = 0;
        if (split.length == 2) {
            exponent = Double.valueOf(split[1]);
        }
        String valuestr = split[0];
        int i = valuestr.indexOf(decimalSeparator);
        if (i != -1) {
            decimal = valuestr.substring(i).length() - 1;
        }
        decimal += -1.0 * exponent;
        return FastMath.abs(decimal);
    }

    /**
     * Returns the number of significant places from the specified number string.
     *
     *
     * <ul>
     * <li>"4" - 1 significant</li>
     * <li>"1.3" - 2 significant</li>
     * <li>"4325.334" - 7 significant</li>
     * <li>"109" - 3 significant</li>
     * <li>"3.005" - 4 significant</li>
     * <li>"40.001" - 5 significant</li>
     * <li>"0.10" - 2 significant</li>
     * <li>"0.0010" - 2 significant</li>
     * <li>"3.20" - 3 significant</li>
     * <li>"320" - 2 significant</li>
     * <li>"14.3000" - 6 significant</li>
     * <li>"400.00" - 5 significant</li>
     * <li>"2.00E7" - 3 significant</li>
     * <li>"1.500E-2" - 4 significant</li>
     * </ul>
     *
     * @param str              the {@link String} of the number.
     *
     * @param decimalSeparator the decimal separator character.
     *
     * @param exponentSeparator the {@link String} exponent separator.
     *
     * @return the number of decimal places.
     *
     * @see DecimalFormatSymbols#getDecimalSeparator()
     *
     * @since 2.1
     */
    public static int sigPlaces(String str, char decimalSeparator, String exponentSeparator) {
        String[] splitEx = split(str, exponentSeparator);
        String[] splitNumber = split(splitEx[0], decimalSeparator);
        int decSig = decSigIndex(splitNumber);
        int numSig = numberSigIndex(splitNumber, decSig);
        return numSig + decSig;
    }

    private static int numberSigIndex(String[] splitNumber, int decSig) {
        int numSig;
        if (decSig == 0) {
            numSig = findNumberSigIndex(splitNumber);
        } else {
            numSig = splitNumber[0].length();
        }
        return numSig;
    }

    private static int decSigIndex(String[] splitNumber) {
        if (splitNumber.length == 1) {
            return 0;
        } else {
            if (splitNumber[0].charAt(0) != '0') {
                return splitNumber[1].length();
            } else {
                return findDecSigIndex(splitNumber);
            }
        }
    }

    private static int findNumberSigIndex(String[] splitNumber) {
        int i = splitNumber[0].length() - 1;
        for (; i >= 0; i--) {
            if (splitNumber[0].charAt(i) == '0') {
                continue;
            } else {
                break;
            }
        }
        return i + 1;
    }

    private static int findDecSigIndex(String[] splitNumber) {
        int i = 0, k = 0;
        for (; i < splitNumber[1].length(); i++) {
            if (splitNumber[1].charAt(i) == '0') {
                continue;
            } else {
                k++;
            }
        }
        return k;
    }
}
