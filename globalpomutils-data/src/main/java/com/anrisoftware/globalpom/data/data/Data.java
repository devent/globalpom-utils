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
package com.anrisoftware.globalpom.data.data;

import org.ejml.data.MatrixIterator;
import org.ejml.data.ReshapeMatrix64F;

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
     * @return the {@link ReshapeMatrix64F}.
     *
     * @since 1.10
     */
    ReshapeMatrix64F getMatrix();

    /**
     * @see ReshapeMatrix64F#reshape(int, int, boolean)
     *
     * @param numRows    the number of rows.
     *
     * @param numCols    the number of columns.
     *
     * @param saveValues the save values flag.
     */
    void reshape(int numRows, int numCols, boolean saveValues);

    /**
     * @see ReshapeMatrix64F#reshape(int, int)
     *
     * @param numRows the number of rows.
     *
     * @param numCols the number of columns.
     */
    void reshape(int numRows, int numCols);

    /**
     * @see ReshapeMatrix64F#get(int, int)
     *
     * @param row the row.
     *
     * @param col the column.
     *
     * @return the value.
     */
    double get(int row, int col);

    /**
     * @see ReshapeMatrix64F#unsafe_get(int, int)
     *
     * @param row the row.
     *
     * @param col the column.
     *
     * @return the value.
     */
    double unsafe_get(int row, int col);

    /**
     * @see ReshapeMatrix64F#set(int, int, double)
     *
     * @param row the row.
     *
     * @param col the column.
     *
     * @param val the value.
     */
    void set(int row, int col, double val);

    /**
     * @see ReshapeMatrix64F#unsafe_set(int, int, double)
     *
     * @param row the row.
     *
     * @param col the column.
     *
     * @param val the value.
     */
    void unsafe_set(int row, int col, double val);

    /**
     * @see ReshapeMatrix64F#iterator(boolean, int, int, int, int)
     *
     * @param rowMajor the row major flag.
     *
     * @param minRow   the minimum row.
     *
     * @param minCol   the minimum column.
     *
     * @param maxRow   the maximum row.
     *
     * @param maxCol   the maximum column.
     *
     * @return the {@link MatrixIterator}
     */
    MatrixIterator iterator(boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol);

    /**
     * @see ReshapeMatrix64F#getNumRows()
     *
     * @return the number of rows.
     */
    int getNumRows();

    /**
     * @see ReshapeMatrix64F#getNumCols()
     *
     * @return the number of columns.
     */
    int getNumCols();

    /**
     * @see ReshapeMatrix64F#setNumRows(int)
     *
     * @param numRows the number of rows.
     */
    void setNumRows(int numRows);

    /**
     * @see ReshapeMatrix64F#setNumCols(int) the number of columns.
     *
     * @param numCols the number of columns.
     */
    void setNumCols(int numCols);

    /**
     * @see ReshapeMatrix64F#getNumElements()
     *
     * @return the number of elements.
     */
    int getNumElements();

    /**
     * @see ReshapeMatrix64F#set(ReshapeMatrix64F)
     *
     * @param A the {@link ReshapeMatrix64F}
     */
    void set(ReshapeMatrix64F A);

    /**
     * @see ReshapeMatrix64F#copy()
     *
     * @param <T> the type of the {@link ReshapeMatrix64F}
     *
     * @return the {@link ReshapeMatrix64F}
     *
     * @since 1.10
     */
    <T extends ReshapeMatrix64F> T copy();

    /**
     * @see ReshapeMatrix64F#print()
     */
    void print();

}
