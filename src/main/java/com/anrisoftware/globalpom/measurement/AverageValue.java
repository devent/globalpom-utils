/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Value that calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class AverageValue extends AbstractValue {

	/**
	 * @see AverageValueFactory#create(double, int, double, int, ValueFactory)
	 */
	@AssistedInject
	AverageValue(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal,
			@Assisted("valueFactory") ValueFactory valueFactory) {
		super(value, significant, uncertainty, decimal, valueFactory);
	}

	/**
	 * @see AverageValueFactory#create(double, int, double, int)
	 */
	@AssistedInject
	AverageValue(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal, AverageValueFactory valueFactory) {
		super(value, significant, uncertainty, decimal, valueFactory);
	}

	@Override
	protected double addUncertainty(Value addend) {
		return subUncertainty(addend);
	}

	@Override
	protected double subUncertainty(Value subtrahend) {
		double uncertaintya = getUncertainty();
		double uncertaintyb = subtrahend.getUncertainty();
		if (subtrahend.isExact()) {
			return uncertaintya;
		} else {
			return uncertaintya + uncertaintyb;
		}
	}

	@Override
	protected double mulUncertainty(Value factor) {
		return divUncertainty(factor);
	}

	@Override
	protected double divUncertainty(Value divisor) {
		double uncertaintya = getUncertainty();
		double uncertaintyb = divisor.getUncertainty();
		if (isExact()) {
			return getValue() * uncertaintyb;
		}
		if (divisor.isExact()) {
			return uncertaintya * divisor.getValue();
		}
		double valuea = getValue();
		double valueb = divisor.getValue();
		return uncertaintya / valuea + uncertaintyb / valueb;
	}

}
