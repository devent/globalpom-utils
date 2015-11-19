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

import org.ejml.ops.MatrixFeatures
import org.ejml.simple.SimpleMatrix
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.csvimport.CsvImporterFactory
import com.anrisoftware.globalpom.csvimport.CsvImporterImplTest
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
