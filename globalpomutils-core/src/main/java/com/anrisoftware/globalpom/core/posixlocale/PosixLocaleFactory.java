/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.posixlocale;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Factory to create a new POSIX locale.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.6
 */
public interface PosixLocaleFactory {

    /**
     * Creates the POSIX locale with the specified locale with the default
     * character set.
     *
     * @param locale
     *            the {@link Locale} locale.
     *
     * @see Charset#defaultCharset()
     *
     * @return the {@link PosixLocale}.
     */
    PosixLocale create(Locale locale);

    /**
     * Creates the POSIX locale with the specified locale with the specified
     * character set.
     *
     * @param locale
     *            the {@link Locale} locale.
     *
     * @param charset
     *            the {@link Charset} character set.
     *
     * @return the {@link PosixLocale}.
     */
    PosixLocale create(Locale locale, Charset charset);
}
