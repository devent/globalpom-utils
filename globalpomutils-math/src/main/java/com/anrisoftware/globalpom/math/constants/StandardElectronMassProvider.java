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
package com.anrisoftware.globalpom.math.constants;

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

import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.quantity.Mass;

import com.anrisoftware.globalpom.math.measurement.Measure;
import com.google.inject.Provider;

/**
 * Provides the physical constants of the electron mass {@code me} that
 * calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardElectronMassProvider implements Provider<Measure<Mass>> {

    private static final String NAME = "electron_mass";

    private Measure<Mass> constant;

    @Inject
    void setStandardConstantsProvider(StandardConstantsProvider provider)
            throws ParseException {
        this.constant = getConstant(provider.get());
    }

    @Override
    public Measure<Mass> get() {
        return constant;
    }

    @SuppressWarnings("unchecked")
    private Measure<Mass> getConstant(Constants constants)
            throws ParseException {
        return (Measure<Mass>) constants.getConstant(NAME);
    }

}
