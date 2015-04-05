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
package com.anrisoftware.globalpom.format.locale;

import static org.apache.commons.lang3.StringUtils.split;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Formats and parses a {@link Value} value.
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
     * @param obj
     *            the {@link Value}.
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
     * @return the parsed {@link Locale}.
     * 
     * @throws ParseException
     *             if the string cannot be parsed to a value.
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
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
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

    private Locale parseValue(String string, ParsePosition pos)
            throws ParseException {
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
                : country != null ? new Locale(lang, country)
                        : new Locale(lang);
    }
}
