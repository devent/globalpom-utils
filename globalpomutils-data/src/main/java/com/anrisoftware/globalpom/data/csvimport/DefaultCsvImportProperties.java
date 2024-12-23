/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static com.anrisoftware.globalpom.core.charset.SerializableCharset.decorate;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.core.charset.SerializableCharset;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Mutable CSV import properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class DefaultCsvImportProperties implements CsvImportProperties, Serializable {

    private URI file;

    private SerializableCharset charset;

    private Locale locale;

    private char separator;

    private char quote;

    private String endOfLineSymbols;

    private int startRow;

    private int numCols;

    private boolean haveHeader;

    /**
     * @see DefaultCsvImportPropertiesFactory#create(CsvImportProperties)
     *
     * @param properties the {@link CsvImportProperties}
     */
    @AssistedInject
    public DefaultCsvImportProperties(@Assisted CsvImportProperties properties) {
        setHaveHeader(properties.isHaveHeader());
        setCharset(properties.getCharset());
        setEndOfLineSymbols(properties.getEndOfLineSymbols());
        setFile(properties.getFile());
        setLocale(properties.getLocale());
        setNumCols(properties.getNumCols());
        setQuote(properties.getQuote());
        setSeparator(properties.getSeparator());
        setStartRow(properties.getStartRow());
    }

    /**
     * @see DefaultCsvImportPropertiesFactory#create()
     */
    @AssistedInject
    public DefaultCsvImportProperties() {
        this.file = new File("").toURI();
        this.haveHeader = false;
        this.charset = decorate(Charset.defaultCharset());
        this.locale = Locale.US;
        this.separator = ',';
        this.quote = '"';
        this.endOfLineSymbols = System.getProperty("line.separator");
        this.startRow = 0;
        this.numCols = 0;
    }

    /**
     *
     * @param file the {@link URI} file.
     */
    public void setFile(URI file) {
        this.file = file;
    }

    @Override
    public URI getFile() {
        return file;
    }

    /**
     * Sets that the CSV data have a header row that determines the columns.
     *
     * @param haveHeader {@code true} if the data have a header row.
     *
     * @since 2.3
     */
    public void setHaveHeader(boolean haveHeader) {
        this.haveHeader = haveHeader;
    }

    @Override
    public boolean isHaveHeader() {
        return haveHeader;
    }

    /**
     *
     * @param charset the {@link Charset}
     */
    public void setCharset(Charset charset) {
        this.charset = decorate(charset);
    }

    @Override
    public Charset getCharset() {
        return charset.getCharset();
    }

    /**
     *
     * @param locale the {@link Locale}
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     *
     * @param separator the separator character.
     */
    public void setSeparator(char separator) {
        this.separator = separator;
    }

    @Override
    public char getSeparator() {
        return separator;
    }

    /**
     *
     * @param quote the quote character.
     */
    public void setQuote(char quote) {
        this.quote = quote;
    }

    @Override
    public char getQuote() {
        return quote;
    }

    /**
     *
     * @param symbols the {@link String} symbols.
     */
    public void setEndOfLineSymbols(String symbols) {
        this.endOfLineSymbols = symbols;
    }

    @Override
    public String getEndOfLineSymbols() {
        return endOfLineSymbols;
    }

    /**
     *
     * @param row the start row.
     */
    public void setStartRow(int row) {
        this.startRow = row;
    }

    @Override
    public int getStartRow() {
        return startRow;
    }

    /**
     *
     * @param numCols the number of columns.
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
