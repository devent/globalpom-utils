/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.data;

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
     */
    void reshape(int numRows, int numCols, boolean saveValues);

    /**
     * @see ReshapeMatrix64F#reshape(int, int)
     */
    void reshape(int numRows, int numCols);

    /**
     * @see ReshapeMatrix64F#get(int, int)
     */
    double get(int row, int col);

    /**
     * @see ReshapeMatrix64F#unsafe_get(int, int)
     */
    double unsafe_get(int row, int col);

    /**
     * @see ReshapeMatrix64F#set(int, int, double)
     */
    void set(int row, int col, double val);

    /**
     * @see ReshapeMatrix64F#unsafe_set(int, int, double)
     */
    void unsafe_set(int row, int col, double val);

    /**
     * @see ReshapeMatrix64F#iterator(boolean, int, int, int, int)
     */
    MatrixIterator iterator(boolean rowMajor, int minRow, int minCol,
            int maxRow, int maxCol);

    /**
     * @see ReshapeMatrix64F#getNumRows()
     */
    int getNumRows();

    /**
     * @see ReshapeMatrix64F#getNumCols()
     */
    int getNumCols();

    /**
     * @see ReshapeMatrix64F#setNumRows(int)
     */
    void setNumRows(int numRows);

    /**
     * @see ReshapeMatrix64F#setNumCols(int)
     */
    void setNumCols(int numCols);

    /**
     * @see ReshapeMatrix64F#getNumElements()
     */
    int getNumElements();

    /**
     * @see ReshapeMatrix64F#set(ReshapeMatrix64F)
     */
    void set(ReshapeMatrix64F A);

    /**
     * @see ReshapeMatrix64F#copy()
     * 
     * @since 1.10
     */
    <T extends ReshapeMatrix64F> T copy();

    /**
     * @see ReshapeMatrix64F#print()
     */
    void print();
}
