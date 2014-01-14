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

import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

/**
 * Defines a measurement with a physical unit.
 * 
 * @param <UnitType>
 *            the {@link Quantity} of the unit.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface Measure<UnitType extends Quantity> extends Value {

    /**
     * Returns the unit of the gauge.
     * 
     * @return the {@link Unit}.
     */
    Unit<UnitType> getUnit();

    /**
     * Returns the measured value.
     * 
     * @return the {@link Value}.
     */
    Value getMeasureValue();

    @Override
    Measure<UnitType> getRoundedValue();

    @Override
    Measure<UnitType> roundedValue(int sig, int dec);

    @Override
    Measure<UnitType> add(Value addend);

    @Override
    Measure<UnitType> add(double addend);

    @Override
    Measure<UnitType> sub(Value subtrahend);

    @Override
    Measure<UnitType> sub(double subtrahend);

    @Override
    Measure<UnitType> mul(Value factor);

    @Override
    Measure<UnitType> mul(double factor);

    @Override
    Measure<UnitType> div(Value divisor);

    @Override
    Measure<UnitType> div(double divisor);

    @Override
    Measure<UnitType> reciprocal();

    @Override
    Measure<UnitType> log();

    @Override
    Measure<UnitType> exp();

}
