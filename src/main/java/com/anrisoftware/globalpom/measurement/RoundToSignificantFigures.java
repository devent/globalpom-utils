/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.ceil;
import static org.apache.commons.math3.util.FastMath.log10;
import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.round;

/**
 * Rounds the value to the significant figures. Algorithm takes from <a href=
 * "http://stackoverflow.com/questions/202302/rounding-to-an-arbitrary-number-of-significant-digits"
 * >rounding to an arbitrary number of significant digits
 * [stackoverflow.com]</a>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class RoundToSignificantFigures {

	/**
	 * Rounds the value to the significant figures.
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
		return round(value * pow(10, decimal)) / pow(10, decimal);
	}
}
