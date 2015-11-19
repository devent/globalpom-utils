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

import java.net.URI;

/**
 * Properties to import a spreadsheet file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface SpreadsheetImportProperties {

    /**
     * Returns the file to import.
     *
     * @return the {@link URI} of the file resource.
     */
    URI getFile();

    /**
     * Returns the sheet number to import.
     *
     * @return the {@link Integer} sheet number.
     */
    int getSheetNumber();

    /**
     * Returns the indices of the columns to import.
     *
     * @return the integer array of the indices.
     */
    int[] getColumns();

    /**
     * Returns the index of the start row of the data.
     *
     * @return the start row {@link Integer} index.
     */
    int getStartRow();

    /**
     * Returns the index of the end row of the data.
     *
     * @return the end row {@link Integer} index.
     */
    int getEndRow();

    /**
     * Returns if the data from start row to end row have a header row.
     *
     * @return {@code true} if have a header row.
     */
    boolean isHaveHeader();
}
