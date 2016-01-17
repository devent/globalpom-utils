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
package com.anrisoftware.globalpom.csvimport;

import java.text.Format;

/**
 * Factory to import data from CSV file importer.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface MatrixDataCsvImportFactory {

    /**
     * Imports data from the specified importer.
     * 
     * @param importer
     *            the {@link CsvImporter}.
     * 
     * @return the {@link MatrixDataCsvImport}.
     */
    MatrixDataCsvImport create(CsvImporter importer);

    /**
     * Imports data from the specified importer.
     * 
     * @param matrix
     *            the {@link CsvImporter}
     * 
     * @param format
     *            the {@link Format} to parse the text to double values.
     * 
     * @return the {@link MatrixDataCsvImport}.
     * 
     * @since 1.11
     */
    MatrixDataCsvImport create(CsvImporter matrix, Format format);

}
