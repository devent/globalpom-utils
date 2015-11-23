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
package com.anrisoftware.globalpom.spreadsheetimport;

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.anrisoftware.globalpom.dataimport.Column;

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
