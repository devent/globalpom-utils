/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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

import org.ejml.data.DenseMatrix64F;
import org.ejml.data.ReshapeMatrix64F;
import org.ejml.ops.CommonOps;

/**
 * Matrix data operations.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
public final class MatrixDataOps {

    /**
     * Copies a splice of data from this data.
     * 
     * @param dataFactory
     *            the {@link MatrixDataFactory}.
     * 
     * @param rowOffset
     *            the row offset.
     * 
     * @param rows
     *            the count of rows.
     * 
     * @param columnOffset
     *            the column offset.
     * 
     * @param columns
     *            the count of columns.
     * 
     * @return the {@link Data}.
     */
    public static Data copy(MatrixDataFactory dataFactory, Data source,
            int rowOffset, int rows, int columnOffset, int columns) {
        int srcX0 = columnOffset;
        int srcX1 = columnOffset + columns;
        int srcY0 = rowOffset;
        int srcY1 = rowOffset + rows;
        DenseMatrix64F m;
        ReshapeMatrix64F matrix = source.getMatrix();
        if (source.getMatrix() instanceof DenseMatrix64F) {
            m = (DenseMatrix64F) matrix;
        } else {
            m = new DenseMatrix64F(matrix);
        }
        ReshapeMatrix64F result;
        result = CommonOps.extract(m, srcY0, srcY1, srcX0, srcX1);
        return dataFactory.create(result);
    }

}
