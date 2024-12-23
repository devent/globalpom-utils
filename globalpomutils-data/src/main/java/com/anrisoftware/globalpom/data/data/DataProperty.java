/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

/**
 * Data property.
 *
 * @see DataBean
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public enum DataProperty {

    /**
     * The number of columns property.
     *
     * @see Data#reshape(int, int)
     * @see Data#reshape(int, int, boolean)
     * @see Data#setNumCols(int)
     */
    COLUMNS,

    /**
     * The number of rows property.
     *
     * @see Data#reshape(int, int)
     * @see Data#reshape(int, int, boolean)
     * @see Data#setNumRows(int)
     */
    ROWS,

    /**
     * The data property.
     *
     * @see Data#set(org.ejml.data.ReshapeMatrix64F)
     * @see Data#set(int, int, double)
     */
    DATA
}
