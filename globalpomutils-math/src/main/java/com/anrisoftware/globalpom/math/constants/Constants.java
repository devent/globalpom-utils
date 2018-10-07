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

import com.anrisoftware.globalpom.math.format.measurement.MeasureFormat;
import com.anrisoftware.globalpom.math.measurement.Measure;
import com.google.inject.assistedinject.Assisted;

/**
 * Provides physical constants.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class Constants {

    private final MeasureFormat format;

    @Inject
    private ConstantsResourceProvider resource;

    /**
     * Sets the constant format to parse physical constants.
     * 
     * @param format
     *            the {@link MeasureFormat}.
     */
    @Inject
    Constants(@Assisted MeasureFormat format) {
        this.format = format;
    }

    /**
     * Returns the constant with the specified name.
     * 
     * @param name
     *            the constant name.
     * 
     * @return the physical {@link Measure} constant.
     * 
     * @throws ParseException
     *             if there was an error parse the physical constant.
     */
    public Measure<?> getConstant(String name) throws ParseException {
        return resource.get().getTypedProperty(name, format);
    }
}
