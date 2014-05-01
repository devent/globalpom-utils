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
package com.anrisoftware.globalpom.math;

import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.ceil;
import static org.apache.commons.math3.util.FastMath.floor;

/**
 * Various mathematical utilities.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class MathUtils {

    /**
     * Rounds the specified value toward zero.
     * 
     * @param value
     *            the value.
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
     * @param value
     *            the value.
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
     * Returns the values after the decimal of a real value. I.e. for
     * value=5.123 it returns 0.123. The function breaks for {@link Double#NaN},
     * {@link Double#POSITIVE_INFINITY} and {@link Double#NEGATIVE_INFINITY}.
     * 
     * @since 2.1
     */
    public static double frac(double value) {
        long n = (long) value;
        return value - n;
    }

}
