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
package com.anrisoftware.globalpom.initfileparser.internal;

/*-
 * #%L
 * Global POM Utilities :: Init File Parser
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.inject.Inject;

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
