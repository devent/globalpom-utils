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

import java.io.Serializable;
import java.math.BigInteger;

import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Defines a measurement with a physical unit.
 *
 * @param <UnitType>
 *            the {@link Quantity} of the unit.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
@SuppressWarnings({ "serial", "unchecked" })
public abstract class AbstractMeasure<UnitType extends Quantity> implements
        Measure<UnitType>, Serializable {

    private final Unit<UnitType> unit;

    private final Value value;

    private transient ValueFactory valueFactory;

    private transient MeasureFactory measureFactory;

    /**
     * @see MeasureFactory#create(Measure, ValueFactory)
     */
    protected AbstractMeasure(Measure<UnitType> measure,
            ValueFactory valueFactory, MeasureFactory measureFactory) {
        this(measure.getUnit(), measure.getMeasureValue(), valueFactory,
                measureFactory);
    }

    /**
     * @see MeasureFactory#create(Value, Unit, ValueFactory)
     */
    protected AbstractMeasure(Unit<UnitType> unit, Value value,
            ValueFactory valueFactory, MeasureFactory measureFactory) {
        this.unit = unit;
        this.valueFactory = valueFactory;
        this.measureFactory = measureFactory;
        this.value = valueFactory.create(value, valueFactory);
    }

    public void setValueFactory(ValueFactory factory) {
        this.valueFactory = factory;
    }

    public void setMeasureFactory(MeasureFactory factory) {
        this.measureFactory = factory;
    }

    @Override
    public Unit<UnitType> getUnit() {
        return unit;
    }

    @Override
    public Value getMeasureValue() {
        return value;
    }

    @Override
    public BigInteger getMantissa() {
        return value.getMantissa();
    }

    @Override
    public int getOrder() {
        return value.getOrder();
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
    public double getRoundedUncertainty() {
        return value.getRoundedUncertainty();
    }

    @Override
    public boolean isExact() {
        return value.isExact();
    }

    @Override
    public double getValue() {
        return value.getValue();
    }

    @Override
    public double getRoundedValue() {
        return value.getRoundedValue();
    }

    @Override
    public double roundedValue(int sig, int dec) {
        return value.roundedValue(sig, dec);
    }

    @Override
    public Measure<UnitType> getMinValue() {
        Value value = this.value.getMinValue();
        return measureFactory.create(value, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> minValue(double deviation) {
        Value value = this.value.minValue(deviation);
        return measureFactory.create(value, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> getMaxValue() {
        Value value = this.value.getMaxValue();
        return measureFactory.create(value, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> maxValue(double deviation) {
        Value value = this.value.maxValue(deviation);
        return measureFactory.create(value, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> valueOf(BigInteger mantissa, int order, int sig,
            int dec, double unc) {
        Value value = this.value.valueOf(mantissa, order, sig, dec, unc);
        return measureFactory.create(value, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> valueOf(double value, int sig, int dec, double unc) {
        Value vvalue = this.value.valueOf(value, sig, dec, unc);
        return measureFactory.create(vvalue, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> valueOf(double value, int dec, double unc) {
        Value vvalue = this.value.valueOf(value, dec, unc);
        return measureFactory.create(vvalue, getUnit(), valueFactory);
    }

    @Override
    public Measure<UnitType> add(Value addend) {
        Value value = this.value.add(addend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> add(double addend) {
        Value value = this.value.add(addend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> plus(Value addend) {
        return add(addend);
    }

    @Override
    public Measure<UnitType> plus(double addend) {
        return add(addend);
    }

    @Override
    public Measure<UnitType> sub(Value subtrahend) {
        Value value = this.value.sub(subtrahend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> sub(double subtrahend) {
        Value value = this.value.sub(subtrahend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> minus(Value subtrahend) {
        return sub(subtrahend);
    }

    @Override
    public Measure<UnitType> minus(double subtrahend) {
        return sub(subtrahend);
    }

    @Override
    public Measure<UnitType> mul(Value factor) {
        Value value = this.value.mul(factor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> mul(double factor) {
        Value value = this.value.mul(factor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> multiply(Value factor) {
        return mul(factor);
    }

    @Override
    public Measure<UnitType> multiply(double factor) {
        return mul(factor);
    }

    @Override
    public Measure<UnitType> div(Value divisor) {
        Value value = this.value.div(divisor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> div(double divisor) {
        Value value = this.value.div(divisor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> divNum(Value numerator) {
        Value value = this.value.divNum(numerator);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> divNum(double numerator) {
        Value value = this.value.divNum(numerator);
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> log() {
        Value value = this.value.log();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> exp() {
        Value value = this.value.exp();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> reciprocal() {
        Value value = this.value.reciprocal();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> abs() {
        Value value = this.value.abs();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> square() {
        Value value = this.value.square();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public Measure<UnitType> cube() {
        Value value = this.value.cube();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public int compareTo(Value v) {
        return value.compareTo(v);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Measure)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Measure rhs = (Measure) obj;
        if (getUnit().equals(rhs.getUnit())) {
            return value.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public boolean isConsistent(Value rhs) {
        @SuppressWarnings("rawtypes")
        Measure mrhs = (Measure) rhs;
        if (getUnit().equals(mrhs.getUnit())) {
            return value.isConsistent(rhs);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getUnit()).append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(value.toString())
                .append(getUnit()).toString();
    }

}
