/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import static com.anrisoftware.globalpom.core.charset.SerializableCharset.decorateSerializableCharset;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.core.charset.SerializableCharset;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Locale as used in POSIX platforms. Contains the language, country and
 * character set.
 *
 * @see {@link http://en.wikipedia.org/wiki/Locale}
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
    public static final PosixLocale ENGLISH = new PosixLocale(Locale.ENGLISH);

    /**
     * @see Locale#FRENCH
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale FRENCH = new PosixLocale(Locale.FRENCH);

    /**
     * @see Locale#GERMAN
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale GERMAN = new PosixLocale(Locale.GERMAN);

    /**
     * @see Locale#ITALIAN
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale ITALIAN = new PosixLocale(Locale.ITALIAN);

    /**
     * @see Locale#JAPANESE
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale JAPANESE = new PosixLocale(Locale.JAPANESE);

    /**
     * @see Locale#KOREAN
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale KOREAN = new PosixLocale(Locale.KOREAN);

    /**
     * @see Locale#CHINESE
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale CHINESE = new PosixLocale(Locale.CHINESE);

    /**
     * @see Locale#SIMPLIFIED_CHINESE
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale SIMPLIFIED_CHINESE = new PosixLocale(Locale.SIMPLIFIED_CHINESE);

    /**
     * @see Locale#TRADITIONAL_CHINESE
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale TRADITIONAL_CHINESE = new PosixLocale(Locale.TRADITIONAL_CHINESE);

    /**
     * @see Locale#FRANCE
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale FRANCE = new PosixLocale(Locale.FRANCE);

    /**
     * @see Locale#GERMANY
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale GERMANY = new PosixLocale(Locale.GERMANY);

    /**
     * @see Locale#ITALY
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale ITALY = new PosixLocale(Locale.ITALY);

    /**
     * @see Locale#JAPAN
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale JAPAN = new PosixLocale(Locale.JAPAN);

    /**
     * @see Locale#KOREA
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale KOREA = new PosixLocale(Locale.KOREA);

    /**
     * @see Locale#CHINA
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale CHINA = new PosixLocale(Locale.CHINA);

    /**
     * @see Locale#PRC
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale PRC = new PosixLocale(Locale.PRC);

    /**
     * @see Locale#TAIWAN
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale TAIWAN = new PosixLocale(Locale.TAIWAN);

    /**
     * @see Locale#UK
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale UK = new PosixLocale(Locale.UK);

    /**
     * @see Locale#US
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale US = new PosixLocale(Locale.US);

    /**
     * @see Locale#CANADA
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale CANADA = new PosixLocale(Locale.CANADA);

    /**
     * @see Locale#CANADA_FRENCH
     * @see Charset#defaultCharset()
     */
    public static final PosixLocale CANADA_FRENCH = new PosixLocale(Locale.CANADA_FRENCH);

    private final Locale locale;

    private final SerializableCharset charset;

    /**
     * Sets the locale with the default character set.
     *
     * @param locale the {@link Locale} locale.
     *
     * @see Charset#defaultCharset()
     *
     * @throws NullPointerException if the specified locale is <code>null</code>.
     */
    @AssistedInject
    public PosixLocale(@Assisted Locale locale) {
        this(locale, Charset.defaultCharset());
    }

    /**
     * Sets the locale and the character set.
     *
     * @param locale  the {@link Locale} locale.
     *
     * @param charset the {@link Charset} character set.
     *
     * @throws NullPointerException if the specified locale or character set is
     *                              <code>null.</code>
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
     * @see java.util.Locale#getLanguage()
     *
     * @return the {@link String} language.
     */
    public String getLanguage() {
        return locale.getLanguage();
    }

    /**
     * @see java.util.Locale#getScript()
     *
     * @return the {@link String} script.
     */
    public String getScript() {
        return locale.getScript();
    }

    /**
     * @see java.util.Locale#getCountry()
     *
     * @return the {@link String} country.
     */
    public String getCountry() {
        return locale.getCountry();
    }

    /**
     * @see java.util.Locale#getVariant()
     *
     * @return the {@link String} variant.
     */
    public String getVariant() {
        return locale.getVariant();
    }

    /**
     * @see java.util.Locale#getExtension(char)
     *
     * @param key the key.
     *
     * @return the {@link String} extension.
     */
    public String getExtension(char key) {
        return locale.getExtension(key);
    }

    /**
     * @see java.util.Locale#getExtensionKeys()
     *
     * @return the {@link Set} extension keys.
     */
    public Set<Character> getExtensionKeys() {
        return locale.getExtensionKeys();
    }

    /**
     * @see java.util.Locale#getUnicodeLocaleAttributes()
     *
     * @return the {@link Set} unicode locale attributes.
     */
    public Set<String> getUnicodeLocaleAttributes() {
        return locale.getUnicodeLocaleAttributes();
    }

    /**
     * @see java.util.Locale#getUnicodeLocaleType(java.lang.String)
     *
     * @param key the {@link String} key.
     *
     * @return the {@link String} unicode locale type.
     */
    public String getUnicodeLocaleType(String key) {
        return locale.getUnicodeLocaleType(key);
    }

    /**
     * @see java.util.Locale#getUnicodeLocaleKeys()
     *
     * @return the {@link Set} unicode locale keys.
     */
    public Set<String> getUnicodeLocaleKeys() {
        return locale.getUnicodeLocaleKeys();
    }

    /**
     * @see java.util.Locale#toLanguageTag()
     *
     * @return the {@link String} language tag.
     */
    public String toLanguageTag() {
        return locale.toLanguageTag();
    }

    /**
     * @see java.util.Locale#getISO3Language()
     *
     * @return the {@link String} ISO3 language.
     */
    public String getISO3Language() throws MissingResourceException {
        return locale.getISO3Language();
    }

    /**
     * @see java.util.Locale#getISO3Country()
     *
     * @return the {@link String} ISO3 country.
     */
    public String getISO3Country() throws MissingResourceException {
        return locale.getISO3Country();
    }

    /**
     * @see java.util.Locale#getDisplayLanguage()
     *
     * @return the {@link String} display language.
     */
    public final String getDisplayLanguage() {
        return locale.getDisplayLanguage();
    }

    /**
     * @see java.util.Locale#getDisplayLanguage(java.util.Locale)
     *
     * @param inLocale the {@link Locale}
     *
     * @return the {@link String} display language.
     */
    public String getDisplayLanguage(Locale inLocale) {
        return locale.getDisplayLanguage(inLocale);
    }

    /**
     * @see java.util.Locale#getDisplayScript()
     *
     * @return the {@link String} display script.
     */
    public String getDisplayScript() {
        return locale.getDisplayScript();
    }

    /**
     * @see java.util.Locale#getDisplayScript(java.util.Locale)
     *
     * @param inLocale the {@link Locale}.
     *
     * @return the {@link String} display script.
     */
    public String getDisplayScript(Locale inLocale) {
        return locale.getDisplayScript(inLocale);
    }

    /**
     * @see java.util.Locale#getDisplayCountry()
     *
     * @return the {@link String} display country.
     */
    public final String getDisplayCountry() {
        return locale.getDisplayCountry();
    }

    /**
     * @see java.util.Locale#getDisplayCountry(java.util.Locale)
     *
     * @param inLocale the {@link Locale}.
     *
     * @return the {@link String} display country.
     */
    public String getDisplayCountry(Locale inLocale) {
        return locale.getDisplayCountry(inLocale);
    }

    /**
     * @see java.util.Locale#getDisplayVariant()
     *
     * @return the {@link String} display variant.
     */
    public final String getDisplayVariant() {
        return locale.getDisplayVariant();
    }

    /**
     * @see java.util.Locale#getDisplayVariant(java.util.Locale)
     *
     * @param inLocale the {@link Locale}.
     *
     * @return the {@link String} display variant.
     */
    public String getDisplayVariant(Locale inLocale) {
        return locale.getDisplayVariant(inLocale);
    }

    /**
     * @see java.util.Locale#getDisplayName()
     *
     * @return the {@link String} locale display name.
     */
    public final String getLocaleDisplayName() {
        return locale.getDisplayName();
    }

    /**
     * @see java.util.Locale#getDisplayName(java.util.Locale)
     *
     * @param inLocale the {@link Locale}.
     *
     * @return the {@link String} locale display name.
     */
    public String getLocaleDisplayName(Locale inLocale) {
        return locale.getDisplayName(inLocale);
    }

    /**
     * Returns the character set.
     *
     * @return the {@link Charset}.
     */
    public Charset getCharset() {
        return charset.getCharset();
    }

    /**
     * @see java.nio.charset.Charset#name()
     *
     * @return the {@link String} charset name.
     */
    public String getCharsetName() {
        return charset.name();
    }

    /**
     * @see java.nio.charset.Charset#aliases()
     *
     * @return the {@link Set} charset aliases.
     */
    public Set<String> getCharsetAliases() {
        return charset.aliases();
    }

    /**
     * @see java.nio.charset.Charset#displayName()
     *
     * @return the {@link String} charset display name.
     */
    public String getCharsetDisplayName() {
        return charset.displayName();
    }

    /**
     * @see java.nio.charset.Charset#isRegistered()
     *
     * @return the {@link Boolean} charset registered.
     */
    public boolean isCharsetRegistered() {
        return charset.isRegistered();
    }

    /**
     * @see java.nio.charset.Charset#displayName(java.util.Locale)
     *
     * @param locale the {@link Locale}.
     *
     * @return the {@link String} charset display name.
     */
    public String getCharsetDisplayName(Locale locale) {
        return charset.displayName(locale);
    }

    /**
     * @see java.nio.charset.Charset#contains(java.nio.charset.Charset)
     *
     * @param cs the {@link Charset}.
     *
     * @return the contains.
     */
    public boolean contains(Charset cs) {
        return charset.contains(cs);
    }

    /**
     * @see java.nio.charset.Charset#newDecoder()
     *
     * @return the {@link CharsetDecoder}
     */
    public CharsetDecoder newDecoder() {
        return charset.newDecoder();
    }

    /**
     * @see java.nio.charset.Charset#newEncoder()
     *
     * @return the {@link CharsetEncoder}
     */
    public CharsetEncoder newEncoder() {
        return charset.newEncoder();
    }

    /**
     * @see java.nio.charset.Charset#canEncode()
     *
     * @return can encode.
     */
    public boolean canEncode() {
        return charset.canEncode();
    }

    /**
     * @see java.nio.charset.Charset#decode(java.nio.ByteBuffer)
     *
     * @param bb the {@link ByteBuffer}
     *
     * @return the {@link CharBuffer}.
     */
    public CharBuffer decode(ByteBuffer bb) {
        return charset.decode(bb);
    }

    /**
     * @see java.nio.charset.Charset#encode(java.nio.CharBuffer)
     *
     * @param cb the {@link CharBuffer}
     *
     * @return the {@link ByteBuffer}
     */
    public ByteBuffer encode(CharBuffer cb) {
        return charset.encode(cb);
    }

    /**
     * @see java.nio.charset.Charset#encode(java.lang.String)
     *
     * @param str the {@link String}
     *
     * @return the {@link ByteBuffer}
     */
    public ByteBuffer encode(String str) {
        return charset.encode(str);
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
        return new EqualsBuilder().append(getLocale(), rhs.getLocale()).append(getCharset(), rhs.getCharset())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getLocale()).append(getCharset()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(locale).append(charset).toString();
    }
}
