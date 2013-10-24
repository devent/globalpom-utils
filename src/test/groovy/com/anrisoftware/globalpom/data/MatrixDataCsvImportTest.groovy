package com.anrisoftware.globalpom.data

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.dataimport.CsvImportModule
import com.anrisoftware.globalpom.dataimport.CsvImporterFactory
import com.anrisoftware.globalpom.dataimport.DefaultCsvImportProperties
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

	static LONG_DATA = MatrixDataCsvImportTest.class.getResource("lotto_2001_numbers.csv").toURI()

	@BeforeClass
	static void createFactory() {
		injector = Guice.createInjector(new DataModule(), new CsvImportModule())
		matrixDataFactory = injector.getInstance MatrixDataCsvImportFactory
		importFactory = injector.getInstance CsvImporterFactory
	}
}
