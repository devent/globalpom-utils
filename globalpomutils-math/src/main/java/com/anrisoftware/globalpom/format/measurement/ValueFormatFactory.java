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
package com.anrisoftware.globalpom.format.measurement;

import java.util.Locale;

import com.anrisoftware.globalpom.measurement.ValueFactory;

/**
 * Factory to create a new value format.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
public interface ValueFormatFactory {

    /**
     * Create value format that uses the default decimal format.
     *
     * @param valueFactory
     *            the {@link ValueFactory} to create the value.
     *
     * @return the {@link ValueFormat}.
     */
    ValueFormat create(ValueFactory valueFactory);

    /**
     * Create value format with the specified locale.
     *
     * @param locale
     *            the {@link Locale}.
     *
     * @param valueFactory
     *            the {@link ValueFactory} to create the value.
     *
     * @return the {@link ValueFormat}.
     */
    ValueFormat create(Locale locale, ValueFactory valueFactory);

}
