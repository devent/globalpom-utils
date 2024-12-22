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
package com.anrisoftware.globalpom.data.spreadsheetimport;

import java.io.File;

/**
 * Export data to spreadsheet.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface SpreadsheetExporter {

    /**
     * Writes the data to the spreadsheet.
     *
     * @param file the {@link File}.
     *
     * @return this {@link SpreadsheetExporter}.
     *
     * @throws SpreadsheetExportException if there was an error exporting.
     */
    SpreadsheetExporter write(File file) throws SpreadsheetExportException;

    /**
     * Returns the import properties.
     *
     * @return the {@link SpreadsheetImportProperties}.
     */
    SpreadsheetImportProperties getProperties();

}
