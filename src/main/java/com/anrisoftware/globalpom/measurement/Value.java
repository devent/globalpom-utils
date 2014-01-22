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
package com.anrisoftware.globalpom.measurement;

/**
 * Measured value with uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface Value extends Comparable<Object> {

    /**
     * Returns the value.
     * 
     * @return the value.
     */
    double getValue();

    /**
     * Returns the value rounded to the significant figures.
     * 
     * @return the rounded {@link Value}.
     */
    Value getRoundedValue();

    /**
     * Returns the value rounded to the significant figures.
     * 
     * @param sig
     *            the significant figures.
     * 
     * @param dec
     *            the decimal places.
     * 
     * @return the rounded {@link Value}.
     */
    Value roundedValue(int sig, int dec);

    /**
     * Returns the significant figures of the value.
     * 
     * @return the significant figures.
     */
    int getSignificant();

    /**
     * Returns the least significant decimal.
     * 
     * @return the least significant decimal.
     */
    int getDecimal();

    /**
     * Returns the uncertainty of the value.
     * 
     * @return the uncertainty or {@link Double#NaN} if the value is exact.
     */
    double getUncertainty();

    /**
     * Returns whether the value is exact. If the value is exact then
     * {@link #getUncertainty()} will return {@link Double#NaN}.
     * 
     * @return {@code true} if the value is exact.
     */
    boolean isExact();

    /**
     * Returns the lower bound of this uncertain value with a default of three
     * standard deviations.
     * 
     * @return the lower bound value.
     * 
     * @since 1.10
     */
    double getMinValue();

    /**
     * Returns the lower bound of this uncertain value.
     * 
     * @param deviation
     *            the standard deviations of the measured value.
     * 
     * @return the lower bound value.
     * 
     * @since 1.10
     */
    double minValue(double deviation);

    /**
     * Returns the upper bound of this uncertain value with a default of three
     * standard deviations.
     * 
     * @return the upper bound value.
     * 
     * @since 1.10
     */
    double getMaxValue();

    /**
     * Returns the upper bound of this uncertain value.
     * 
     * @param deviation
     *            the standard deviations of the measured value.
     * 
     * @return the upper bound value.
     * 
     * @since 1.10
     */
    double maxValue(double deviation);

    /**
     * Calculates the addition of this value with the addend.
     * 
     * @param addend
     *            the {@link Value} addend.
     * 
     * @return the result {@link Value}.
     */
    Value add(Value addend);

    /**
     * Calculates the addition of this value with the addend.
     * 
     * @param addend
     *            the exact addend.
     * 
     * @return the result {@link Value}.
     */
    Value add(double addend);

    /**
     * Calculates the subtraction of this value with the subtrahend.
     * 
     * @param subtrahend
     *            the {@link Value} subtrahend.
     * 
     * @return the result {@link Value}.
     */
    Value sub(Value subtrahend);

    /**
     * Calculates the subtraction of this value with the subtrahend.
     * 
     * @param subtrahend
     *            the exact subtrahend.
     * 
     * @return the result {@link Value}.
     */
    Value sub(double subtrahend);

    /**
     * Calculates the multiplication of this value with the factor.
     * 
     * @param factor
     *            the {@link Value} factor.
     * 
     * @return the result {@link Value}.
     */
    Value mul(Value factor);

    /**
     * Calculates the multiplication of this value with the factor.
     * 
     * @param factor
     *            the exact factor.
     * 
     * @return the result {@link Value}.
     */
    Value mul(double factor);

    /**
     * Calculates the division of this value with the divisor.
     * 
     * @param divisor
     *            the {@link Value} divisor.
     * 
     * @return the result {@link Value}.
     */
    Value div(Value divisor);

    /**
     * Calculates the division of this value with the divisor.
     * 
     * @param divisor
     *            the exact divisor.
     * 
     * @return the result {@link Value}.
     */
    Value div(double divisor);

    /**
     * Calculates the reciprocal of this value.
     * 
     * @return the result {@link Value}.
     */
    Value reciprocal();

    /**
     * Calculates the natural logarithm of the value.
     * 
     * @return the {@link Value}.
     */
    Value log();

    /**
     * Calculates the Euler's number e raised to the power of this value.
     * 
     * @return the {@link Value}.
     */
    Value exp();

    /**
     * Calculates the absolute value of this value.
     * 
     * @return the {@link Value}.
     */
    Value abs();

    /**
     * Compares this value to the specified value using three standard
     * deviations.
     * 
     * @param v
     *            the {@link Value}.
     * 
     * @return {@code -1} if {@code this<value;} {@code 0} if
     *         {@code this=value;} {@code 1} if {@code this>value.}
     */
    int compare(Value v);

    /**
     * Compares this value to the specified value.
     * 
     * @param v
     *            the {@link Value}.
     * 
     * @param dev
     *            the standard deviations the values can differ from each other
     *            to be unequal.
     * 
     * @return {@code -1} if {@code this<value;} {@code 0} if
     *         {@code this=value;} {@code 1} if {@code this>value.}
     */
    int compare(Value v, double dev);

    /**
     * Compares this value to the specified exact value using three standard
     * deviations.
     * 
     * @param v
     *            the exact {@link Number}.
     * 
     * @return {@code -1} if {@code this<value;} {@code 0} if
     *         {@code this=value;} {@code 1} if {@code this>value.}
     */
    int compare(Number v);

    /**
     * Compares this value to the specified exact value.
     * 
     * @param v
     *            the exact {@link Number}.
     * 
     * @param dev
     *            the standard deviations the values can differ from each other
     *            to be unequal.
     * 
     * @return {@code -1} if {@code this<value;} {@code 0} if
     *         {@code this=value;} {@code 1} if {@code this>value.}
     */
    int compare(Number v, double dev);

    /**
     * Compares this value and the specified value for equality. Two values are
     * equal if they do not differ from each other by more then three standard
     * deviations.
     */
    @Override
    boolean equals(Object obj);

    /**
     * Compares this value and the specified value for equality. Two values are
     * equal if they do not differ from each other by more then the specified
     * standard deviations.
     * 
     * @param dev
     *            the standard deviations the values can differ from each other
     *            to be unequal.
     * 
     * @see #equals(Object)
     */
    boolean equals(Object obj, double dev);

}
