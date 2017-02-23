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
package com.anrisoftware.globalpom.data.spreadsheetimport

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.commons.io.IOUtils
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.data.dataimport.DataImportModule
import com.anrisoftware.globalpom.data.dataimport.IntegerColumnFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

/**
 * @see OpenDocumentImporter
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Slf4j
class OpenDocumentImporterTest {

    @Test
    void "import ods"() {
        def file = folder.newFile()
        IOUtils.copy(lottoFile.openStream(), new FileOutputStream(file))
        DefaultSpreadsheetImportProperties prop = injector.getInstance DefaultSpreadsheetImportProperties
        prop.setFile file.toURI()
        prop.setSheetNumber 0
        def importer = factory.create(prop)
        importer.open()
        assert importer.loadNext() == true
        assert importer.getValues().toString() == "[Row, Tag, Monat, Jahr, Zahl1, Zahl2, Zahl3, Zahl4, Zahl5, Zahl6, Zusatz, Super]"
        assert importer.loadNext() == true
        assert importer.getValues().toString() == "[0, 3, 1, 2001, 46, 13, 21, 34, 19, 36, 38, 2]"
        (3..105).each {
            assert importer.loadNext() == true
        }
        assert importer.loadNext() == false
        assert importer.getValues().toString() == "[103, 29, 12, 2001, 44, 11, 8, 48, 46, 2, 32, 9]"
    }

    @Test
    void "import ods, start end row"() {
        def file = folder.newFile()
        IOUtils.copy(lottoFile.openStream(), new FileOutputStream(file))
        DefaultSpreadsheetImportProperties prop = injector.getInstance DefaultSpreadsheetImportProperties
        prop.setFile file.toURI()
        prop.setSheetNumber 0
        prop.setStartRow 5
        prop.setEndRow 10
        def importer = factory.create(prop)
        importer.open()
        assert importer.loadNext() == true
        assert importer.getValues().toString() == "[4, 17, 1, 2001, 20, 12, 49, 11, 43, 45, 22, 4]"
        (1..5).each {
            assert importer.loadNext() == true
        }
        assert importer.loadNext() == false
        assert importer.getValues().toString() == "[9, 3, 2, 2001, 28, 12, 15, 3, 37, 35, 47, 2]"
    }

    @Test
    void "import ods, one column"() {
        def file = folder.newFile()
        IOUtils.copy(lottoFile.openStream(), new FileOutputStream(file))
        DefaultSpreadsheetImportProperties prop = injector.getInstance DefaultSpreadsheetImportProperties
        prop.setFile file.toURI()
        prop.setSheetNumber 0
        prop.setStartRow 1
        prop.setColumns([1] as int[])
        def importer = factory.create(prop)
        importer.open()
        assert importer.loadNext() == true
        assert importer.getValues().toString() == "[3]"
        (1..5).each {
            assert importer.loadNext() == true
        }
        assert importer.loadNext() == true
        assert importer.getValues().toString() == "[24]"
    }

    @Test
    void "import ods, specific columns"() {
        def file = folder.newFile()
        IOUtils.copy(lottoFile.openStream(), new FileOutputStream(file))
        DefaultSpreadsheetImportProperties prop = injector.getInstance DefaultSpreadsheetImportProperties
        prop.setFile file.toURI()
        prop.setSheetNumber 0
        prop.setStartRow 1

        def testCases = [
            [
                columns: [0],
                values: [
                    "[0]",
                    "[1]",
                ],
            ],
            [
                columns: [0, 1],
                values: [
                    "[0, 3]",
                    "[1, 6]",
                ],
            ],
            [
                columns: [1],
                values: [
                    "[3]",
                    "[6]",
                ],
            ],
            [
                columns: [1, 2, 5],
                // 0, 1, 2, 3, 4
                // 1=0, 2=1, 5=4
                values: [
                    "[3, 1, 13]",
                    "[6, 1, 42]",
                ],
            ],
        ]
        testCases.eachWithIndex { Map test, int k ->
            log.info "{}. test case: {}", k, test
            def p = propertiesFactory.create(prop)
            p.setColumns(test.columns as int[])
            def importer = factory.create(p)
            importer.open()
            (test.values as List<String>).each { String expectedValues ->
                assert importer.loadNext() == true
                assert importer.getValues().toString() == expectedValues
            }
        }
    }

    @Test
    void "serialize default properties"() {
        def properties = injector.getInstance DefaultSpreadsheetImportProperties
        def propertiesB = reserialize properties
    }

    static Injector injector

    static DefaultSpreadsheetImportPropertiesFactory propertiesFactory

    static OpenDocumentImporterFactory factory

    static IntegerColumnFactory integerColumnFactory

    static URL lottoFile = OpenDocumentImporterTest.class.getResource("lotto_2001.ods")

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new SpreadsheetImporterModule(), new DataImportModule())
        propertiesFactory = injector.getInstance DefaultSpreadsheetImportPropertiesFactory
        factory = injector.getInstance OpenDocumentImporterFactory
        integerColumnFactory = injector.getInstance IntegerColumnFactory
    }
}
