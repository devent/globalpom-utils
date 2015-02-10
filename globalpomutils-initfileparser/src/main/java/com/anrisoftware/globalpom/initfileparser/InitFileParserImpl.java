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

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.replaceChars;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.stripEnd;
import static org.apache.commons.lang3.StringUtils.stripStart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Implements the INI file parser.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class InitFileParserImpl implements InitFileParser {

    private static final String RESOURCE = "resource";

    private final InitFileAttributes attributes;

    @Inject
    private InitFileParserImplLogger log;

    @Inject
    private SectionFactory sectionFactory;

    private URL url;

    private URI uri;

    private File file;

    private Charset charset;

    private InputStream stream;

    private LineIterator lines;

    /**
     * @see InitFileParserFactory#create(URL)
     */
    @AssistedInject
    InitFileParserImpl(@Assisted URL url,
            @Assisted InitFileAttributes attributes) {
        this.url = url;
        this.attributes = attributes;
    }

    /**
     * @see InitFileParserFactory#create(URL, Charset)
     */
    @AssistedInject
    InitFileParserImpl(@Assisted URL url,
            @Assisted InitFileAttributes attributes, @Assisted Charset charset) {
        this.url = url;
        this.attributes = attributes;
        this.charset = charset;
    }

    /**
     * @see InitFileParserFactory#create(URI)
     */
    @AssistedInject
    InitFileParserImpl(@Assisted URI uri,
            @Assisted InitFileAttributes attributes) {
        this.uri = uri;
        this.attributes = attributes;
    }

    /**
     * @see InitFileParserFactory#create(URI, Charset)
     */
    @AssistedInject
    InitFileParserImpl(@Assisted URI uri,
            @Assisted InitFileAttributes attributes, @Assisted Charset charset) {
        this.uri = uri;
        this.attributes = attributes;
        this.charset = charset;
    }

    /**
     * @see InitFileParserFactory#create(File)
     */
    @AssistedInject
    InitFileParserImpl(@Assisted File file,
            @Assisted InitFileAttributes attributes) {
        this.file = file;
        this.attributes = attributes;
    }

    /**
     * @see InitFileParserFactory#create(File, Charset)
     */
    @AssistedInject
    InitFileParserImpl(@Assisted File file,
            @Assisted InitFileAttributes attributes, @Assisted Charset charset) {
        this.file = file;
        this.attributes = attributes;
        this.charset = charset;
    }

    @Override
    public InitFileParser call() throws InitFileParserException {
        this.charset = setupDefaultCharset(this.charset);
        this.stream = openStream();
        this.lines = createLineIterator();
        return this;
    }

    @Override
    public Iterator<Section> iterator() {
        return new It(lines);
    }

    private class It implements Iterator<Section> {

        private static final String BLANK = " ";

        private final LineIterator lines;

        private final String comment;

        private final String openSection;

        private final String closeSection;

        private final String sectionBrackets;

        private final String delimiter;

        private final char stringQuote;

        private final String defaultSectionName;

        private final boolean allowMultiLineProperties;

        private Section section;

        private String nextSectionName;

        public It(LineIterator lines) {
            this.section = null;
            this.lines = lines;
            this.defaultSectionName = attributes.getDefaultSectionName();
            this.comment = Character.toString(attributes.getComment());
            this.delimiter = Character.toString(attributes
                    .getPropertyDelimiter());
            this.stringQuote = attributes.getStringQuote();
            this.openSection = Character.toString(attributes
                    .getSectionBrackets()[0]);
            this.closeSection = Character.toString(attributes
                    .getSectionBrackets()[1]);
            this.sectionBrackets = openSection + closeSection;
            this.allowMultiLineProperties = attributes
                    .isAllowMultiLineProperties();
        }

        @Override
        public boolean hasNext() {
            if (section == null) {
                try {
                    section = readNextSection(lines);
                } catch (InitFileParserException e) {
                    throw new RuntimeException(e);
                }
            }
            return section != null;
        }

        @Override
        public Section next() {
            Section section = this.section;
            this.section = null;
            return section;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Section readNextSection(LineIterator lines)
                throws InitFileParserException {
            if (nextSectionName != null) {
                String name = nextSectionName;
                nextSectionName = null;
                Section section = createSection(name, lines, null);
                return section;
            }
            while (lines.hasNext()) {
                String line = lines.next().trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (lineIsComment(line)) {
                    continue;
                }
                if (lineIsProperty(line)) {
                    String name = defaultSectionName;
                    return createSection(name, lines, line);
                }
                if (lineStartsSection(line)) {
                    String name = sectionName(line);
                    return createSection(name, lines, null);
                }
            }
            return section;
        }

        private Section createSection(String name, LineIterator lines,
                String line) throws InitFileParserException {
            Properties properties = new Properties();
            if (line != null) {
                putProperty(line, properties);
            }
            String lastProperty = null;
            while (lines.hasNext()) {
                line = stripEnd(lines.next(), null);
                if (line.isEmpty()) {
                    continue;
                }
                if (lineIsComment(line)) {
                    continue;
                }
                if (lineIsMultiLine(line, lastProperty)) {
                    checkMultiLineAllowed();
                    String value = properties.getProperty(lastProperty);
                    value += line.trim();
                    properties.setProperty(lastProperty, value);
                    continue;
                }
                if (lineIsProperty(line)) {
                    lastProperty = putProperty(line, properties);
                    continue;
                }
                if (lineStartsSection(line)) {
                    nextSectionName = sectionName(line);
                    break;
                }
            }
            return sectionFactory.create(name, properties);
        }

        private void checkMultiLineAllowed() throws InitFileParserException {
            if (!allowMultiLineProperties) {
                throw log.errorMultiLineProperty(InitFileParserImpl.this);
            }
        }

        private boolean lineIsMultiLine(String line, String lastProperty) {
            return line.startsWith(BLANK) && lastProperty != null;
        }

        private String putProperty(String line, Properties properties) {
            int i = line.indexOf(delimiter);
            String property = line.substring(0, i).trim();
            String value = stripStart(line.substring(i + 1), null);
            int quoteStart = value.indexOf(stringQuote);
            int quoteEnd = value.lastIndexOf(stringQuote);
            if (quoteEnd > quoteStart) {
                value = value.substring(quoteStart + 1, quoteEnd);
            }
            properties.put(property, value);
            return property;
        }

        private boolean lineIsComment(String line) {
            return startsWith(line, comment);
        }

        private boolean lineIsProperty(String line) {
            return contains(line, delimiter);
        }

        private String sectionName(String line) {
            return replaceChars(line, sectionBrackets, EMPTY);
        }

        private boolean lineStartsSection(String line) {
            return startsWith(line, openSection)
                    && endsWith(line, closeSection);
        }

    }

    private LineIterator createLineIterator() throws InitFileParserException {
        try {
            return IOUtils.lineIterator(stream, charset);
        } catch (IOException e) {
            throw log.lineIteratorError(this, e);
        }
    }

    private InputStream openStream() throws InitFileParserException {
        return url != null ? openURLStream(url)
                : uri != null ? openURIStream(uri) : openFileStream(file);
    }

    private InputStream openFileStream(File file)
            throws InitFileParserException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw log.openStreamError(this, e);
        }
    }

    private InputStream openURIStream(URI uri) throws InitFileParserException {
        try {
            return openURLStream(uri.toURL());
        } catch (MalformedURLException e) {
            throw log.openStreamError(this, e);
        }
    }

    private InputStream openURLStream(URL url) throws InitFileParserException {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw log.openStreamError(this, e);
        }
    }

    private Charset setupDefaultCharset(Charset charset) {
        return charset == null ? defaultCharset() : charset;
    }

    public Object getResource() {
        if (uri != null) {
            return uri;
        }
        if (url != null) {
            return url;
        }
        return file;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        if (uri != null) {
            builder.append(RESOURCE, uri);
        }
        if (url != null) {
            builder.append(RESOURCE, url);
        }
        if (file != null) {
            builder.append(RESOURCE, file);
        }
        return builder.toString();
    }
}
