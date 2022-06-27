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
package com.anrisoftware.globalpom.data.csvimport;

/*-
 * #%L
 * Global POM Utilities :: Data
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

import com.anrisoftware.globalpom.data.dataimport.Column;
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
            throw new ReadValuesException(this, e);
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
            throw new OpenCsvException(this, e);
        } catch (IOException e) {
            throw new OpenCsvException(this, e);
        }
    }

}
