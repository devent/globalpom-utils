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

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ejml.data.Matrix64F;
import org.ejml.data.MatrixIterator;

import com.google.inject.assistedinject.Assisted;

/**
 * Default generic data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class MatrixData implements Data, Serializable {

    private static final String COLUMNS = "columns";
    private static final String ROWS = "rows";

    private final Matrix64F matrix;

    /**
     * @see MatrixDataFactory#create(Matrix64F)
     */
    @Inject
    MatrixData(@Assisted Matrix64F matrix) {
        this.matrix = matrix;
    }

    @Override
    public Matrix64F getMatrix() {
        return matrix;
    }

    @Override
    public void reshape(int numRows, int numCols, boolean saveValues) {
        matrix.reshape(numRows, numCols, saveValues);
    }

    @Override
    public void reshape(int numRows, int numCols) {
        matrix.reshape(numRows, numCols);
    }

    @Override
    public int hashCode() {
        return matrix.hashCode();
    }

    @Override
    public double get(int row, int col) {
        return matrix.get(row, col);
    }

    @Override
    public double unsafe_get(int row, int col) {
        return matrix.unsafe_get(row, col);
    }

    @Override
    public void set(int row, int col, double val) {
        matrix.set(row, col, val);
    }

    @Override
    public void unsafe_set(int row, int col, double val) {
        matrix.unsafe_set(row, col, val);
    }

    @Override
    public boolean equals(Object obj) {
        return matrix.equals(obj);
    }

    @Override
    public MatrixIterator iterator(boolean rowMajor, int minRow, int minCol,
            int maxRow, int maxCol) {
        return matrix.iterator(rowMajor, minRow, minCol, maxRow, maxCol);
    }

    @Override
    public int getNumRows() {
        return matrix.getNumRows();
    }

    @Override
    public int getNumCols() {
        return matrix.getNumCols();
    }

    @Override
    public void setNumRows(int numRows) {
        matrix.setNumRows(numRows);
    }

    @Override
    public void setNumCols(int numCols) {
        matrix.setNumCols(numCols);
    }

    @Override
    public int getNumElements() {
        return matrix.getNumElements();
    }

    @Override
    public void set(Matrix64F A) {
        matrix.set(A);
    }

    @Override
    public <T extends Matrix64F> T copy() {
        return matrix.copy();
    }

    @Override
    public void print() {
        matrix.print();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ROWS, getNumRows())
                .append(COLUMNS, getNumCols()).toString();
    }

}
