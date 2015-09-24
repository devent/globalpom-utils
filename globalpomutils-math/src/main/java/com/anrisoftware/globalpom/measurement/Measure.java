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

import java.math.BigInteger;

import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

/**
 * Defines a measurement with a physical unit.
 *
 * @param <UnitType>
 *            the {@link Quantity} of the unit.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
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
    Measure<UnitType> valueOf(BigInteger mantissa, int order, int sig, int dec,
            double unc);

    @Override
    Measure<UnitType> valueOf(double value, int sig, int dec, double unc);

    @Override
    Measure<UnitType> valueOf(double value, int dec, double unc);

    @Override
    Measure<UnitType> getMinValue();

    @Override
    Measure<UnitType> minValue(double deviation);

    @Override
    Measure<UnitType> getMaxValue();

    @Override
    Measure<UnitType> maxValue(double deviation);

    @Override
    Measure<UnitType> add(Value addend);

    @Override
    Measure<UnitType> add(double addend);

    @Override
    Measure<UnitType> plus(Value addend);

    @Override
    Measure<UnitType> plus(double addend);

    @Override
    Measure<UnitType> sub(Value subtrahend);

    @Override
    Measure<UnitType> sub(double subtrahend);

    @Override
    Measure<UnitType> minus(Value subtrahend);

    @Override
    Measure<UnitType> minus(double subtrahend);

    @Override
    Measure<UnitType> mul(Value factor);

    @Override
    Measure<UnitType> mul(double factor);

    @Override
    Measure<UnitType> multiply(Value factor);

    @Override
    Measure<UnitType> multiply(double factor);

    @Override
    Measure<UnitType> div(Value divisor);

    @Override
    Measure<UnitType> div(double divisor);

    @Override
    Measure<UnitType> divNum(Value numerator);

    @Override
    Measure<UnitType> divNum(double numerator);

    @Override
    Measure<UnitType> reciprocal();

    @Override
    Measure<UnitType> log();

    @Override
    Measure<UnitType> exp();

    @Override
    Measure<UnitType> abs();

    @Override
    Measure<UnitType> square();

    @Override
    Measure<UnitType> cube();
}
