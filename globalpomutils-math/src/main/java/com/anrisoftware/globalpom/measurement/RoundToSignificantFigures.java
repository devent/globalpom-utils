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
package com.anrisoftware.globalpom.measurement;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.ceil;
import static org.apache.commons.math3.util.FastMath.log10;
import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.round;

import java.math.BigDecimal;

/**
 * Rounds the value to the significant figures.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class RoundToSignificantFigures {

	/**
	 * Rounds the value to the significant figures.
	 * <p>
	 * Algorithm takes from <a href=
	 * "http://stackoverflow.com/questions/202302/rounding-to-an-arbitrary-number-of-significant-digits"
	 * >rounding to an arbitrary number of significant digits
	 * [stackoverflow.com]</a>
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param significant
	 *            the significant figures.
	 * 
	 * @return the rounded value.
	 */
	public static double roundToSignificant(double value, int significant) {
		if (value == 0) {
			return 0;
		}
		double d = ceil(log10(abs(value)));
		int power = significant - (int) d;
		double magnitude = pow(10, power);
		long shifted = round(value * magnitude);
		return shifted / magnitude;
	}

	/**
	 * Rounds the value to the decimal places.
	 * <p>
	 * Algorithm takes from <a href=
	 * "http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java"
	 * >How to round a number to n decimal places in Java
	 * [stackoverflow.com]</a>
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param decimal
	 *            the decimal places.
	 * 
	 * @return the rounded value.
	 */
	public static double roundToDecimal(double value, int decimal) {
		return BigDecimal.valueOf(value).setScale(decimal, ROUND_HALF_UP)
				.doubleValue();
	}
}
