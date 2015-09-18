/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
import static java.lang.Math.min;
import static org.apache.commons.math3.util.FastMath.max;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.format.measurement.ValueFormat;
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory;

/**
 * Implements exact value's operators.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
@SuppressWarnings("serial")
public abstract class AbstractValue implements Value, Serializable {

    private static final double DEFAULT_DIV = 3.0;

    private final long mantissa;

    private final int significant;

    private final double uncertainty;

    private final int decimal;

    private final int order;

    @Inject
    protected transient ValueFormatFactory valueFormatFactory;

    protected transient ValueFactory valueFactory;

    protected AbstractValue(Value value, ValueFactory valueFactory) {
        this(value.getMantissa(), value.getOrder(), value.getSignificant(),
                value.getDecimal(), value.getUncertainty(), valueFactory);
    }

    protected AbstractValue(long mantissa, int order, int sig, int dec,
            double unc, ValueFactory valueFactory) {
        this.mantissa = mantissa;
        this.uncertainty = unc;
        this.order = order;
        this.significant = sig;
        this.decimal = dec;
        this.valueFactory = valueFactory;
    }

    @Override
    public long getMantissa() {
        return mantissa;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public int getSignificant() {
        return significant;
    }

    @Override
    public double getUncertainty() {
        if (isExact()) {
            return Double.NaN;
        }
        return uncertainty;
    }

    @Override
    public double getRoundedUncertainty() {
        if (isExact()) {
            return Double.NaN;
        }
        return roundToDecimal(uncertainty, getAbsDec());
    }

    @Override
    public int getDecimal() {
        return decimal;
    }

    @Override
    public boolean isExact() {
        return Double.isNaN(uncertainty);
    }

    @Override
    public double getValue() {
        return getMantissa() * FastMath.pow(10, getDecimal());
    }

    @Override
    public double getRoundedValue() {
        return roundedValue(getSignificant(), getAbsDec());
    }

    @Override
    public double roundedValue(int sig, int dec) {
        double value = getValue();
        value = roundToSignificant(value, sig);
        value = roundToDecimal(value, dec);
        return value;
    }

    @Override
    public Value getMinValue() {
        return minValue(DEFAULT_DIV);
    }

    @Override
    public Value minValue(double deviation) {
        if (isExact()) {
            return this;
        } else {
            return this.sub(deviation * getUncertainty());
        }
    }

    @Override
    public Value getMaxValue() {
        return maxValue(DEFAULT_DIV);
    }

    @Override
    public Value maxValue(double deviation) {
        if (isExact()) {
            return this;
        } else {
            return add(deviation * getUncertainty());
        }
    }

    @Override
    public Value valueOf(long mantissa, int order, int sig, int dec, double unc) {
        return createValue(mantissa, order, sig, dec, unc);
    }

    @Override
    public Value valueOf(double value, int sig, int dec, double unc) {
        return createValue(value, sig, dec, unc);
    }

    @Override
    public Value valueOf(double value, int dec, double unc) {
        return createValue(value, dec, unc);
    }

    @Override
    public Value add(Value addend) {
        double value = this.getValue() + addend.getValue();
        double unc = addUncertainty(addend, value);
        int sig = min(significant, addend.getSignificant());
        int dec = max(decimal, addend.getDecimal());
        double rvalue = roundToDecimal(value, getAbsDec());
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value add(double addend) {
        double value = this.getValue() + addend;
        double unc = addUncertainty(addend, value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToDecimal(value, getAbsDec());
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value plus(Value addend) {
        return add(addend);
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

    /**
     * Adds the uncertainty of this value and the addend.
     *
     * @param addend
     *            the {@link Double} addend.
     *
     * @param sum
     *            the value of the sum.
     *
     * @return the added uncertainly or {@link Double#NaN} if the value is
     *         exact.
     */
    protected abstract double addUncertainty(double addend, double sum);

    @Override
    public Value sub(Value subtrahend) {
        double value = this.getValue() - subtrahend.getValue();
        double unc = subUncertainty(subtrahend, value);
        int sig = min(significant, subtrahend.getSignificant());
        int dec = max(decimal, subtrahend.getDecimal());
        double rvalue = roundToDecimal(value, getAbsDec());
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value sub(double subtrahend) {
        double value = this.getValue() - subtrahend;
        double unc = subUncertainty(subtrahend, value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToDecimal(value, getAbsDec());
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value minus(Value subtrahend) {
        return sub(subtrahend);
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

    /**
     * Subtracts the uncertainty of this value and the subtrahend.
     *
     * @param subtrahend
     *            the {@link Double} subtrahend.
     *
     * @param diff
     *            the value of the difference.
     *
     * @return the subtracted uncertainly.
     */
    protected abstract double subUncertainty(double subtrahend, double diff);

    @Override
    public Value mul(Value factor) {
        double value = this.getValue() * factor.getValue();
        double unc = mulUncertainty(factor, value);
        int sig = min(significant, factor.getSignificant());
        int dec = max(decimal, factor.getDecimal());
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value mul(double factor) {
        double value = this.getValue() * factor;
        double unc = mulUncertainty(factor, value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value multiply(Value factor) {
        return mul(factor);
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

    /**
     * Multiplies the uncertainty of this value and the factor.
     *
     * @param factor
     *            the {@link Double} factor.
     *
     * @param product
     *            the value of the product.
     *
     * @return the multiplied uncertainly.
     */
    protected abstract double mulUncertainty(double factor, double product);

    @Override
    public Value div(Value divisor) {
        double value = this.getValue() / divisor.getValue();
        double unc = divUncertainty(divisor, value);
        int sig = min(significant, divisor.getSignificant());
        int dec = max(decimal, divisor.getDecimal());
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value div(double divisor) {
        double value = this.getValue() / divisor;
        double unc = divUncertainty(divisor, value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToSignificant(value, FastMath.abs(dec));
        return valueOf(rvalue, sig, dec, unc);
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

    /**
     * Divides the uncertainty of this value and the divisor.
     *
     * @param divisor
     *            the {@link Double} divisor.
     *
     * @param quotient
     *            the value of the quotient.
     *
     * @return the divided uncertainly.
     */
    protected abstract double divUncertainty(double divisor, double quotient);

    @Override
    public Value reciprocal() {
        double value = 1.0 / this.getValue();
        double unc = reciprocalUncertainty(value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
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
        double value = FastMath.log(this.getValue());
        double unc = logUncertainty(value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
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
        double value = FastMath.exp(this.getValue());
        double unc = expUncertainty(value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToDecimal(value, getAbsDec());
        return valueOf(rvalue, sig, dec, unc);
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
        double value = FastMath.abs(this.getValue());
        double unc = absUncertainty(value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
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
     * Creates a new value with the specified uncertainty.
     *
     * @param m
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param unc
     *            the uncertainty {@link Value}.
     *
     * @return the {@link Value}.
     */
    protected Value createValue(long m, int order, int sig, int dec, double unc) {
        return valueFactory.create(m, order, sig, dec, unc, valueFactory);
    }

    @Override
    public int compareTo(Value rhs) {
        return Double.compare(this.getValue(), rhs.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Value)) {
            return false;
        }
        Value rhs = (Value) obj;
        return equalExact(this, rhs);
    }

    @Override
    public boolean isConsistent(Value rhs) {
        return rhs == null ? false : rhs.isExact() ? equalExact(this, rhs)
                : equalsUncertain(rhs);
    }

    private boolean equalsUncertain(Value rhs) {
        double delta = FastMath.abs(this.getValue() - rhs.getValue());
        double deltaunc = this.getUncertainty() + rhs.getUncertainty();
        return delta <= deltaunc;
    }

    private boolean equalExact(Value lhs, Value rhs) {
        return lhs.getMantissa() == rhs.getMantissa()
                && lhs.getDecimal() == rhs.getDecimal();
    }

    @Override
    public int hashCode() {
        return new Double(mantissa).hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(MANTISSA_FIELD, mantissa)
                .append(ORDER_FIELD, order).append(SIG_FIELD, significant)
                .append(DEC_FIELD, decimal).append(UNC_FIELD, uncertainty)
                .toString();
    }

    private Value createValue(double value, int sig, int dec, double unc) {
        StringBuilder pattern = new StringBuilder("0");
        if (decimal < 0) {
            pattern.append('.');
            for (int i = 0; i < sig; i++) {
                pattern.append('0');
            }
            for (int i = dec; i < 0; i++) {
                pattern.append('#');
            }
            pattern.append("E0");
        }
        String svalue;
        Locale locale = Locale.ENGLISH;
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        svalue = new DecimalFormat(pattern.toString(), symbols).format(value);
        if (!Double.isNaN(unc)) {
            String uvalue = new DecimalFormat(String.format("(%s)",
                    pattern.toString())).format(unc);
            svalue += uvalue;
        }
        ValueFormat format = valueFormatFactory.create(locale, valueFactory);
        ParsePosition pos = new ParsePosition(0);
        Value vv = format.parse(svalue, pos);
        return vv;
    }

    private Value createValue(double value, int dec, double unc) {
        String svalue = Double.toString(value);
        Locale locale = Locale.ENGLISH;
        ValueFormat format;
        format = valueFormatFactory.create(locale, valueFactory);
        format.setDecimal(dec);
        ParsePosition pos = new ParsePosition(0);
        Value vv = format.parse(svalue, unc, pos);
        return vv;
    }

    private int getAbsDec() {
        return decimal < 0 ? -1 * decimal : 0;
    }

    private static final String UNC_FIELD = "unc";

    private static final String DEC_FIELD = "dec";

    private static final String SIG_FIELD = "sig";

    private static final String ORDER_FIELD = "order";

    private static final String MANTISSA_FIELD = "mantissa";

}
