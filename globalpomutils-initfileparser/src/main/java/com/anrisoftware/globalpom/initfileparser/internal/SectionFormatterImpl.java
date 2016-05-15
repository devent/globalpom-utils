/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-initfileparser.
 *
 * globalpomutils-initfileparser is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-initfileparser is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-initfileparser. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser.internal;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.anrisoftware.globalpom.initfileparser.external.InitFileAttributes;
import com.anrisoftware.globalpom.initfileparser.external.Section;
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatter;
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatterFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Implements the INI file section formatter.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class SectionFormatterImpl implements SectionFormatter {

    private static final String SPACE = " ";

    private static final Pattern QUOTE_MATCH_PATTERN = Pattern
            .compile("[\\s\\p{Punct}]");

    private final char delimiter;

    private final char openSection;

    private final char closeSection;

    private final boolean whitespaceBetweenPropertyDelimiter;

    private final String newLine;

    private final String mark;

    private final char stringQuote;

    private final boolean stringQuoteEnabled;

    /**
     * @see SectionFormatterFactory#create(InitFileAttributes)
     */
    @Inject
    SectionFormatterImpl(@Assisted InitFileAttributes attributes) {
        this.delimiter = attributes.getPropertyDelimiter();
        this.openSection = attributes.getSectionBrackets()[0];
        this.closeSection = attributes.getSectionBrackets()[1];
        this.mark = attributes.getMultiValueMark();
        this.whitespaceBetweenPropertyDelimiter = attributes
                .isWhitespaceBetweenPropertyDelimiter();
        this.newLine = attributes.getNewLine();
        this.stringQuote = attributes.getStringQuote();
        this.stringQuoteEnabled = attributes.isStringQuoteEnabled();
    }

    @Override
    public String format(Section section) {
        StringBuilder builder = new StringBuilder();
        return format(section, builder).toString();
    }

    @Override
    public StringBuilder format(Section section, StringBuilder builder) {
        try {
            return (StringBuilder) format0(section, builder);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public StringBuffer format(Section section, StringBuffer builder) {
        try {
            return (StringBuffer) format0(section, builder);
        } catch (IOException e) {
            return null;
        }
    }

    private Appendable format0(Section section, Appendable b)
            throws IOException {
        b.append(openSection).append(section.getName()).append(closeSection);
        b.append(newLine);
        for (Map.Entry<Object, Object> entry : section.getProperties()
                .entrySet()) {
            if (entry.getValue() instanceof Iterable) {
                for (Object v : ((Iterable<?>) entry.getValue())) {
                    b = formatSection(b, mark, entry.getKey(), v);
                }
            } else {
                b = formatSection(b, "", entry.getKey(), entry.getValue());
            }
        }
        return b;
    }

    private Appendable formatSection(Appendable b, String mark, Object key,
            Object value) throws IOException {
        return formatSection(b, mark, key.toString(), value.toString());
    }

    private Appendable formatSection(Appendable b, String multiMark,
            String property, String value) throws IOException {
        b.append(property);
        b.append(multiMark);
        b = whitespaceBetweenPropertyDelimiter ? b.append(SPACE) : b;
        b.append(delimiter);
        b = whitespaceBetweenPropertyDelimiter ? b.append(SPACE) : b;
        b.append(quoteValue(value));
        b.append(newLine);
        return b;
    }

    private CharSequence quoteValue(String value) {
        if (!stringQuoteEnabled) {
            return value;
        }
        Matcher matcher = QUOTE_MATCH_PATTERN.matcher(value);
        if (matcher.find()) {
            return String.format("%s%s%s", stringQuote, value, stringQuote);
        } else {
            return value;
        }
    }
}
