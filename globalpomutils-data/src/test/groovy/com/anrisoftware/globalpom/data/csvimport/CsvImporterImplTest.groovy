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
package com.anrisoftware.globalpom.data.csvimport

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.data.data.DataModule
import com.anrisoftware.globalpom.data.dataimport.DataImportModule
import com.anrisoftware.globalpom.data.dataimport.StringColumnFactory
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

    @BeforeEachClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new CsvImportModule(), new DataModule(), new DataImportModule())
        factory = injector.getInstance(CsvImporterFactory)
        stringColumnFactory = injector.getInstance(StringColumnFactory)
    }
}
