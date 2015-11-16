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
@CompileStatic
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
        assert importer.getValues().toString() == "[Tag, Monat, Jahr, Zahl1, Zahl2, Zahl3, Zahl4, Zahl5, Zahl6, Zusatz, Super]"
        assert importer.loadNext() == true
        assert importer.getValues().toString() == "[3, 1, 2001, 46, 13, 21, 34, 19, 36, 38, 2]"
        (3..105).each {
            assert importer.loadNext() == true
        }
        assert importer.loadNext() == false
        assert importer.getValues().toString() == "[29, 12, 2001, 44, 11, 8, 48, 46, 2, 32, 9]"
    }

    @Test
    void "serialize default properties"() {
        def properties = injector.getInstance DefaultSpreadsheetImportProperties
        def propertiesB = reserialize properties
    }

    static Injector injector

    static OpenDocumentImporterFactory factory

    static IntegerColumnFactory integerColumnFactory

    static URL lottoFile = OpenDocumentImporterTest.class.getResource("lotto_2001.ods")

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new SpreadsheetImporterModule(), new DataImportModule())
        factory = injector.getInstance OpenDocumentImporterFactory
        integerColumnFactory = injector.getInstance IntegerColumnFactory
    }
}
