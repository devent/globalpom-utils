/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-data.
 *
 * globalpomutils-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-data is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-data. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.dataimport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.google.inject.assistedinject.Assisted;

/**
 * Import CSV data based on CSV import properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class CsvImporterImpl implements CsvImporter {

    private static final String FILE_FIELD = "file";

    private final CsvImportProperties properties;

    private final CsvImporterImplLogger log;

    private CsvListReader reader;

    private int readLines;

    private List<String> nextValues;

    private List<String> headers;

    @Inject
    CsvImporterImpl(CsvImporterImplLogger logger,
            @Assisted CsvImportProperties properties) {
        this.log = logger;
        this.properties = properties;
        this.readLines = 0;
    }

    @Override
    public CsvImporter call() throws CsvImportException {
        CsvListReader reader = getReader();
        readHead(reader);
        this.nextValues = read(reader);
        return this;
    }

    @Override
    public List<String> getValues() {
        return nextValues;
    }

    @Override
    public List<String> getHeaders() {
        return headers;
    }

    @Override
    public CsvImportProperties getProperties() {
        return properties;
    }

    @Override
    public Map<String, Object> mapValues(List<Column> columns)
            throws ParseException {
        List<String> values = this.nextValues;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < values.size(); i++) {
            Object parsed = columns.get(i).parseValue(values.get(i));
            String name = columns.get(i).getName();
            map.put(name, parsed);
        }
        return map;
    }

    @Override
    public Map<String, Object> mapValues(Map<String, Column> columns,
            List<String> columnNames) throws ParseException {
        List<String> values = this.nextValues;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < values.size(); i++) {
            String name = columnNames.get(i);
            Column column = columns.get(name);
            log.checkColumnForName(column, name);
            Object parsed = column.parseValue(values.get(i));
            map.put(name, parsed);
        }
        return map;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(FILE_FIELD,
                properties.getFile()).toString();
    }

    private void readHead(CsvListReader reader) throws CsvImportException {
        for (; readLines < properties.getStartRow(); readLines++) {
            read(reader);
        }
        if (properties.isHaveHeader()) {
            if (headers == null) {
                this.headers = Collections.unmodifiableList(read(reader));
            }
        }
    }

    private List<String> read(CsvListReader reader) throws CsvImportException {
        try {
            return reader.read();
        } catch (IOException e) {
            throw log.errorRead(this, e);
        }
    }

    private CsvListReader getReader() throws CsvImportException {
        if (reader == null) {
            reader = createReader();
        }
        return reader;
    }

    private CsvListReader createReader() throws CsvImportException {
        char quote = properties.getQuote();
        int separator = properties.getSeparator();
        String end = properties.getEndOfLineSymbols();
        return new CsvListReader(openFile(), new CsvPreference.Builder(quote,
                separator, end).build());
    }

    private Reader openFile() throws CsvImportException {
        Charset cs = properties.getCharset();
        return new InputStreamReader(openFileStream(), cs);
    }

    private InputStream openFileStream() throws CsvImportException {
        try {
            return properties.getFile().toURL().openStream();
        } catch (MalformedURLException e) {
            throw log.errorOpenFile(this, e);
        } catch (IOException e) {
            throw log.errorOpenFile(this, e);
        }
    }

}
