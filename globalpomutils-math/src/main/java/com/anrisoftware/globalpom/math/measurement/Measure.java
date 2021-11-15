/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
