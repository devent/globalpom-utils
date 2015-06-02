/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.posixlocale;

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
