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
import static com.anrisoftware.globalpom.measurement.ValueFactory.VALUE_FACTORY;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Value that calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class StandardValue extends AbstractValue {

    private static final String VALUE = "value";

    @Inject
    private ExactStandardValueFactory exactFactory;

    /**
     * @see StandardValueFactory#create(double, int, double, int, ValueFactory)
     */
    @AssistedInject
    StandardValue(@Assisted(VALUE) double value,
            @Assisted(SIGNIFICANT) int significant,
            @Assisted(UNCERTAINTY) double uncertainty,
            @Assisted(DECIMAL) int decimal,
            @Assisted(VALUE_FACTORY) ValueFactory valueFactory) {
        super(value, significant, uncertainty, decimal, valueFactory);
    }

    /**
     * @see StandardValueFactory#create(double, int, double, int)
     */
    @AssistedInject
    StandardValue(StandardValueFactory valueFactory,
            @Assisted(VALUE) double value,
            @Assisted(SIGNIFICANT) int significant,
            @Assisted(UNCERTAINTY) double uncertainty,
            @Assisted(DECIMAL) int decimal) {
        super(value, significant, uncertainty, decimal, valueFactory);
    }

    @Override
    protected double addUncertainty(Value addend, double sum) {
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
    protected double mulUncertainty(Value factor, double product) {
        double sa = getUncertainty();
        double sb = factor.getUncertainty();
        double a = getValue();
        if (isExact()) {
            return a * sb;
        }
        if (factor.isExact()) {
            return sa * factor.getValue();
        }
        double b = factor.getValue();
        return StandardValueMath.mulUncertaintly(a, sa, b, sb, product);
    }

    @Override
    protected double divUncertainty(Value divisor, double quotient) {
        double sa = getUncertainty();
        double sb = divisor.getUncertainty();
        double a = getValue();
        if (isExact()) {
            return a / sb;
        }
        if (divisor.isExact()) {
            return sa / divisor.getValue();
        }
        double b = divisor.getValue();
        return StandardValueMath.mulUncertaintly(a, sa, b, sb, quotient);
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

    @Override
    protected Value createValue(double value) {
        return exactFactory.create(value, getValueFactory());
    }
}
