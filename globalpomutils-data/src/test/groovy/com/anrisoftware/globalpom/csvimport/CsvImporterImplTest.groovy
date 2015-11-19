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
package com.anrisoftware.globalpom.csvimport

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.data.DataModule
import com.anrisoftware.globalpom.dataimport.DataImportModule
import com.anrisoftware.globalpom.dataimport.StringColumnFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see CsvImporterImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class CsvImporterImplTest {

    @Test
    void "import csv"() {
        DefaultCsvImportProperties prop = injector.getInstance DefaultCsvImportProperties
        prop.setFile lottoFile
        def importer = factory.create(prop)
        importer()
        assert importer.getValues().toString() == "[Tag, Monat, Jahr, Zahl1, Zahl2, Zahl3, Zahl4, Zahl5, Zahl6, Zusatz, Super]"
        importer()
        assert importer.getValues().toString() == "[3, 1, 2001, 46, 13, 21, 34, 19, 36, 38, 2]"
    }

    @Test
    void "import csv with header"() {
        DefaultCsvImportProperties prop = injector.getInstance DefaultCsvImportProperties
        prop.setFile lottoFile
        prop.setHaveHeader true
        def importer = factory.create(prop)
        importer()
        assert importer.getHeaders().toString() == "[Tag, Monat, Jahr, Zahl1, Zahl2, Zahl3, Zahl4, Zahl5, Zahl6, Zusatz, Super]"
        assert importer.getValues().toString() == "[3, 1, 2001, 46, 13, 21, 34, 19, 36, 38, 2]"
        importer()
        assert importer.getValues().toString() == "[6, 1, 2001, 17, 42, 12, 8, 37, 26, 31, 6]"
    }

    @Test
    void "import csv with header, map values"() {
        DefaultCsvImportProperties prop = injector.getInstance DefaultCsvImportProperties
        def columns = [
            Tag: stringColumnFactory.create("Tag"),
            Monat: stringColumnFactory.create("Monat"),
            Jahr: stringColumnFactory.create("Jahr"),
            Zahl1: stringColumnFactory.create("Zahl1"),
            Zahl2: stringColumnFactory.create("Zahl2"),
            Zahl3: stringColumnFactory.create("Zahl3"),
            Zahl4: stringColumnFactory.create("Zahl4"),
            Zahl5: stringColumnFactory.create("Zahl5"),
            Zahl6: stringColumnFactory.create("Zahl6"),
            Zusatz: stringColumnFactory.create("Zusatz"),
            Super: stringColumnFactory.create("Super"),
        ]

        prop.setFile lottoFile
        prop.setHaveHeader true
        def importer = factory.create(prop)
        importer()
        def headers = importer.getHeaders()
        def values = importer.mapValues(columns, headers)
        assert values.size() == 11
        assert values.Tag == "3"
        assert values.Monat == "1"
        assert values.Jahr == "2001"
        importer()
        values = importer.mapValues(columns, headers)
        assert values.size() == 11
        assert values.Tag == "6"
        assert values.Monat == "1"
        assert values.Jahr == "2001"
    }

    @Test
    void "import csv, skip head"() {
        DefaultCsvImportProperties prop = injector.getInstance DefaultCsvImportProperties
        prop.setFile lottoFile
        prop.setStartRow 1

        def importer = factory.create(prop)
        importer()
        assert importer.getValues().toString() == "[3, 1, 2001, 46, 13, 21, 34, 19, 36, 38, 2]"
        importer()
        assert importer.getValues().toString() == "[6, 1, 2001, 17, 42, 12, 8, 37, 26, 31, 6]"
    }

    @Test
    void "serialize default properties"() {
        def properties = injector.getInstance DefaultCsvImportProperties
        def propertiesB = reserialize properties
    }

    static Injector injector

    static CsvImporterFactory factory

    static StringColumnFactory stringColumnFactory

    static URI lottoFile = CsvImporterImplTest.class.getResource("lotto_2001.csv").toURI()

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new CsvImportModule(), new DataModule(), new DataImportModule())
        factory = injector.getInstance(CsvImporterFactory)
        stringColumnFactory = injector.getInstance(StringColumnFactory)
    }
}
