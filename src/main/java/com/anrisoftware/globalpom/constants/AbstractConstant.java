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
package com.anrisoftware.globalpom.constants;

import java.text.DecimalFormat;

import javax.inject.Inject;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.measurement.Value;

/**
 * Physical constant.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public abstract class AbstractConstant<Q extends Quantity> implements
        Constant<Q> {

    private static final DecimalFormat VALUE_FORMAT = new DecimalFormat(
            "#.#########");

    private final Value value;

    private final Unit<Q> unit;

    @Inject
    private ValueToString toString;

    /**
     * Sets the specified value and unit of the constant.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @param unit
     *            the {@link Unit}.
     */
    protected AbstractConstant(Value value, Unit<Q> unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public Unit<Q> getUnit() {
        return unit;
    }

    @Override
    public double getValue() {
        return value.getValue();
    }

    @Override
    public Value getRoundedValue() {
        return value.getRoundedValue();
    }

    @Override
    public Value roundedValue(int sig, int dec) {
        return value.roundedValue(sig, dec);
    }

    @Override
    public int getSignificant() {
        return value.getSignificant();
    }

    @Override
    public int getDecimal() {
        return value.getDecimal();
    }

    @Override
    public double getUncertainty() {
        return value.getUncertainty();
    }

    @Override
    public boolean isExact() {
        return value.isExact();
    }

    @Override
    public Value add(Value addend) {
        return value.add(addend);
    }

    @Override
    public Value add(double addend) {
        return value.add(addend);
    }

    @Override
    public Value sub(Value subtrahend) {
        return value.sub(subtrahend);
    }

    @Override
    public Value sub(double subtrahend) {
        return value.sub(subtrahend);
    }

    @Override
    public Value mul(Value factor) {
        return value.mul(factor);
    }

    @Override
    public Value mul(double factor) {
        return value.mul(factor);
    }

    @Override
    public Value div(Value divisor) {
        return value.div(divisor);
    }

    @Override
    public Value div(double divisor) {
        return value.div(divisor);
    }

    @Override
    public Value reciprocal() {
        return value.reciprocal();
    }

    @Override
    public Value log() {
        return value.log();
    }

    @Override
    public Value exp() {
        return value.exp();
    }

    @Override
    public Value abs() {
        return value.abs();
    }

    @Override
    public int compare(Value v) {
        return value.compare(v);
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(o);
    }

    @Override
    public int compare(Value v, double dev) {
        return value.compare(v, dev);
    }

    @Override
    public int compare(Number v) {
        return value.compare(v);
    }

    @Override
    public int compare(Number v, double dev) {
        return value.compare(v, dev);
    }

    @Override
    public boolean equals(Object obj) {
        return equals(obj, 3.0);
    }

    @Override
    public boolean equals(Object obj, double dev) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Constant)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Constant<Q> rhs = (Constant<Q>) obj;
        return getUnit().equals(rhs.getUnit()) ? value.equals(obj, dev) : false;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        toString.format(buff, this, VALUE_FORMAT);
        return new ToStringBuilder(this).append(buff.toString()).build();
    }
}
