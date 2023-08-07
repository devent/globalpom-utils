/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.data.csvimport;

/*-
 * #%L
 * Global POM Utilities :: Data
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import jakarta.inject.Inject;

import org.ejml.data.MatrixIterator;
import org.ejml.data.ReshapeMatrix64F;
import org.ejml.simple.SimpleMatrix;

import com.anrisoftware.globalpom.data.data.Data;
import com.anrisoftware.globalpom.data.data.MatrixDataFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Imports data from a CSV file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class MatrixDataCsvImport implements Callable<MatrixDataCsvImport>,
		Data, Serializable {

    private final Format format;

    @Inject
    private MatrixDataCsvImportLogger log;

	@Inject
	private MatrixDataFactory dataFactory;

	private CsvImporter importer;

	private Data data;

	/**
	 * @see MatrixDataCsvImportFactory#create(CsvImporter)
	 */
	@AssistedInject
	MatrixDataCsvImport(@Assisted CsvImporter importer) {
		this(importer, DecimalFormat.getInstance(importer.getProperties()
				.getLocale()));
	}

	/**
	 * @see MatrixDataCsvImportFactory#create(CsvImporter, Format)
	 */
	@AssistedInject
	MatrixDataCsvImport(@Assisted CsvImporter importer, @Assisted Format format) {
		this.importer = importer;
		this.format = format;
	}

	/**
	 * Imports the CSV data.
	 */
	@Override
	public MatrixDataCsvImport call() throws CsvImportException, ParseException {
		List<double[]> rows = parseValues();
        ReshapeMatrix64F matrix = createMatrix(rows);
		this.data = dataFactory.create(matrix);
		log.importedData(this, importer);
		this.importer = null;
		return this;
	}

    private ReshapeMatrix64F createMatrix(List<double[]> rows) {
		SimpleMatrix matrix = new SimpleMatrix(rows.toArray(new double[][] {}));
		return matrix.getMatrix();
	}

	private List<double[]> parseValues() throws CsvImportException,
			ParseException {
		CsvImporter importer = this.importer;
		int columns = importer.getProperties().getNumCols();
		List<double[]> list = new ArrayList<double[]>(1024);
		double[] values;
        List<String> row;
		while ((row = importer.call().getValues()) != null) {
			values = parseRow(columns, row);
			list.add(values);
		}
		return list;
	}

    private double[] parseRow(int columns, List<String> row)
			throws ParseException {
		double[] values = new double[columns];
		for (int i = 0; i < columns; i++) {
			String s = row.get(i).toString();
			values[i] = ((Number) format.parseObject(s)).doubleValue();
		}
		return values;
	}

	@Override
    public ReshapeMatrix64F getMatrix() {
		return data.getMatrix();
	}

	@Override
	public void reshape(int numRows, int numCols, boolean saveValues) {
		data.reshape(numRows, numCols, saveValues);
	}

	@Override
	public void reshape(int numRows, int numCols) {
		data.reshape(numRows, numCols);
	}

	@Override
	public double get(int row, int col) {
		return data.get(row, col);
	}

	@Override
	public double unsafe_get(int row, int col) {
		return data.unsafe_get(row, col);
	}

	@Override
	public void set(int row, int col, double val) {
		data.set(row, col, val);
	}

	@Override
	public void unsafe_set(int row, int col, double val) {
		data.unsafe_set(row, col, val);
	}

	@Override
	public MatrixIterator iterator(boolean rowMajor, int minRow, int minCol,
			int maxRow, int maxCol) {
		return data.iterator(rowMajor, minRow, minCol, maxRow, maxCol);
	}

	@Override
	public int getNumRows() {
		return data.getNumRows();
	}

	@Override
	public int getNumCols() {
		return data.getNumCols();
	}

	@Override
	public void setNumRows(int numRows) {
		data.setNumRows(numRows);
	}

	@Override
	public void setNumCols(int numCols) {
		data.setNumCols(numCols);
	}

	@Override
	public int getNumElements() {
		return data.getNumElements();
	}

	@Override
    public void set(ReshapeMatrix64F A) {
		data.set(A);
	}

	@Override
    public <T extends ReshapeMatrix64F> T copy() {
		return data.copy();
	}

    @Override
	public void print() {
		data.print();
	}

	@Override
	public String toString() {
		return data.toString();
	}
}
