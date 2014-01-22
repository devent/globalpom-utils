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

import javax.inject.Inject;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Measurement that calculates error propagation using simpler average errors.
 * 
 * @param <UnitType>
 *            the {@link Quantity} of the unit.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class StandardMeasure<UnitType extends Quantity> implements
        Measure<UnitType> {

    private final Unit<UnitType> unit;

    private final Value value;

    private final ValueFactory valueFactory;

    @Inject
    private StandardMeasureFactory measureFactory;

    /**
     * @see StandardMeasureFactory#create(Unit, Value)
     */
    @AssistedInject
    StandardMeasure(StandardValueFactory valueFactory,
            @SuppressWarnings("rawtypes") @Assisted Unit unit,
            @Assisted Value value) {
        this(unit, value, valueFactory);
    }

    /**
     * @see StandardMeasureFactory#create(Unit, Value, ValueFactory)
     */
    @SuppressWarnings("unchecked")
    @AssistedInject
    StandardMeasure(@SuppressWarnings("rawtypes") @Assisted Unit unit,
            @Assisted Value value, @Assisted ValueFactory valueFactory) {
        this.unit = unit;
        this.valueFactory = valueFactory;
        this.value = valueFactory.create(value.getValue(),
                value.getSignificant(), value.getUncertainty(),
                value.getDecimal(), valueFactory);
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
    public double getValue() {
        return value.getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> getRoundedValue() {
        Value value = this.value.getRoundedValue();
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> roundedValue(int sig, int dec) {
        Value value = this.value.roundedValue(sig, dec);
        return measureFactory.create(value, unit, valueFactory);
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
    public double getMinValue() {
        return value.getMinValue();
    }

    @Override
    public double minValue(double deviation) {
        return value.minValue(deviation);
    }

    @Override
    public double getMaxValue() {
        return value.getMaxValue();
    }

    @Override
    public double maxValue(double deviation) {
        return value.maxValue(deviation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> add(Value addend) {
        Value value = this.value.add(addend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> add(double addend) {
        Value value = this.value.add(addend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> sub(Value subtrahend) {
        Value value = this.value.sub(subtrahend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> sub(double subtrahend) {
        Value value = this.value.sub(subtrahend);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> mul(Value factor) {
        Value value = this.value.mul(factor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> mul(double factor) {
        Value value = this.value.mul(factor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> div(Value divisor) {
        Value value = this.value.div(divisor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> div(double divisor) {
        Value value = this.value.div(divisor);
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> log() {
        Value value = this.value.log();
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> exp() {
        Value value = this.value.exp();
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> reciprocal() {
        Value value = this.value.reciprocal();
        return measureFactory.create(value, unit, valueFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Measure<UnitType> abs() {
        Value value = this.value.abs();
        return measureFactory.create(value, unit, valueFactory);
    }

    @Override
    public int compare(Value value) {
        return this.value.compare(value);
    }

    @Override
    public int compare(Value value, double dev) {
        return this.value.compare(value, dev);
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(o);
    }

    @Override
    public int compare(Number value) {
        return this.value.compare(value);
    }

    @Override
    public int compare(Number value, double dev) {
        return this.value.compare(value, dev);
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
        if (!(obj instanceof Measure)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Measure rhs = (Measure) obj;
        if (getMeasureValue().equals(rhs.getMeasureValue(), dev)) {
            return getUnit().equals(rhs.getUnit());
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
        return new ToStringBuilder(this).append(value).append(getUnit())
                .toString();
    }

}
