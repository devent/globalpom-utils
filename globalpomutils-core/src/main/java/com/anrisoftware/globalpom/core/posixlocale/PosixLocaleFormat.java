/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

import java.nio.charset.Charset;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;

import com.anrisoftware.globalpom.core.localeformat.LocaleFormatFactory;

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
     * @param obj the {@link Object} value.
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
     * @param posixLocale the POSIX {@link PosixLocale} locale to format.
     *
     * @param buff        the {@link StringBuffer}
     *
     * @param pos         the {@link FieldPosition}
     */
    public void formatLocale(PosixLocale posixLocale, StringBuffer buff, FieldPosition pos) {
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
     * Parses the specified string to a POSIX locale. The format is expected to be
     * <code>[language[_territory][.codeset][@modifier]]</code>
     *
     * @param source the {@link String} source.
     *
     * @return the parsed {@link PosixLocale}.
     *
     * @throws ParseException if the string cannot be parsed to a value.
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
     * @param source the {@link String} source.
     *
     * @param pos    the index {@link ParsePosition} position from where to start
     *               parsing.
     *
     * @return the parsed {@link PosixLocale}.
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

    private PosixLocale parseLocale(String string, ParsePosition pos) throws ParseException {
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
