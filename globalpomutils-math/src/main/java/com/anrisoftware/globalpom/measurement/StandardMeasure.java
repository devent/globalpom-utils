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

import java.io.Serializable;

import javax.inject.Inject;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

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
@SuppressWarnings("serial")
public final class StandardMeasure<UnitType extends Quantity> extends
        AbstractMeasure<UnitType> implements Serializable {

    /**
     * @see StandardMeasureFactory#create(Unit, Value)
     */
    @AssistedInject
    StandardMeasure(StandardMeasureFactory measureFactory,
            StandardValueFactory valueFactory,
            @SuppressWarnings("rawtypes") @Assisted Unit unit,
            @Assisted Value value) {
        this(unit, value, valueFactory, measureFactory);
    }

    /**
     * @see StandardMeasureFactory#create(Unit, Value, ValueFactory)
     */
    @SuppressWarnings("unchecked")
    @AssistedInject
    StandardMeasure(@SuppressWarnings("rawtypes") @Assisted Unit unit,
            @Assisted Value value, @Assisted ValueFactory valueFactory,
            StandardMeasureFactory measureFactory) {
        super(unit, value, valueFactory, measureFactory);
    }

    /**
     * @see StandardMeasureFactory#create(Measure)
     */
    @SuppressWarnings("unchecked")
    @AssistedInject
    StandardMeasure(StandardMeasureFactory measureFactory,
            StandardValueFactory valueFactory,
            @SuppressWarnings("rawtypes") @Assisted Measure measure) {
        super(measure, valueFactory, measureFactory);
    }

    /**
     * @see StandardMeasureFactory#create(Measure, ValueFactory)
     */
    @SuppressWarnings("unchecked")
    @AssistedInject
    StandardMeasure(@SuppressWarnings("rawtypes") @Assisted Measure measure,
            @Assisted ValueFactory valueFactory,
            StandardMeasureFactory measureFactory) {
        super(measure, valueFactory, measureFactory);
    }

    @Inject
    public void setValueFactory(StandardValueFactory factory) {
        super.setValueFactory(factory);
    }

    @Inject
    public void setMeasureFactory(StandardMeasureFactory factory) {
        super.setMeasureFactory(factory);
    }
}
