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
package com.anrisoftware.globalpom.format.measurement;

import java.util.Locale;

import com.anrisoftware.globalpom.measurement.MeasureFactory;
import com.anrisoftware.globalpom.measurement.ValueFactory;

/**
 * Factory to create a new measurement format.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
public interface MeasureFormatFactory {

    /**
     * Create value format that uses the default decimal format.
     *
     * @param valueFactory
     *            the {@link ValueFactory} to create the value.
     *
     * @param measureFactory
     *            the {@link MeasureFactory} to create the measure.
     *
     * @return the {@link ValueFormat}.
     */
    MeasureFormat create(ValueFactory valueFactory,
            MeasureFactory measureFactory);

    /**
     * Create value format with the specified locale.
     *
     * @param locale
     *            the {@link Locale}.
     *
     * @param valueFactory
     *            the {@link ValueFactory} to create the value.
     *
     * @param measureFactory
     *            the {@link MeasureFactory} to create the measure.
     *
     * @return the {@link ValueFormat}.
     */
    MeasureFormat create(Locale locale, ValueFactory valueFactory,
            MeasureFactory measureFactory);

}
