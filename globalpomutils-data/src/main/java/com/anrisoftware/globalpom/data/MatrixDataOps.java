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
