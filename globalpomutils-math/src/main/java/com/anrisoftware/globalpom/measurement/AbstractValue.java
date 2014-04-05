/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToSignificant;
import static java.lang.Double.isNaN;
import static java.lang.Math.min;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

/**
 * Implements exact value's operators.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public abstract class AbstractValue implements Value, Serializable {

    private static final double DEFAULT_DIV = 3.0;

    private static final double EPSILON = 10e-9;

    private final double value;

    private final int significant;

    private final double uncertainty;

    private final int decimal;

    private transient ValueFactory valueFactory;

    @Inject
    private ValueToString toString;

    /**
     * @see ValueFactory#create(Value, ValueFactory)
     */
    protected AbstractValue(Value value, ValueFactory valueFactory) {
        this(value.getValue(), value.getSignificant(), value.getUncertainty(),
                value.getDecimal(), valueFactory);
    }

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

    public void setValueFactory(ValueFactory factory) {
        this.valueFactory = factory;
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
        int sig = this.significant;
        int dec = this.decimal;
        return roundedValue(sig, dec);
    }

    @Override
    public Value roundedValue(int sig, int dec) {
        double v = this.value;
        double un = this.uncertainty;
        if (isExact()) {
            return createValue(v, sig, un, dec);
        } else {
            double value = roundToDecimal(v, sig);
            double signi = roundToSignificant(un, roundedSignificantFigure(sig));
            return createValue(value, sig, signi, dec);
        }
    }

    private int roundedSignificantFigure(int sig) {
        double v = FastMath.abs(getUncertainty());
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
    public double getMinValue() {
        return minValue(DEFAULT_DIV);
    }

    @Override
    public double minValue(double deviation) {
        if (isExact()) {
            return getValue();
        } else {
            return getValue() - (deviation * getUncertainty());
        }
    }

    @Override
    public double getMaxValue() {
        return maxValue(DEFAULT_DIV);
    }

    @Override
    public double maxValue(double deviation) {
        if (isExact()) {
            return getValue();
        } else {
            return getValue() + (deviation * getUncertainty());
        }
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
    public Value add(Value addend) {
        double value = this.value + addend.getValue();
        double uncertainty = addUncertainty(addend, value);
        int sig = min(significant, addend.getSignificant());
        int dec = min(decimal, addend.getDecimal());
        return createValue(value, sig, uncertainty, dec);
    }

    @Override
    public Value plus(Value addend) {
        return add(addend);
    }

    @Override
    public Value add(double addend) {
        return add(createValue(addend));
    }

    @Override
    public Value plus(double addend) {
        return add(addend);
    }

    /**
     * Adds the uncertainty of this value and the addend.
     * 
     * @param addend
     *            the {@link Value} addend.
     * 
     * @param sum
     *            the value of the sum.
     * 
     * @return the added uncertainly or {@link Double#NaN} if the value is
     *         exact.
     */
    protected abstract double addUncertainty(Value addend, double sum);

    @Override
    public Value sub(Value subtrahend) {
        double value = this.value - subtrahend.getValue();
        double uncertainty = subUncertainty(subtrahend, value);
        int sig = min(significant, subtrahend.getSignificant());
        int dec = min(decimal, subtrahend.getDecimal());
        return createValue(value, sig, uncertainty, dec);
    }

    @Override
    public Value minus(Value subtrahend) {
        return sub(subtrahend);
    }

    @Override
    public Value sub(double subtrahend) {
        return sub(createValue(subtrahend));
    }

    @Override
    public Value minus(double subtrahend) {
        return sub(subtrahend);
    }

    /**
     * Subtracts the uncertainty of this value and the subtrahend.
     * 
     * @param subtrahend
     *            the {@link Value} subtrahend.
     * 
     * @param diff
     *            the value of the difference.
     * 
     * @return the subtracted uncertainly.
     */
    protected abstract double subUncertainty(Value subtrahend, double diff);

    @Override
    public Value mul(Value factor) {
        double value = this.value * factor.getValue();
        double uncertainty = mulUncertainty(factor, value);
        int sig = min(significant, factor.getSignificant());
        int dec = min(decimal, factor.getDecimal());
        return createValue(value, sig, uncertainty, dec);
    }

    @Override
    public Value multiply(Value factor) {
        return mul(factor);
    }

    @Override
    public Value mul(double factor) {
        return mul(createValue(factor));
    }

    @Override
    public Value multiply(double factor) {
        return mul(factor);
    }

    /**
     * Multiplies the uncertainty of this value and the factor.
     * 
     * @param factor
     *            the {@link Value} factor.
     * 
     * @param product
     *            the value of the product.
     * 
     * @return the multiplied uncertainly.
     */
    protected abstract double mulUncertainty(Value factor, double product);

    @Override
    public Value div(double divisor) {
        return div(createValue(divisor));
    }

    @Override
    public Value div(Value divisor) {
        double value = this.value / divisor.getValue();
        double uncertainty = divUncertainty(divisor, value);
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
     * @param quotient
     *            the value of the quotient.
     * 
     * @return the divided uncertainly.
     */
    protected abstract double divUncertainty(Value divisor, double quotient);

    @Override
    public Value reciprocal() {
        double value = 1.0 / this.value;
        double uncertainty = reciprocalUncertainty(value);
        int sig = significant;
        int dec = decimal;
        return createValue(value, sig, uncertainty, dec);
    }

    /**
     * Calculates the uncertainty of this value as reciprocal.
     * 
     * @param value
     *            the value.
     * 
     * @return the reciprocal uncertainly.
     */
    protected abstract double reciprocalUncertainty(double value);

    @Override
    public Value log() {
        double value = FastMath.log(this.value);
        double uncertainty = logUncertainty(value);
        int sig = significant;
        int dec = decimal;
        return createValue(value, sig, uncertainty, dec);
    }

    /**
     * Logarithm the uncertainty of this value.
     * 
     * @param exponent
     *            the value of the exponent.
     * 
     * @return the logarithm uncertainly.
     */
    protected abstract double logUncertainty(double exponent);

    @Override
    public Value exp() {
        double value = FastMath.exp(this.value);
        double uncertainty = expUncertainty(value);
        int sig = significant;
        int dec = decimal;
        return createValue(value, sig, uncertainty, dec);
    }

    /**
     * Power to the basis of e the uncertainty of this value.
     * 
     * @param power
     *            the value of the power.
     * 
     * @return the power uncertainly.
     */
    protected abstract double expUncertainty(double power);

    @Override
    public Value abs() {
        double value = FastMath.abs(this.value);
        double uncertainty = absUncertainty(value);
        int sig = significant;
        int dec = decimal;
        return createValue(value, sig, uncertainty, dec);
    }

    /**
     * Absolute uncertainty of this value.
     * 
     * @param value
     *            the absolute value.
     * 
     * @return the uncertainly.
     */
    protected abstract double absUncertainty(double value);

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
    public int compareTo(Object o) {
        if (o instanceof Value) {
            return compare((Value) o);
        }
        if (o instanceof Number) {
            return compare((Number) o);
        }
        throw new ClassCastException();
    }

    @Override
    public int compare(Value value) {
        return compare(value, DEFAULT_DIV);
    }

    @Override
    public int compare(Value value, double dev) {
        if (equals(value, dev)) {
            return 0;
        }
        Value delta = this.sub(value).getRoundedValue();
        double deltavalue = delta.getValue();
        double deltaunc = delta.getUncertainty();
        double d = deltavalue - (deltaunc * dev);
        if (d < 0.0) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public int compare(Number value) {
        return compare(value, DEFAULT_DIV);
    }

    @Override
    public int compare(Number value, double dev) {
        return compare(createValue(value.doubleValue()), dev);
    }

    @Override
    public boolean equals(Object obj) {
        return equals(obj, DEFAULT_DIV);
    }

    @Override
    public boolean equals(Object obj, double dev) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Value rhs = asValue(obj);
        return rhs == null ? false : rhs.isExact() ? equalExact(this, rhs)
                : equalsUncertain(rhs, dev);
    }

    private Value asValue(Object obj) {
        if (obj instanceof Value) {
            return (Value) obj;
        } else if (obj instanceof Number) {
            return createValue(((Number) obj).doubleValue());
        }
        return null;
    }

    private boolean equalsUncertain(Value rhs, double dev) {
        Value delta = this.sub(rhs).getRoundedValue();
        double deltavalue = FastMath.abs(delta.getValue());
        double deltaunc = delta.getUncertainty();
        return deltavalue - (deltaunc * dev) <= 0.0;
    }

    private boolean equalExact(Value lhs, Value rhs) {
        return FastMath.abs(lhs.getValue() - rhs.getValue()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        toString.format(buff, this);
        return new ToStringBuilder(this).append(buff.toString()).build();
    }

}
