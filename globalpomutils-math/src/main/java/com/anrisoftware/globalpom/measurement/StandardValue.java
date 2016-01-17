/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.measurement.ValueFactory.DECIMAL;
import static com.anrisoftware.globalpom.measurement.ValueFactory.MANTISSA;
import static com.anrisoftware.globalpom.measurement.ValueFactory.ORDER;
import static com.anrisoftware.globalpom.measurement.ValueFactory.SIGNIFICANT;

import java.math.BigInteger;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Value that calculates error propagation using standard uncertainty.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class StandardValue extends AbstractValue {

    /**
     * @see StandardValueFactory#create(BigInteger, int, int, int)
     */
    @AssistedInject
    StandardValue(StandardValueFactory valueFactory,
            @Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec) {
        super(mantissa, order, sig, dec, Double.NaN, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(long, int, int, int)
     */
    @AssistedInject
    StandardValue(StandardValueFactory valueFactory,
            @Assisted(MANTISSA) long mantissa, @Assisted(ORDER) int order,
            @Assisted(SIGNIFICANT) int sig, @Assisted(DECIMAL) int dec) {
        super(mantissa, order, sig, dec, Double.NaN, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(BigInteger, int, int, int, ValueFactory)
     */
    @AssistedInject
    StandardValue(@Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, @Assisted ValueFactory valueFactory) {
        super(mantissa, order, sig, dec, Double.NaN, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(long, int, int, int, ValueFactory)
     */
    @AssistedInject
    StandardValue(@Assisted(MANTISSA) long mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, @Assisted ValueFactory valueFactory) {
        super(mantissa, order, sig, dec, Double.NaN, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(BigInteger, int, int, int, double)
     */
    @AssistedInject
    StandardValue(StandardValueFactory valueFactory,
            @Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, @Assisted double unc) {
        super(mantissa, order, sig, dec, unc, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(long, int, int, int, double)
     */
    @AssistedInject
    StandardValue(StandardValueFactory valueFactory,
            @Assisted(MANTISSA) long mantissa, @Assisted(ORDER) int order,
            @Assisted(SIGNIFICANT) int sig, @Assisted(DECIMAL) int dec,
            @Assisted double unc) {
        super(mantissa, order, sig, dec, unc, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(long, int, int, int, double,
     *      ValueFactory)
     */
    @AssistedInject
    StandardValue(@Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, @Assisted double unc,
            @Assisted ValueFactory valueFactory) {
        super(mantissa, order, sig, dec, unc, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(long, int, int, int, double,
     *      ValueFactory)
     */
    @AssistedInject
    StandardValue(@Assisted(MANTISSA) long mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, @Assisted double unc,
            @Assisted ValueFactory valueFactory) {
        super(mantissa, order, sig, dec, unc, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(Value)
     */
    @AssistedInject
    StandardValue(StandardValueFactory valueFactory, @Assisted Value value) {
        super(value, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(Value, ValueFactory)
     */
    @AssistedInject
    StandardValue(@Assisted Value value, @Assisted ValueFactory valueFactory) {
        super(value, valueFactory);
    }

    @Inject
    void setValueFactory(StandardValueFactory valueFactory) {
        if (this.valueFactory == null) {
            this.valueFactory = valueFactory;
        }
    }

    @Override
    protected double addUncertainty(Value addend, double sum) {
        return subUncertainty(addend, sum);
    }

    @Override
    protected double addUncertainty(double addend, double sum) {
        return subUncertainty(addend, sum);
    }

    @Override
    protected double subUncertainty(Value subtrahend, double diff) {
        double sa = getUncertainty();
        double sb = subtrahend.getUncertainty();
        if (subtrahend.isExact()) {
            return sa;
        } else {
            return StandardValueMath.subUncertainty(sa, sb);
        }
    }

    @Override
    protected double subUncertainty(double subtrahend, double diff) {
        return getUncertainty();
    }

    @Override
    protected double mulUncertainty(Value factor, double product) {
        return divUncertainty(factor, product);
    }

    @Override
    protected double mulUncertainty(double factor, double product) {
        return divUncertainty(factor, product);
    }

    @Override
    protected double divUncertainty(Value divisor, double quotient) {
        double sa = getUncertainty();
        double sb = divisor.getUncertainty();
        double a = getValue();
        double b = divisor.getValue();
        if (isExact()) {
            return StandardValueMath.mulUncertaintly(b, sb, quotient);
        }
        if (divisor.isExact()) {
            return StandardValueMath.mulUncertaintly(a, sa, quotient);
        }
        return StandardValueMath.mulUncertaintly(a, sa, b, sb, quotient);
    }

    @Override
    protected double divUncertainty(double divisor, double quotient) {
        double sa = getUncertainty();
        double a = getValue();
        return StandardValueMath.mulUncertaintly(a, sa, quotient);
    }

    @Override
    protected double reciprocalUncertainty(double value) {
        double a = getValue();
        double z = value;
        double sa = getUncertainty();
        return StandardValueMath.reciprocalUncertaintly(a, sa, z);
    }

    @Override
    protected double logUncertainty(double exponent) {
        double sa = getUncertainty();
        if (isExact()) {
            return sa;
        }
        double a = getValue();
        return StandardValueMath.logUncertainty(a, sa);
    }

    @Override
    protected double expUncertainty(double power) {
        double sa = getUncertainty();
        if (isExact()) {
            return sa;
        }
        return StandardValueMath.expUncertainty(sa, power);
    }

    @Override
    protected double absUncertainty(double value) {
        double sa = getUncertainty();
        return sa;
    }

}
