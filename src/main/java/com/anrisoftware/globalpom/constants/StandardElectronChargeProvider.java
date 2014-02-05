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
package com.anrisoftware.globalpom.constants;

import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.Measure;
import com.google.inject.Provider;

/**
 * Provides the physical constants of the electron charge {@code e} that
 * calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardElectronChargeProvider implements Provider<Measure<?>> {

    private static final String NAME = "electron_charge";

    private Measure<?> constant;

    @Inject
    void setStandardConstantsProvider(StandardConstantsProvider provider)
            throws ParseException {
        this.constant = getConstant(provider.get());
    }

    @Override
    public Measure<?> get() {
        return constant;
    }

    private Measure<?> getConstant(Constants constants) throws ParseException {
        return constants.getConstant(NAME);
    }

}
