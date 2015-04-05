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

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Reads CSV formatted data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface CsvImporter extends Callable<CsvImporter> {

    /**
     * Returns the import properties.
     * 
     * @return the {@link CsvImportProperties}.
     */
    CsvImportProperties getProperties();

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
     * Returns the values of the read row.
     * 
     * @return the values {@link List} of the read row or {@code null} if the
     *         end of file was reached.
     */
    List<String> getValues();

    /**
     * Reads the next row of the CSV formatted data.
     */
    @Override
    CsvImporter call() throws CsvImportException;
}
