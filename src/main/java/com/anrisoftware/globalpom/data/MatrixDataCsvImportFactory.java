/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
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
