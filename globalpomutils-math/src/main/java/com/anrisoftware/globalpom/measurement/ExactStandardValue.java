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

import static com.anrisoftware.globalpom.measurement.ValueFactory.DECIMAL;
import static com.anrisoftware.globalpom.measurement.ValueFactory.SIGNIFICANT;
import static com.anrisoftware.globalpom.measurement.ValueFactory.UNCERTAINTY;
import static com.anrisoftware.globalpom.measurement.ValueFactory.VALUE;
import static com.anrisoftware.globalpom.measurement.ValueFactory.VALUE_FACTORY;
import static java.lang.Double.NaN;
import static java.lang.Integer.MAX_VALUE;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Exact value that calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ExactStandardValue extends AbstractValue {

    /**
     * @see ExactStandardValueFactory#create(double, int, double, int)
     */
    @AssistedInject
    ExactStandardValue(StandardValueFactory valueFactory,
            @Assisted(VALUE) double value,
            @Assisted(SIGNIFICANT) int significant,
            @Assisted(UNCERTAINTY) double uncertainty,
            @Assisted(DECIMAL) int decimal) {
        super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
    }

    /**
     * @see ExactStandardValueFactory#create(double, int, double, int,
     *      ValueFactory)
     */
    @AssistedInject
    ExactStandardValue(@Assisted(VALUE) double value,
            @Assisted(SIGNIFICANT) int significant,
            @Assisted(UNCERTAINTY) double uncertainty,
            @Assisted(DECIMAL) int decimal,
            @Assisted(VALUE_FACTORY) ValueFactory valueFactory) {
        super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
    }

    /**
     * @see ExactStandardValueFactory#create(double, ValueFactory)
     */
    @AssistedInject
    ExactStandardValue(@Assisted double value,
            @Assisted ValueFactory valueFactory) {
        super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
    }

    /**
     * @see ExactStandardValueFactory#create(double)
     */
    @AssistedInject
    ExactStandardValue(StandardValueFactory valueFactory, @Assisted double value) {
        super(value, MAX_VALUE, NaN, MAX_VALUE, valueFactory);
    }

    /**
     * @see ExactStandardValue#create(Value)
     */
    @AssistedInject
    ExactStandardValue(StandardValueFactory valueFactory, @Assisted Value value) {
        super(value, valueFactory);
    }

    /**
     * @see ExactStandardValue#create(Value, ValueFactory)
     */
    @AssistedInject
    ExactStandardValue(@Assisted Value value,
            @Assisted(VALUE_FACTORY) ValueFactory valueFactory) {
        super(value, valueFactory);
    }

    @Inject
    public void setValueFactory(StandardValueFactory factory) {
        super.setValueFactory(factory);
    }

    @Override
    public Value getRoundedValue() {
        double value = getValue();
        return createValue(value, MAX_VALUE, NaN, MAX_VALUE);
    }

    @Override
    public int getSignificant() {
        return MAX_VALUE;
    }

    @Override
    public boolean isExact() {
        return true;
    }

    @Override
    protected double addUncertainty(Value addend, double sum) {
        return addend.getUncertainty();
    }

    @Override
    protected double subUncertainty(Value subtrahend, double diff) {
        return subtrahend.getUncertainty();
    }

    @Override
    protected double mulUncertainty(Value factor, double product) {
        double uncertainty = factor.getUncertainty();
        if (!factor.isExact()) {
            uncertainty = getValue() * uncertainty;
        }
        return uncertainty;
    }

    @Override
    protected double divUncertainty(Value divisor, double quotient) {
        double uncertainty = divisor.getUncertainty();
        if (!divisor.isExact()) {
            uncertainty = uncertainty * uncertainty * quotient;
        }
        return uncertainty;
    }

    @Override
    protected double reciprocalUncertainty(double value) {
        double uncertainty = getUncertainty();
        return uncertainty;
    }

    @Override
    protected double logUncertainty(double exponent) {
        double uncertainty = getUncertainty();
        return uncertainty;
    }

    @Override
    protected double expUncertainty(double power) {
        double uncertainty = getUncertainty();
        return uncertainty;
    }

    @Override
    protected double absUncertainty(double value) {
        double uncertainty = getUncertainty();
        return uncertainty;
    }

    @Override
    protected Value createValue(double value) {
        ValueFactory factory = getValueFactory();
        return factory.create(value, MAX_VALUE, NaN, MAX_VALUE, factory);
    }

}
