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

import javax.inject.Inject;

import org.apache.commons.math3.util.FastMath;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Value that calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class AverageValue extends AbstractValue {

	@Inject
	private ExactAverageValueFactory exactFactory;

	/**
	 * @see AverageValueFactory#create(double, int, double, int, ValueFactory)
	 */
	@AssistedInject
	AverageValue(@Assisted(VALUE) double value,
			@Assisted(SIGNIFICANT) int significant,
			@Assisted(UNCERTAINTY) double uncertainty,
			@Assisted(DECIMAL) int decimal,
			@Assisted(VALUE_FACTORY) ValueFactory valueFactory) {
		super(value, significant, uncertainty, decimal, valueFactory);
	}

	/**
	 * @see AverageValueFactory#create(double, int, double, int)
	 */
	@AssistedInject
	AverageValue(AverageValueFactory valueFactory,
			@Assisted(VALUE) double value,
			@Assisted(SIGNIFICANT) int significant,
			@Assisted(UNCERTAINTY) double uncertainty,
			@Assisted(DECIMAL) int decimal) {
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
		double valuea = getValue();
		if (isExact()) {
			return valuea * uncertaintyb;
		}
		if (divisor.isExact()) {
			return uncertaintya * divisor.getValue();
		}
		double valueb = divisor.getValue();
		return uncertaintya / valuea + uncertaintyb / valueb;
	}

	@Override
	protected double logUncertainty() {
		double uncertaintya = getUncertainty();
		if (isExact()) {
			return uncertaintya;
		}
		double valuea = getValue();
		return uncertaintya / valuea;
	}

	@Override
	protected double expUncertainty() {
		double uncertaintya = getUncertainty();
		if (isExact()) {
			return uncertaintya;
		}
		double valuea = getValue();
		return uncertaintya / FastMath.exp(valuea);
	}

	@Override
	protected Value createValue(double value) {
		return exactFactory.create(value, getValueFactory());
	}

}
