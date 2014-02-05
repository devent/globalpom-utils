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
public final class StandardMeasure<UnitType extends Quantity> extends
        AbstractMeasure<UnitType> {

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

}
