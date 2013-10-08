/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

/**
 * Measured value with uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
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
}
