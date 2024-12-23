/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.measurement;

import java.math.BigInteger;

/**
 * Measured value with uncertainty.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
public interface Value extends Comparable<Value> {

    /**
     * Returns the significant digits of the value.
     *
     * @return the mantissa.
     */
    BigInteger getMantissa();

    /**
     * Returns the order of the value.
     *
     * @return the order.
     */
    int getOrder();

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
     * @return the {@link Value} uncertainty or {@code Double#NaN}.
     */
    double getUncertainty();

    /**
     * Returns the rounded to the least decimal uncertainty of the value.
     *
     * @return the {@link Value} uncertainty or {@code Double#NaN}.
     */
    double getRoundedUncertainty();

    /**
     * Returns whether the value is exact.
     *
     * @return {@code true} if the value is exact.
     */
    boolean isExact();

    /**
     * Returns the value.
     *
     * @return the value.
     */
    double getValue();

    /**
     * Returns the value rounded to the significant figures.
     *
     * @return the rounded {@link Double}.
     */
    double getRoundedValue();

    /**
     * Returns the value rounded to the significant figures.
     *
     * @param sig the significant figures.
     *
     * @param dec the least significant decimal place.
     *
     * @return the rounded {@link Double}.
     */
    double roundedValue(int sig, int dec);

    /**
     * Returns the lower bound of this uncertain value with a default of three
     * standard deviations.
     *
     * @return the lower bound {@link Value} value.
     */
    Value getMinValue();

    /**
     * Returns the lower bound of this uncertain value.
     *
     * @param deviation the standard deviations of the measured value.
     *
     * @return the lower bound {@link Value} value.
     */
    Value minValue(double deviation);

    /**
     * Returns the upper bound of this uncertain value with a default of three
     * standard deviations.
     *
     * @return the upper bound {@link Value} value.
     */
    Value getMaxValue();

    /**
     * Returns the upper bound of this uncertain value.
     *
     * @param deviation the standard deviations of the measured value.
     *
     * @return the upper bound {@link Value} value.
     */
    Value maxValue(double deviation);

    /**
     * Returns the value with the specified uncertainty.
     *
     * @param mantissa the significant digits of the value.
     *
     * @param order    the order of the value.
     *
     * @param sig      the significant figures of the value.
     *
     * @param dec      the least significant decimal.
     *
     * @param unc      the uncertainty {@link Double} or {@link Double#NaN}.
     *
     * @return the {@link Value}.
     */
    Value valueOf(BigInteger mantissa, int order, int sig, int dec, double unc);

    /**
     * Returns the value with the specified uncertainty.
     *
     * @param value the {@link Double} value.
     *
     * @param sig   the significant figures of the value.
     *
     * @param dec   the least significant decimal.
     *
     * @param unc   the uncertainty {@link Double} or {@link Double#NaN}.
     *
     * @return the {@link Value}.
     */
    Value valueOf(double value, int sig, int dec, double unc);

    /**
     * Returns the value with the specified uncertainty.
     *
     * @param value the {@link Double} value.
     *
     * @param dec   the least significant decimal.
     *
     * @param unc   the uncertainty {@link Double} or {@link Double#NaN}.
     *
     * @return the {@link Value}.
     */
    Value valueOf(double value, int dec, double unc);

    /**
     * Calculates the addition of this value with the addend.
     *
     * @param addend the {@link Value} addend.
     *
     * @return the result {@link Value}.
     */
    Value add(Value addend);

    /**
     * Calculates the addition of this value with the addend.
     *
     * @param addend the {@link Double} addend.
     *
     * @return the result {@link Value}.
     */
    Value add(double addend);

    /**
     * Calculates the addition of this value with the addend.
     *
     * @param addend the {@link Value} addend.
     *
     * @return the result {@link Value}.
     */
    Value plus(Value addend);

    /**
     * Calculates the addition of this value with the addend.
     *
     * @param addend the {@link Double} addend.
     *
     * @return the result {@link Value}.
     */
    Value plus(double addend);

    /**
     * Calculates the subtraction of this value with the subtrahend.
     *
     * @param subtrahend the {@link Value} subtrahend.
     *
     * @return the result {@link Value}.
     */
    Value sub(Value subtrahend);

    /**
     * Calculates the subtraction of this value with the subtrahend.
     *
     * @param subtrahend the {@link Double} subtrahend.
     *
     * @return the result {@link Value}.
     */
    Value sub(double subtrahend);

    /**
     * Calculates the subtraction of this value with the subtrahend.
     *
     * @param subtrahend the {@link Value} subtrahend.
     *
     * @return the result {@link Value}.
     */
    Value minus(Value subtrahend);

    /**
     * Calculates the subtraction of this value with the subtrahend.
     *
     * @param subtrahend the {@link Double} subtrahend.
     *
     * @return the result {@link Value}.
     */
    Value minus(double subtrahend);

    /**
     * Calculates the multiplication of this value with the factor.
     *
     * @param factor the {@link Value} factor.
     *
     * @return the result {@link Value}.
     */
    Value mul(Value factor);

    /**
     * Calculates the multiplication of this value with the factor.
     *
     * @param factor the {@link Double} factor.
     *
     * @return the result {@link Value}.
     */
    Value mul(double factor);

    /**
     * Calculates the multiplication of this value with the factor.
     *
     * @param factor the {@link Value} factor.
     *
     * @return the result {@link Value}.
     */
    Value multiply(Value factor);

    /**
     * Calculates the multiplication of this value with the factor.
     *
     * @param factor the {@link Double} factor.
     *
     * @return the result {@link Value}.
     */
    Value multiply(double factor);

    /**
     * Calculates the division of this value with the divisor.
     *
     * @param divisor the {@link Value} divisor.
     *
     * @return the result {@link Value}.
     */
    Value div(Value divisor);

    /**
     * Calculates the division of this value with the divisor.
     *
     * @param divisor the {@link Double} divisor.
     *
     * @return the result {@link Value}.
     */
    Value div(double divisor);

    /**
     * Calculates the division of this value with the numerator.
     *
     * @param numerator the {@link Value} numerator.
     *
     * @return the result {@link Value}.
     */
    Value divNum(Value numerator);

    /**
     * Calculates the division of this value with the numerator.
     *
     * @param numerator the {@link Double} numerator.
     *
     * @return the result {@link Value}.
     */
    Value divNum(double numerator);

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
     * Calculates the square this value.
     *
     * @return the square {@link Value}.
     */
    Value square();

    /**
     * Calculates the cube this value.
     *
     * @return the cube {@link Value}.
     */
    Value cube();

    /**
     * Compares this value to the specified value using the absolute value.
     *
     * @param v the {@link Value}.
     *
     * @return {@code -1} if {@code this<value;} {@code 0} if {@code this=value;}
     *         {@code 1} if {@code this>value.}
     */
    @Override
    int compareTo(Value v);

    /**
     * Compares this value and the specified value for equality by their absolute
     * value.
     */
    @Override
    boolean equals(Object obj);

    /**
     * Compares this value and the specified value for consistency.
     *
     * @param rhs the right hand side {@link Value}.
     *
     * @return <code>true</code> if it is consistent.
     */
    boolean isConsistent(Value rhs);

    /**
     * Returns the hash code from the value.
     */
    @Override
    int hashCode();

}
