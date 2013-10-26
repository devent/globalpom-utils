package com.anrisoftware.globalpom.data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.ejml.data.Matrix64F;
import org.ejml.data.MatrixIterator;
import org.ejml.simple.SimpleMatrix;

import com.anrisoftware.globalpom.dataimport.CsvImportException;
import com.anrisoftware.globalpom.dataimport.CsvImporter;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Default generic data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class MatrixDataCsvImport implements Callable<MatrixDataCsvImport>,
		Data, Serializable {

	@Inject
	private MatrixDataCsvImportLogger log;

	private final Format format;

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
		Matrix64F matrix = createMatrix(rows);
		this.data = dataFactory.create(matrix);
		log.importedData(this, importer);
		this.importer = null;
		return this;
	}

	private Matrix64F createMatrix(List<double[]> rows) {
		SimpleMatrix matrix = new SimpleMatrix(rows.toArray(new double[][] {}));
		return matrix.getMatrix();
	}

	private List<double[]> parseValues() throws CsvImportException,
			ParseException {
		CsvImporter importer = this.importer;
		int columns = importer.getProperties().getNumCols();
		List<double[]> list = new ArrayList<double[]>(1024);
		double[] values;
		List<Object> row;
		while ((row = importer.call().getValues()) != null) {
			values = parseRow(columns, row);
			list.add(values);
		}
		return list;
	}

	private double[] parseRow(int columns, List<Object> row)
			throws ParseException {
		double[] values = new double[columns];
		for (int i = 0; i < columns; i++) {
			String s = row.get(i).toString();
			values[i] = ((Number) format.parseObject(s)).doubleValue();
		}
		return values;
	}

	@Override
	public Matrix64F getMatrix() {
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
	public void set(Matrix64F A) {
		data.set(A);
	}

	@Override
	public <T extends Matrix64F> T copy() {
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