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
package com.anrisoftware.globalpom.data.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.MatrixIterator;
import org.ejml.data.ReshapeMatrix64F;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

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

    private final ReshapeMatrix64F matrix;

    /**
     * @see MatrixDataFactory#create()
     */
    @AssistedInject
    MatrixData() {
        this.matrix = new DenseMatrix64F(1, 1);
    }

    /**
     * @see MatrixDataFactory#create(ReshapeMatrix64F)
     */
    @AssistedInject
    MatrixData(@Assisted ReshapeMatrix64F matrix) {
        this.matrix = matrix;
    }

    @Override
    public ReshapeMatrix64F getMatrix() {
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
    public void set(ReshapeMatrix64F A) {
        matrix.set(A);
    }

    @Override
    public <T extends ReshapeMatrix64F> T copy() {
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
