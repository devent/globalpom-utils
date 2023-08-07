/*
 * Copyright 2013-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.localeformat;

import static org.apache.commons.lang3.StringUtils.split;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import jakarta.inject.Inject;

/**
 * Formats and parses a {@link Locale}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class LocaleFormat extends Format {

    private static final String SEP = "_";

    @Inject
    private LocaleFormatLogger log;

    /**
     * Formats the specified locale.
     *
     * @param obj the {@link Locale}.
     *
     * @see Locale#toString()
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof Locale) {
            buff.append(obj.toString());
        }
        return buff;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Parses the specified string to a locale.
     *
     * @param source the {@link String} source.
     *
     * @return the parsed {@link Locale}.
     *
     * @throws ParseException if the string cannot be parsed to a value.
     *
     * @see Locale#toString()
     */
    public Locale parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Locale result = parse(source, pos);
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
     * @return the parsed {@link Locale}.
     */
    public Locale parse(String source, ParsePosition pos) {
        source = source.substring(pos.getIndex());
        try {
            Locale address = parseValue(source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length() + 1);
            return address;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private Locale parseValue(String string, ParsePosition pos) throws ParseException {
        String[] strs = split(string, SEP);
        if (strs.length == 0) {
            return new Locale("");
        }
        String lang = strs[0];
        String country = null;
        String variant = null;
        if (strs.length > 1) {
            country = strs[1];
        }
        if (strs.length > 2) {
            variant = strs[2];
        }
        return variant != null ? new Locale(lang, country, variant)
                : country != null ? new Locale(lang, country) : new Locale(lang);
    }
}
