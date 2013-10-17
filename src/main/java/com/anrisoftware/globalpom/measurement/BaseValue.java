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

import static com.anrisoftware.globalpom.measurement.ValueFactory.DECIMAL;
import static com.anrisoftware.globalpom.measurement.ValueFactory.SIGNIFICANT;
import static com.anrisoftware.globalpom.measurement.ValueFactory.UNCERTAINTY;
import static com.anrisoftware.globalpom.measurement.ValueFactory.VALUE;
import static com.anrisoftware.globalpom.measurement.ValueFactory.VALUE_FACTORY;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Value that calculates error propagation using basic minimal and maximal.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class BaseValue extends AbstractValue {

	/**
	 * @see BaseValueFactory#create(double, int, double, int, ValueFactory)
	 */
	@AssistedInject
	BaseValue(@Assisted(VALUE) double value,
			@Assisted(SIGNIFICANT) int significant,
			@Assisted(UNCERTAINTY) double uncertainty,
			@Assisted(DECIMAL) int decimal,
			@Assisted(VALUE_FACTORY) ValueFactory valueFactory) {
		super(value, significant, uncertainty, decimal, valueFactory);
	}

	/**
	 * @see BaseValueFactory#create(double, int, double, int)
	 */
	@AssistedInject
	BaseValue(@Assisted(VALUE) double value,
			@Assisted(SIGNIFICANT) int significant,
			@Assisted(UNCERTAINTY) double uncertainty,
			@Assisted(DECIMAL) int decimal, BaseValueFactory valueFactory) {
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

	@Override
	protected double logUncertainty() {
		return getUncertainty();
	}
}
