/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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

import javax.measure.unit.Unit;

/**
 * Factory to create measurement.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface MeasureFactory {

    /**
     * Creates the measurement with the specified physical unit and value.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @param unit
     *            the physical {@link Unit}.
     * 
     * @return the {@link Measure}.
     */
    @SuppressWarnings("rawtypes")
    Measure create(Value value, Unit unit);

    /**
     * Creates the measurement with the specified physical unit and value.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @param unit
     *            the physical {@link Unit}.
     * 
     * @param valueFactory
     *            the {@link ValueFactory}.
     * 
     * @return the {@link Measure}.
     */
    @SuppressWarnings("rawtypes")
    Measure create(Value value, Unit unit, ValueFactory valueFactory);

    /**
     * Creates the measurement from the value and unit of the specified measure.
     * 
     * @param measure
     *            the {@link Measure}.
     * 
     * @return the {@link Measure}.
     */
    @SuppressWarnings("rawtypes")
    Measure create(Measure measure);

    /**
     * Creates the measurement from the value and unit of the specified measure.
     * 
     * @param measure
     *            the {@link Measure}.
     * 
     * @param valueFactory
     *            the {@link ValueFactory}.
     * 
     * @return the {@link Measure}.
     */
    @SuppressWarnings("rawtypes")
    Measure create(Measure measure, ValueFactory valueFactory);

}
