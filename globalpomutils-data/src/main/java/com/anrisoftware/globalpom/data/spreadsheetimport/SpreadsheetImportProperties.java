/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

package com.anrisoftware.globalpom.data.spreadsheetimport;

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

import java.net.URI;

/**
 * Properties to import a spreadsheet file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface SpreadsheetImportProperties {

    /**
     * Returns the file to import.
     *
     * @return the {@link URI} of the file resource.
     */
    URI getFile();

    /**
     * Returns the sheet number to import.
     *
     * @return the {@link Integer} sheet number.
     */
    int getSheetNumber();

    /**
     * Returns the indices of the columns to import.
     *
     * @return the integer array of the indices.
     */
    int[] getColumns();

    /**
     * Returns the index of the start row of the data.
     *
     * @return the start row {@link Integer} index.
     */
    int getStartRow();

    /**
     * Returns the index of the end row of the data.
     *
     * @return the end row {@link Integer} index.
     */
    int getEndRow();

    /**
     * Returns if the data from start row to end row have a header row.
     *
     * @return {@code true} if have a header row.
     */
    boolean isHaveHeader();
}
