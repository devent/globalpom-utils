/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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


import static com.anrisoftware.globalpom.data.csvimport.CsvImporterImplLogger.m.column_for_name_null;
import static org.apache.commons.lang3.Validate.notNull;

import com.anrisoftware.globalpom.data.dataimport.Column;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link CsvImporterImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class CsvImporterImplLogger extends AbstractLogger {

    enum m {

        importer("importer"),

        error_open_file_message("Error open file '{}'."),

        error_open_file("Error open file"),

        error_read("Error read file"),

        error_read_message("Error read file '{}'."),

        column_for_name_null("No column found for '%s'.");

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
     * Creates a logger for {@link CsvImporterImpl}.
     */
    public CsvImporterImplLogger() {
        super(CsvImporterImpl.class);
    }

    void checkColumnForName(Column column, String name) {
        notNull(column, column_for_name_null.toString(), name);
    }

}
