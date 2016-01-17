/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import java.io.File;

/**
 * Export data to spreadsheet.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface SpreadsheetExporter {

    /**
     * Writes the data to the spreadsheet.
     *
     * @param file
     *            the {@link File}.
     *
     * @return this {@link SpreadsheetExporter}.
     */
    SpreadsheetExporter write(File file) throws SpreadsheetExportException;

    /**
     * Returns the import properties.
     *
     * @return the {@link SpreadsheetImportProperties}.
     */
    SpreadsheetImportProperties getProperties();

}
