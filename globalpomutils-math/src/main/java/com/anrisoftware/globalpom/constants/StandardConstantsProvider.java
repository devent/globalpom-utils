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
