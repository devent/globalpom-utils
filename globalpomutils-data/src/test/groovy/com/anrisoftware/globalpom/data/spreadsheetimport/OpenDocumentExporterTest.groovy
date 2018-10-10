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
package com.anrisoftware.globalpom.data.spreadsheetimport

import org.junit.Rule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.data.csvimport.CsvImportModule
import com.anrisoftware.globalpom.data.csvimport.CsvImporterFactory
import com.anrisoftware.globalpom.data.csvimport.CsvImporterImplTest
import com.anrisoftware.globalpom.data.csvimport.DefaultCsvImportProperties
import com.anrisoftware.globalpom.data.csvimport.MatrixDataCsvImportFactory
import com.anrisoftware.globalpom.data.data.DataModule
import com.anrisoftware.globalpom.data.dataimport.DataImportModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.transform.CompileStatic

/**
 * @see OpenDocumentExporter
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@CompileStatic
@EnableRuleMigrationSupport
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

    @BeforeAll
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(
                new SpreadsheetImporterModule(), new DataImportModule(),
                new DataModule(), new CsvImportModule())
        modelFactory = injector.getInstance DefaultSpreadsheetDataTableModelFactory
        exporterFactory = injector.getInstance OpenDocumentExporterFactory
    }
}
