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

import java.math.BigInteger;

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
    public static final String MANTISSA = "mantissa";
    public static final String ORDER = "order";

    /**
     * Creates a new value.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) long mantissa, @Assisted(ORDER) int order,
            @Assisted(SIGNIFICANT) int sig, @Assisted(DECIMAL) int dec);

    /**
     * Creates a new value.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec);

    /**
     * Creates a new value with the specified value factory.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param valueFactory
     *            the {@link ValueFactory}.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) long mantissa, @Assisted(ORDER) int order,
            @Assisted(SIGNIFICANT) int sig, @Assisted(DECIMAL) int dec,
            ValueFactory valueFactory);

    /**
     * Creates a new value with the specified value factory.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param valueFactory
     *            the {@link ValueFactory}.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, ValueFactory valueFactory);

    /**
     * Creates a new value with the specified uncertainty.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param unc
     *            the uncertainty {@link Value}.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) long mantissa, @Assisted(ORDER) int order,
            @Assisted(SIGNIFICANT) int sig, @Assisted(DECIMAL) int dec,
            double unc);

    /**
     * Creates a new value with the specified uncertainty.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param unc
     *            the uncertainty {@link Value}.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, double unc);

    /**
     * Creates a new value with the specified uncertainty and value factory.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param unc
     *            the uncertainty {@link Value}.
     *
     * @param valueFactory
     *            the {@link ValueFactory}.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) long mantissa, @Assisted(ORDER) int order,
            @Assisted(SIGNIFICANT) int sig, @Assisted(DECIMAL) int dec,
            double unc, ValueFactory valueFactory);

    /**
     * Creates a new value with the specified uncertainty and value factory.
     *
     * @param mantissa
     *            the significant digits of the value.
     *
     * @param order
     *            the order of the value.
     *
     * @param sig
     *            the significant figures of the value.
     *
     * @param dec
     *            the least significant decimal.
     *
     * @param unc
     *            the uncertainty {@link Value}.
     *
     * @param valueFactory
     *            the {@link ValueFactory}.
     *
     * @return the {@link Value}.
     *
     * @since 2.4
     */
    Value create(@Assisted(MANTISSA) BigInteger mantissa,
            @Assisted(ORDER) int order, @Assisted(SIGNIFICANT) int sig,
            @Assisted(DECIMAL) int dec, double unc, ValueFactory valueFactory);

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
     * Creates a new value from the specified value with the specified value
     * factory.
     *
     * @param value
     *            the {@link Value}.
     *
     * @param valueFactory
     *            the {@link ValueFactory} to create the value for calculations.
     *
     * @return the {@link Value}.
     */
    Value create(Value value, ValueFactory valueFactory);

}
