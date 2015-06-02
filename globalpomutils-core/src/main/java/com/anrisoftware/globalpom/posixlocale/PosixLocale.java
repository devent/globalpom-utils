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

    /**
     * @see Locale#ENGLISH
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale ENGLISH = new PosixLocale(Locale.ENGLISH);

    /**
     * @see Locale#FRENCH
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale FRENCH = new PosixLocale(Locale.FRENCH);

    /**
     * @see Locale#GERMAN
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale GERMAN = new PosixLocale(Locale.GERMAN);

    /**
     * @see Locale#ITALIAN
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale ITALIAN = new PosixLocale(Locale.ITALIAN);

    /**
     * @see Locale#JAPANESE
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale JAPANESE = new PosixLocale(Locale.JAPANESE);

    /**
     * @see Locale#KOREAN
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale KOREAN = new PosixLocale(Locale.KOREAN);

    /**
     * @see Locale#CHINESE
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale CHINESE = new PosixLocale(Locale.CHINESE);

    /**
     * @see Locale#SIMPLIFIED_CHINESE
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale SIMPLIFIED_CHINESE = new PosixLocale(
            Locale.SIMPLIFIED_CHINESE);

    /**
     * @see Locale#TRADITIONAL_CHINESE
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale TRADITIONAL_CHINESE = new PosixLocale(
            Locale.TRADITIONAL_CHINESE);

    /**
     * @see Locale#FRANCE
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale FRANCE = new PosixLocale(Locale.FRANCE);

    /**
     * @see Locale#GERMANY
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale GERMANY = new PosixLocale(Locale.GERMANY);

    /**
     * @see Locale#ITALY
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale ITALY = new PosixLocale(Locale.ITALY);

    /**
     * @see Locale#JAPAN
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale JAPAN = new PosixLocale(Locale.JAPAN);

    /**
     * @see Locale#KOREA
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale KOREA = new PosixLocale(Locale.KOREA);

    /**
     * @see Locale#CHINA
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale CHINA = new PosixLocale(Locale.CHINA);

    /**
     * @see Locale#PRC
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale PRC = new PosixLocale(Locale.PRC);

    /**
     * @see Locale#TAIWAN
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale TAIWAN = new PosixLocale(Locale.TAIWAN);

    /**
     * @see Locale#UK
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale UK = new PosixLocale(Locale.UK);

    /**
     * @see Locale#US
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale US = new PosixLocale(Locale.US);

    /**
     * @see Locale#CANADA
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale CANADA = new PosixLocale(Locale.CANADA);

    /**
     * @see Locale#CANADA_FRENCH
     * @see Charset#defaultCharset()
     */
    static public final PosixLocale CANADA_FRENCH = new PosixLocale(
            Locale.CANADA_FRENCH);

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
