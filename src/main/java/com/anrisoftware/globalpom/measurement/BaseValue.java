/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Value that calculates error propagation using basic minimal and maximal.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class BaseValue extends AbstractValue {

	/**
	 * @see BaseValueFactory#create(double, int, double, int, ValueFactory)
	 */
	@AssistedInject
	BaseValue(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal,
			@Assisted("valueFactory") ValueFactory valueFactory) {
		super(value, significant, uncertainty, decimal, valueFactory);
	}

	/**
	 * @see BaseValueFactory#create(double, int, double, int)
	 */
	@AssistedInject
	BaseValue(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal, BaseValueFactory valueFactory) {
		super(value, significant, uncertainty, decimal, valueFactory);
	}

	@Override
	protected double addUncertainty(Value addend) {
		return addend.getValue();
	}

	@Override
	protected double subUncertainty(Value subtrahend) {
		return subtrahend.getValue();
	}

	@Override
	protected double mulUncertainty(Value factor) {
		return factor.getValue();
	}

	@Override
	protected double divUncertainty(Value divisor) {
		return divisor.getValue();
	}

}
