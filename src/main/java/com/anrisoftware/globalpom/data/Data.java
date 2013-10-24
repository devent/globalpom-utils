package com.anrisoftware.globalpom.data;

import org.ejml.data.Matrix64F;
import org.ejml.data.MatrixIterator;

/**
 * Access and modify generic data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface Data {

	/**
	 * Returns the matrix of the data.
	 * 
	 * @return the {@link Matrix64F}.
	 */
	Matrix64F getMatrix();

	/**
	 * @see Matrix64F#reshape(int, int, boolean)
	 */
	void reshape(int numRows, int numCols, boolean saveValues);

	/**
	 * @see Matrix64F#reshape(int, int)
	 */
	void reshape(int numRows, int numCols);

	/**
	 * @see Matrix64F#get(int, int)
	 */
	double get(int row, int col);

	/**
	 * @see Matrix64F#unsafe_get(int, int)
	 */
	double unsafe_get(int row, int col);

	/**
	 * @see Matrix64F#set(int, int, double)
	 */
	void set(int row, int col, double val);

	/**
	 * @see Matrix64F#unsafe_set(int, int, double)
	 */
	void unsafe_set(int row, int col, double val);

	/**
	 * @see Matrix64F#iterator(boolean, int, int, int, int)
	 */
	MatrixIterator iterator(boolean rowMajor, int minRow, int minCol,
			int maxRow, int maxCol);

	/**
	 * @see Matrix64F#getNumRows()
	 */
	int getNumRows();

	/**
	 * @see Matrix64F#getNumCols()
	 */
	int getNumCols();

	/**
	 * @see Matrix64F#setNumRows(int)
	 */
	void setNumRows(int numRows);

	/**
	 * @see Matrix64F#setNumCols(int)
	 */
	void setNumCols(int numCols);

	/**
	 * @see Matrix64F#getNumElements()
	 */
	int getNumElements();

	/**
	 * @see Matrix64F#set(Matrix64F)
	 */
	void set(Matrix64F A);

	/**
	 * @see Matrix64F#copy()
	 */
	<T extends Matrix64F> T copy();

	/**
	 * @see Matrix64F#print()
	 */
	void print();
}
