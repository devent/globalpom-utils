package com.anrisoftware.globalpom.data;

import java.text.Format;

import org.ejml.data.Matrix64F;

import com.anrisoftware.globalpom.dataimport.CsvImporter;

/**
 * Factory to import data from CSV file importer.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface MatrixDataCsvImportFactory {

	/**
	 * Imports data from the specified importer.
	 * 
	 * @param importer
	 *            the {@link CsvImporter}.
	 * 
	 * @return the {@link MatrixDataCsvImport}.
	 */
	MatrixDataCsvImport create(CsvImporter importer);

	/**
	 * Creates new matrix data with the specified matrix.
	 * 
	 * @param matrix
	 *            the {@link Matrix64F}
	 * 
	 * @param format
	 *            the {@link Format} to parse the text to double values.
	 * 
	 * @return the {@link MatrixData}.
	 */
	MatrixData create(Matrix64F matrix, Format format);

}
