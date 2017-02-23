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
package com.anrisoftware.globalpom.data.spreadsheetimport;

/**
 * Factory to create a spreadsheet data exporter.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface SpreadsheetExporterFactory {

    /**
     * Create the exporter with the spreadsheet import properties.
     *
     * @param model
     *            the {@link SpreadsheetDataTableModel}.
     *
     * @param properties
     *            the {@link SpreadsheetImportProperties}.
     *
     * @return the {@link SpreadsheetExporter}.
     */
    SpreadsheetExporter create(SpreadsheetDataTableModel model,
            SpreadsheetImportProperties properties);
}
