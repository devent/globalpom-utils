/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import java.text.ParseException;

import javax.inject.Inject;

import com.anrisoftware.globalpom.format.measurement.MeasureFormat;
import com.anrisoftware.globalpom.measurement.Measure;
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
