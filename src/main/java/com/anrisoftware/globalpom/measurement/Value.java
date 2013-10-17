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
public interface Value {

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
	 * Calculates the addition of this value with the addend.
	 * 
	 * @param addend
	 *            the addend.
	 * 
	 * @return the result {@link Value}.
	 */
	Value add(Value addend);

	/**
	 * Calculates the subtraction of this value with the subtrahend.
	 * 
	 * @param subtrahend
	 *            the subtrahend.
	 * 
	 * @return the result {@link Value}.
	 */
	Value sub(Value subtrahend);

	/**
	 * Calculates the multiplication of this value with the factor.
	 * 
	 * @param factor
	 *            the factor.
	 * 
	 * @return the result {@link Value}.
	 */
	Value mul(Value factor);

	/**
	 * Calculates the division of this value with the divisor.
	 * 
	 * @param divisor
	 *            the divisor.
	 * 
	 * @return the result {@link Value}.
	 */
	Value div(Value divisor);

	/**
	 * Calculates the natural logarithm of the value.
	 * 
	 * @return the value.
	 */
	Value log();
}
