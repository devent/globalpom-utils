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
package com.anrisoftware.globalpom.measurement;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create a new value with an uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface ValueFactory {

    public static final String DECIMAL = "decimal";
    public static final String UNCERTAINTY = "uncertainty";
    public static final String SIGNIFICANT = "significant";
    public static final String VALUE = "value";
    public static final String VALUE_FACTORY = "valueFactory";

    /**
     * Creates a new value with an uncertainty.
     * 
     * @param value
     *            the value.
     * 
     * @param significant
     *            the significant figures of the value.
     * 
     * @param uncertainty
     *            the uncertainty of the value.
     * 
     * @param decimal
     *            the least significant decimal.
     * 
     * @return the {@link Value}.
     */
    Value create(@Assisted(VALUE) double value,
            @Assisted(SIGNIFICANT) int significant,
            @Assisted(UNCERTAINTY) double uncertainty,
            @Assisted(DECIMAL) int decimal);

    /**
     * @param valueFactory
     *            the {@link ValueFactory} to create the value for calculations.
     * 
     * @see ValueFactory#create(double, int, double, int)
     */
    Value create(@Assisted(VALUE) double value,
            @Assisted(SIGNIFICANT) int significant,
            @Assisted(UNCERTAINTY) double uncertainty,
            @Assisted(DECIMAL) int decimal,
            @Assisted(VALUE_FACTORY) ValueFactory valueFactory);

    /**
     * Creates a new value from the specified value.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @return the {@link Value}.
     */
    Value create(Value value);

    /**
     * Creates a new value from the specified value.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @param valueFactory
     *            the {@link ValueFactory} to create the value for calculations.
     * 
     * @return the {@link Value}.
     */
    Value create(Value value, @Assisted(VALUE_FACTORY) ValueFactory valueFactory);

}
