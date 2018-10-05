/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.data.spreadsheetimport;

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

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.anrisoftware.globalpom.data.dataimport.Column;

/**
 * Reads spreadsheet data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface SpreadsheetImporter extends Closeable {

    /**
     * Opens the spreadsheet to read data.
     *
     * @return this {@link SpreadsheetImporter}.
     */
    SpreadsheetImporter open() throws SpreadsheetImportException;

    /**
     * Closes the spreadsheet.
     *
     * @since 2.15
     */
    @Override
    void close() throws IOException;

    /**
     * Returns how many sheets are in the spreadsheet document.
     *
     * @return the sheets {@link Integer} count.
     *
     * @since 2.15
     */
    int getSheetsCount();

    /**
     * Loads the next row
     *
     * @return {@code true} if there are more rows to load.
     */
    boolean loadNext() throws SpreadsheetImportException;

    /**
     * Returns the import properties.
     *
     * @return the {@link SpreadsheetImportProperties}.
     */
    SpreadsheetImportProperties getProperties();

    /**
     * Returns the names of the header if the spreadsheet file contains a header
     * row.
     *
     * @return the {@link List} of the header {@link String} names or
     *         {@code null} if the spreadsheet file have no header row.
     *
     * @see SpreadsheetImportProperties#isHaveHeader()
     */
    List<String> getHeaders();

    /**
     * Returns the values of the read row.
     *
     * @return the values {@link List} of the read row or {@code null} if the
     *         end of file was reached.
     */
    List<String> getValues();

    /**
     * Returns the values of the read row with the column name as the map key
     * and the column value as the map value.
     *
     * @param columns
     *            the {@link List} of {@link Column} columns.
     *
     * @return the column names and values {@link Map} of the read row or
     *         {@code null} if the end of file was reached.
     *
     * @throws ParseException
     *             if the columns could not be parsed.
     */
    Map<String, Object> mapValues(List<Column> columns) throws ParseException;

    /**
     * Returns the values of the read row with the column name as the map key
     * and the column value as the map value.
     *
     * @param columns
     *            the {@link Map} of {@link Column} columns, identified by the
     *            column name.
     *
     * @param columnNames
     *            the {@link List} of column {@link String} names to map the
     *            column.
     *
     * @return the column names and values {@link Map} of the read row or
     *         {@code null} if the end of file was reached.
     *
     * @throws ParseException
     *             if the columns could not be parsed.
     */
    Map<String, Object> mapValues(Map<String, Column> columns,
            List<String> columnNames) throws ParseException;

}
