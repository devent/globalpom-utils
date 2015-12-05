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

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.apache.commons.io.IOUtils
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.dataimport.DataImportModule
import com.anrisoftware.globalpom.dataimport.IntegerColumnFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

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
