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
package com.anrisoftware.globalpom.data.csvimport;


import static com.anrisoftware.globalpom.data.csvimport.MatrixDataCsvImportLogger.m.imported_data_debug;
import static com.anrisoftware.globalpom.data.csvimport.MatrixDataCsvImportLogger.m.imported_data_trace;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link MatrixDataCsvImport}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class MatrixDataCsvImportLogger extends AbstractLogger {

    enum m {

        imported_data_debug("Matrix data imported from '{}', {} columns, {} rows."),

        imported_data_trace("Matrix data imported from '{}' {}.");

        private String name;

        private m(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Creates a logger for {@link MatrixDataCsvImport}.
     */
    public MatrixDataCsvImportLogger() {
        super(MatrixDataCsvImport.class);
    }

    void importedData(MatrixDataCsvImport data, CsvImporter importer) {
        if (isTraceEnabled()) {
            trace(imported_data_trace, importer.getProperties().getFile(), data);
        } else {
            debug(imported_data_debug, importer.getProperties().getFile(), data.getNumCols(), data.getNumRows());
        }
    }

}
