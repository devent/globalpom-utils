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

import static com.anrisoftware.globalpom.charset.SerializableCharset.decorateSerializableCharset;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.charset.SerializableCharset;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Locale as used in POSIX platforms. Contains the language, country and
 * character set.
 *
 * @see http://en.wikipedia.org/wiki/Locale
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.6
 */
@SuppressWarnings("serial")
public final class PosixLocale implements Serializable {

    private final Locale locale;

    private final SerializableCharset charset;

    /**
     * Sets the locale with the default character set.
     *
     * @param locale
     *            the {@link Locale} locale.
     *
     * @see Charset#defaultCharset()
     *
     * @throws NullPointerException
     *             if the specified locale is <code>null</code>.
     */
    @AssistedInject
    public PosixLocale(@Assisted Locale locale) {
        this(locale, Charset.defaultCharset());
    }

    /**
     * Sets the locale and the character set.
     *
     * @param locale
     *            the {@link Locale} locale.
     *
     * @param charset
     *            the {@link Charset} character set.
     *
     * @throws NullPointerException
     *             if the specified locale or character set is
     *             <code>null.</code>
     */
    @AssistedInject
    public PosixLocale(@Assisted Locale locale, @Assisted Charset charset) {
        notNull(locale);
        notNull(charset);
        this.locale = locale;
        this.charset = decorateSerializableCharset(charset);
    }

    /**
     * Returns the locale.
     *
     * @return the {@link Locale}.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the character set.
     *
     * @return the {@link Charset}.
     */
    public Charset getCharset() {
        return charset.getCharset();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PosixLocale)) {
            return false;
        }
        PosixLocale rhs = (PosixLocale) obj;
        return new EqualsBuilder().append(getLocale(), rhs.getLocale())
                .append(getCharset(), rhs.getCharset()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getLocale()).append(getCharset())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(locale).append(charset)
                .toString();
    }
}
