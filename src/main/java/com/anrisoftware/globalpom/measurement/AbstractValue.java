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

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToSignificant;
import static java.lang.Double.isNaN;
import static java.lang.Math.min;
import static java.lang.String.format;
import static org.apache.commons.math3.util.FastMath.abs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

/**
 * Implements exact value's operators.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public abstract class AbstractValue implements Value {

	private static final String FORMAT = "%%.%df";

	private static final String SIGNIFICANT = "significant";

	private static final String UNCERTAINTY = "uncertainty";

	private static final String VALUE = "value";

	private final double value;

	private final int significant;

	private final double uncertainty;

	private final int decimal;

	private final ValueFactory valueFactory;

	/**
	 * @see ValueFactory#create(double, int, double, int, ValueFactory)
	 */
	protected AbstractValue(double value, int sig, double un, int dec,
			ValueFactory valueFactory) {
		this.value = value;
		this.significant = sig;
		this.uncertainty = un;
		this.decimal = dec;
		this.valueFactory = valueFactory;
	}

	public ValueFactory getValueFactory() {
		return valueFactory;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public Value getRoundedValue() {
		int sig = roundedSignificantFigure();
		double v = this.value;
		double un = this.uncertainty;
		int dec = this.decimal;
		if (isExact()) {
			return createValue(v, sig, un, dec);
		} else {
			return createValue(roundToDecimal(v, dec), sig,
					roundToSignificant(un, sig), dec);
		}
	}

	private int roundedSignificantFigure() {
		int sig = this.significant;
		double v = abs(getUncertainty());
		while (v < 0.0) {
			v *= 10;
		}
		if (v >= 1.0) {
			return sig + 1;
		} else {
			return sig;
		}
	}

	@Override
	public int getSignificant() {
		return significant;
	}

	@Override
	public double getUncertainty() {
		return uncertainty;
	}

	@Override
	public boolean isExact() {
		return isNaN(uncertainty);
	}

	@Override
	public int getDecimal() {
		return decimal;
	}

	@Override
	public Value add(double addend) {
		return add(createValue(addend));
	}

	@Override
	public Value add(Value addend) {
		double value = this.value + addend.getValue();
		double uncertainty = addUncertainty(addend);
		int sig = min(significant, addend.getSignificant());
		int dec = min(decimal, addend.getDecimal());
		return createValue(value, sig, uncertainty, dec);
	}

	/**
	 * Adds the uncertainty of this value and the addend.
	 * 
	 * @param addend
	 *            the {@link Value} addend.
	 * 
	 * @return the added uncertainly or {@link Double#NaN} if the value is
	 *         exact.
	 */
	protected abstract double addUncertainty(Value addend);

	@Override
	public Value sub(double subtrahend) {
		return sub(createValue(subtrahend));
	}

	@Override
	public Value sub(Value subtrahend) {
		double value = this.value - subtrahend.getValue();
		double uncertainty = subUncertainty(subtrahend);
		int sig = min(significant, subtrahend.getSignificant());
		int dec = min(decimal, subtrahend.getDecimal());
		return createValue(value, sig, uncertainty, dec);
	}

	/**
	 * Subtracts the uncertainty of this value and the subtrahend.
	 * 
	 * @param subtrahend
	 *            the {@link Value} subtrahend.
	 * 
	 * @return the subtracted uncertainly or {@link Double#NaN} if the value is
	 *         exact.
	 */
	protected abstract double subUncertainty(Value subtrahend);

	@Override
	public Value mul(double factor) {
		return mul(createValue(factor));
	}

	@Override
	public Value mul(Value factor) {
		double value = this.value * factor.getValue();
		double uncertainty = mulUncertainty(factor);
		int sig = min(significant, factor.getSignificant());
		int dec = min(decimal, factor.getDecimal());
		return createValue(value, sig, uncertainty, dec);
	}

	/**
	 * Multiplies the uncertainty of this value and the factor.
	 * 
	 * @param factor
	 *            the {@link Value} factor.
	 * 
	 * @return the multiplied uncertainly or {@link Double#NaN} if the value is
	 *         exact.
	 */
	protected abstract double mulUncertainty(Value factor);

	@Override
	public Value div(double divisor) {
		return div(createValue(divisor));
	}

	@Override
	public Value div(Value divisor) {
		double value = this.value / divisor.getValue();
		double uncertainty = divUncertainty(divisor);
		int sig = min(significant, divisor.getSignificant());
		int dec = min(decimal, divisor.getDecimal());
		return createValue(value, sig, uncertainty, dec);
	}

	/**
	 * Divides the uncertainty of this value and the divisor.
	 * 
	 * @param divisor
	 *            the {@link Value} divisor.
	 * 
	 * @return the divided uncertainly or {@link Double#NaN} if the value is
	 *         exact.
	 */
	protected abstract double divUncertainty(Value divisor);

	@Override
	public Value log() {
		double value = FastMath.log(this.value);
		double uncertainty = logUncertainty();
		int sig = significant;
		int dec = decimal;
		return createValue(value, sig, uncertainty, dec);
	}

	/**
	 * Logarithm the uncertainty of this value.
	 * 
	 * @return the logarithm uncertainly or {@link Double#NaN} if the value is
	 *         exact.
	 */
	protected abstract double logUncertainty();

	@Override
	public Value exp() {
		double value = FastMath.exp(this.value);
		double uncertainty = expUncertainty();
		int sig = significant;
		int dec = decimal;
		return createValue(value, sig, uncertainty, dec);
	}

	protected abstract double expUncertainty();

	/**
	 * Create a new value.
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param sig
	 *            the significant figures of the value.
	 * 
	 * @param un
	 *            the uncertainty or {@link Double#NaN} for an exact number.
	 * 
	 * @param dec
	 *            the least significant decimal places.
	 * 
	 * @return the {@link Value}.
	 */
	protected Value createValue(double value, int sig, double un, int dec) {
		return valueFactory.create(value, sig, un, dec, valueFactory);
	}

	/**
	 * Create a new exact value.
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @return the exact {@link Value}.
	 */
	protected abstract Value createValue(double value);

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(VALUE, getValueString())
				.append(UNCERTAINTY, getUncertaintyString())
				.append(SIGNIFICANT, getSignificant()).toString();
	}

	public String getUncertaintyString() {
		long sig = getSignificant() + 1;
		if (sig < 0) {
			sig = 5;
		}
		return format(format(FORMAT, sig), getUncertainty());
	}

	public String getValueString() {
		long sig = getSignificant() + 1;
		if (sig < 0) {
			sig = 5;
		}
		return format(format(FORMAT, sig), getValue());
	}
}
