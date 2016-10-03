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
package com.anrisoftware.globalpom.constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.format.measurement.MeasureFormat;
import com.anrisoftware.globalpom.format.measurement.MeasureFormatFactory;
import com.anrisoftware.globalpom.measurement.StandardMeasureFactory;
import com.anrisoftware.globalpom.measurement.StandardValueFactory;
import com.google.inject.Provider;

/**
 * Provides the physical constants that calculates error propagation using
 * standard uncertainty.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardConstantsProvider implements Provider<Constants> {

    @Inject
    private MeasureFormatFactory formatFactory;

    @Inject
    private StandardMeasureFactory measureFactory;

    @Inject
    private StandardValueFactory valueFactory;

    private Constants constants;

    @Inject
    void setConstantsFactory(ConstantsFactory constantsFactory) {
        MeasureFormat format;
        format = formatFactory.create(valueFactory, measureFactory);
        this.constants = constantsFactory.create(format);
    }

    @Override
    public Constants get() {
        return constants;
    }

}
