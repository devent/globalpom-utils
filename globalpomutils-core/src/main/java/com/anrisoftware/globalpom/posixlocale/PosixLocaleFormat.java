/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

import java.nio.charset.Charset;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;

import com.anrisoftware.globalpom.format.locale.LocaleFormatFactory;

/**
 * Formats and parses a {@link PosixLocale} POSIX locale.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.6
 */
@SuppressWarnings("serial")
public class PosixLocaleFormat extends Format {

    /**
     * Create the duration format.
     *
     * @return the {@link PosixLocaleFormat}.
     */
    public static PosixLocaleFormat createDurationFormat() {
        return create();
    }

    /**
     * Create the duration format.
     *
     * @return the {@link PosixLocaleFormat}.
     */
    public static PosixLocaleFormat create() {
        return PosixLocaleFormatModule.getFactory().create();
    }

    private static final String SEP = ".";

    @Inject
    private PosixLocaleFactory posixLocaleFactory;

    @Inject
    private LocaleFormatFactory localeFormatFactory;

    @Inject
    private PosixLocaleFormatLogger log;

    /**
     * Use the factory methods.
     *
     * @see #create()
     * @see #createDurationFormat()
     */
    PosixLocaleFormat() {
    }

    /**
     * Formats the specified POSIX locale.
     *
     * @param obj
     *            the {@link Object} value.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof PosixLocale) {
            formatLocale((PosixLocale) obj, buff, pos);
        }
        return buff;
    }

    /**
     * Formats the specified POSIX locale.
     *
     * @param posixLocale
     *            the POSIX {@link PosixLocale} locale to format.
     */
    public void formatLocale(PosixLocale posixLocale, StringBuffer buff,
            FieldPosition pos) {
        String lang = posixLocale.getLocale().getLanguage();
        String country = posixLocale.getLocale().getCountry();
        String charset = posixLocale.getCharset().name();
        if (!isBlank(country)) {
            country = "_" + country;
        }
        buff.append(String.format("%s%s.%s", lang, country, charset));
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Parses the specified string to a POSIX locale. The format is expected to
     * be <code>[language[_territory][.codeset][@modifier]]</code>
     *
     * @return the parsed {@link PosixLocale}.
     *
     * @throws ParseException
     *             if the string cannot be parsed to a value.
     */
    public PosixLocale parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        PosixLocale result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParse(source, pos);
        }
        return result;
    }

    /**
     * @see #parse(String)
     *
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
     */
    public PosixLocale parse(String source, ParsePosition pos) {
        source = source.substring(pos.getIndex());
        try {
            PosixLocale locale = parseLocale(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length() + 1);
            return locale;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private PosixLocale parseLocale(String string, ParsePosition pos)
            throws ParseException {
        String[] strs = split(string, SEP);
        String localestr = strs[0];
        Locale locale = localeFormatFactory.create().parse(localestr);
        Charset charset = Charset.defaultCharset();
        if (strs.length > 1) {
            charset = Charset.forName(strs[1]);
        }
        return posixLocaleFactory.create(locale, charset);
    }
}
