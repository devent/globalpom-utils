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
package com.anrisoftware.globalpom.math.measurement;

import static com.anrisoftware.globalpom.math.measurement.ValueFactory.DECIMAL;
import static com.anrisoftware.globalpom.math.measurement.ValueFactory.MANTISSA;
import static com.anrisoftware.globalpom.math.measurement.ValueFactory.ORDER;
import static com.anrisoftware.globalpom.math.measurement.ValueFactory.SIGNIFICANT;

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
