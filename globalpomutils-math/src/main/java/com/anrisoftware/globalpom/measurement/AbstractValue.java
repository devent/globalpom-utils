/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.measurement;

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToSignificant;
import static java.lang.Math.min;
import static org.apache.commons.math3.util.FastMath.max;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.format.measurement.ValueFormat;
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory;
import com.anrisoftware.globalpom.math.MathUtils;

/**
 * Implements exact value's operators.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
@SuppressWarnings("serial")
public abstract class AbstractValue implements Value, Serializable {

    private static final double DEFAULT_DIV = 3.0;

    private final BigInteger mantissa;

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
        this.mantissa = BigInteger.valueOf(mantissa);
        this.uncertainty = unc;
        this.order = order;
        this.significant = sig;
        this.decimal = dec;
        this.valueFactory = valueFactory;
    }

    protected AbstractValue(BigInteger mantissa, int order, int sig, int dec,
            double unc, ValueFactory valueFactory) {
        this.mantissa = mantissa;
        this.uncertainty = unc;
        this.order = order;
        this.significant = sig;
        this.decimal = dec;
        this.valueFactory = valueFactory;
    }

    @Override
    public BigInteger getMantissa() {
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
        return MathUtils.calculateValue(mantissa, decimal);
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
    public Value valueOf(BigInteger mantissa, int order, int sig, int dec,
            double unc) {
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
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value divNum(Value numerator) {
        double value = numerator.getValue() / this.getValue();
        double unc = divUncertainty(numerator, value);
        int sig = min(significant, numerator.getSignificant());
        int dec = max(decimal, numerator.getDecimal());
        double rvalue = roundToSignificant(value, sig);
        return valueOf(rvalue, sig, dec, unc);
    }

    @Override
    public Value divNum(double numerator) {
        double value = numerator / this.getValue();
        double unc = divUncertainty(numerator, value);
        int sig = significant;
        int dec = decimal;
        double rvalue = roundToSignificant(value, sig);
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

    @Override
    public Value square() {
        return this.mul(this);
    }

    @Override
    public Value cube() {
        return this.square().mul(this);
    }

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
    protected Value createValue(BigInteger m, int order, int sig, int dec,
            double unc) {
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
        return lhs.getMantissa().equals(rhs.getMantissa())
                && lhs.getDecimal() == rhs.getDecimal();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getMantissa()).append(getDecimal())
                .hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(MANTISSA_FIELD, mantissa)
                .append(ORDER_FIELD, order).append(SIG_FIELD, significant)
                .append(DEC_FIELD, decimal).append(UNC_FIELD, uncertainty)
                .toString();
    }

    private Value createValue(double value, int sig, int dec, double unc) {
        double avalue = FastMath.abs(value);
        StringBuilder pattern = new StringBuilder("0");
        pattern.append('.');
        if (avalue > 0) {
            sig--;
        }
        for (int i = 0; i < sig; i++) {
            pattern.append('0');
        }
        pattern.append("E0");
        String svalue;
        Locale locale = Locale.ENGLISH;
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        svalue = new DecimalFormat(pattern.toString(), symbols).format(value);
        if (!Double.isNaN(unc)) {
            String uvalue = new DecimalFormat(String.format("(%s)",
                    pattern.toString()), symbols).format(unc);
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
