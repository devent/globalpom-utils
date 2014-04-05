/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.initfileparser;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * Implements the INI file section formatter.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class SectionFormatterImpl implements SectionFormatter {

    private static final String SPACE = " ";

    private final char delimiter;

    private final char openSection;

    private final char closeSection;

    private final boolean whitespaceBetweenPropertyDelimiter;

    private final String newLine;

    /**
     * @see SectionFormatterFactory#create(InitFileAttributes)
     */
    @Inject
    SectionFormatterImpl(@Assisted InitFileAttributes attributes) {
        this.delimiter = attributes.getPropertyDelimiter();
        this.openSection = attributes.getSectionBrackets()[0];
        this.closeSection = attributes.getSectionBrackets()[1];
        this.whitespaceBetweenPropertyDelimiter = attributes
                .isWhitespaceBetweenPropertyDelimiter();
        this.newLine = attributes.getNewLine();
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

    private Appendable format0(Section section, Appendable builder)
            throws IOException {
        builder.append(openSection).append(section.getName())
                .append(closeSection).append(newLine);
        for (Map.Entry<Object, Object> entry : section.getProperties()
                .entrySet()) {
            builder.append(entry.getKey().toString());
            builder = whitespaceBetweenPropertyDelimiter ? builder
                    .append(SPACE) : builder;
            builder.append(delimiter);
            builder = whitespaceBetweenPropertyDelimiter ? builder
                    .append(SPACE) : builder;
            builder.append(entry.getValue().toString());
            builder.append(newLine);
        }
        return builder;
    }
}
