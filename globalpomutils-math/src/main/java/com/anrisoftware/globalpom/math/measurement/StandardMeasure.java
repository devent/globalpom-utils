/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.io.Serializable;

import jakarta.inject.Inject;
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
