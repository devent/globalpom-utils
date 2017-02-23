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
package com.anrisoftware.globalpom.data.data

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.ejml.ops.MatrixFeatures
import org.ejml.simple.SimpleMatrix
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.data.csvimport.CsvImporterFactory
import com.anrisoftware.globalpom.data.csvimport.CsvImporterImplTest
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see MatrixData
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class MatrixDataTest {

    @Test
    void "serialize"() {
        def matrix = SimpleMatrix.diag 1.0, 1.0, 1.0
        def data = matrixDataFactory.create matrix.matrix
        assert data.numCols == 3
        assert data.numRows == 3
        def dataB = reserialize data
        assert data.numCols == dataB.numCols
        assert data.numRows == dataB.numRows
        assert MatrixFeatures.isEquals(data.matrix, dataB.matrix)
    }

    static Injector injector

    static MatrixDataFactory matrixDataFactory

    static CsvImporterFactory importFactory

    static LONG_DATA = CsvImporterImplTest.class.getResource("lotto_2001_numbers.csv").toURI()

    @BeforeClass
    static void createFactory() {
        injector = Guice.createInjector(new DataModule())
        matrixDataFactory = injector.getInstance MatrixDataFactory
    }
}
