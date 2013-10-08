/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import static java.lang.Double.NaN;
import static java.lang.Integer.MAX_VALUE;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Exact value that calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ExactAverageValue extends AbstractValue {

	/**
	 * @see ExactAverageValueFactory#create(double, int, double, int,
	 *      ValueFactory)
	 */
	@AssistedInject
	ExactAverageValue(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal,
			@Assisted("valueFactory") ValueFactory valueFactory) {
		super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
	}

	/**
	 * @see ExactAverageValueFactory#create(double, ValueFactory)
	 */
	@AssistedInject
	ExactAverageValue(@Assisted double value,
			@Assisted ValueFactory valueFactory) {
		super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
	}

	/**
	 * @see ExactAverageValueFactory#create(double)
	 */
	@AssistedInject
	ExactAverageValue(@Assisted double value, AverageValueFactory valueFactory) {
		super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
	}

	@Override
	public Value getRoundedValue() {
		double value = getValue();
		return createValue(value, MAX_VALUE, NaN, MAX_VALUE);
	}

	@Override
	public int getSignificant() {
		return MAX_VALUE;
	}

	@Override
	public boolean isExact() {
		return true;
	}

	@Override
	protected double addUncertainty(Value addend) {
		return addend.getUncertainty();
	}

	@Override
	protected double subUncertainty(Value subtrahend) {
		return subtrahend.getUncertainty();
	}

	@Override
	protected double mulUncertainty(Value factor) {
		double uncertainty = factor.getUncertainty();
		if (!factor.isExact()) {
			uncertainty = getValue() * uncertainty;
		}
		return uncertainty;
	}

	@Override
	protected double divUncertainty(Value divisor) {
		double uncertainty = divisor.getUncertainty();
		if (!divisor.isExact()) {
			uncertainty = getValue() * uncertainty;
		}
		return uncertainty;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(getValueString()).toString();
	}

	@Override
	public String getValueString() {
		return String.format("%f", getValue());
	}
}
