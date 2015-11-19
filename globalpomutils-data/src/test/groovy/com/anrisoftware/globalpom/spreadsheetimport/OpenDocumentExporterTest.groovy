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
package com.anrisoftware.globalpom.spreadsheetimport

import groovy.transform.CompileStatic

import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.csvimport.CsvImportModule
import com.anrisoftware.globalpom.csvimport.CsvImporterFactory
import com.anrisoftware.globalpom.csvimport.CsvImporterImplTest
import com.anrisoftware.globalpom.csvimport.DefaultCsvImportProperties
import com.anrisoftware.globalpom.csvimport.MatrixDataCsvImportFactory
import com.anrisoftware.globalpom.data.DataModule
import com.anrisoftware.globalpom.dataimport.DataImportModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see OpenDocumentExporter
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@CompileStatic
class OpenDocumentExporterTest {

    @Test
    void "export ods"() {
        DefaultCsvImportProperties properties = injector.getInstance DefaultCsvImportProperties
        properties.setFile lottoFile.toURI()
        properties.setStartRow 1
        properties.setNumCols 8
        properties.setSeparator ';' as char

        def importer = injector.getInstance(CsvImporterFactory).create(properties)
        def data = injector.getInstance(MatrixDataCsvImportFactory).create(importer).call()
        assert data.numCols == 8
        assert data.numRows == 104

        DefaultSpreadsheetImportProperties prop = injector.getInstance DefaultSpreadsheetImportProperties
        def file = folder.newFile()
        prop.setFile file.toURI()
        def model = modelFactory.create(data, [])
        def exporter = exporterFactory.create(model, prop).write(file)
        assert new File("${file.getAbsolutePath()}.ods").isFile()
        //Thread.sleep 60*1000; assert false : "Thread.sleep"
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    static Injector injector

    static DefaultSpreadsheetDataTableModelFactory modelFactory

    static OpenDocumentExporterFactory exporterFactory

    static URL lottoFile = CsvImporterImplTest.class.getResource("lotto_2001_numbers.csv")

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(
                new SpreadsheetImporterModule(), new DataImportModule(),
                new DataModule(), new CsvImportModule())
        modelFactory = injector.getInstance DefaultSpreadsheetDataTableModelFactory
        exporterFactory = injector.getInstance OpenDocumentExporterFactory
    }
}
