/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.measurement;

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
