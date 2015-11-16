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
package com.anrisoftware.globalpom.data

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.csvimport.CsvImportModule;
import com.anrisoftware.globalpom.csvimport.CsvImporterFactory;
import com.anrisoftware.globalpom.csvimport.DefaultCsvImportProperties;
import com.anrisoftware.globalpom.csvimport.MatrixDataCsvImportFactory;
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see MatrixDataCsvImport
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class MatrixDataCsvImportTest {

    @Test
    void "import long numbers"() {
        DefaultCsvImportProperties properties = injector.getInstance DefaultCsvImportProperties
        properties.setFile LONG_DATA
        properties.setStartRow 1
        properties.setNumCols 8
        properties.setSeparator ';' as char

        def importer = importFactory.create properties
        def data = matrixDataFactory.create(importer)()
        assert data.numCols == 8
        assert data.numRows == 104

        assertDecimalEquals data.get(0, 0), 46
        assertDecimalEquals data.get(0, 1), 13
    }

    static Injector injector

    static MatrixDataCsvImportFactory matrixDataFactory

    static CsvImporterFactory importFactory

    static LONG_DATA = MatrixDataCsvImportTest.class.getResource("lotto_2001_numbers.csv").toURI()

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new DataModule(), new CsvImportModule())
        matrixDataFactory = injector.getInstance MatrixDataCsvImportFactory
        importFactory = injector.getInstance CsvImporterFactory
    }
}
