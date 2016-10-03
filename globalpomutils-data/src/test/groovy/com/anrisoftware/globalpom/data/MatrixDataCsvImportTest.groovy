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
package com.anrisoftware.globalpom.data

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.csvimport.CsvImportModule
import com.anrisoftware.globalpom.csvimport.CsvImporterFactory
import com.anrisoftware.globalpom.csvimport.CsvImporterImplTest
import com.anrisoftware.globalpom.csvimport.DefaultCsvImportProperties
import com.anrisoftware.globalpom.csvimport.MatrixDataCsvImportFactory
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

    static LONG_DATA = CsvImporterImplTest.class.getResource("lotto_2001_numbers.csv").toURI()

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new DataModule(), new CsvImportModule())
        matrixDataFactory = injector.getInstance MatrixDataCsvImportFactory
        importFactory = injector.getInstance CsvImporterFactory
    }
}
