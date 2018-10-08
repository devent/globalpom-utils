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

import static com.anrisoftware.globalpom.data.spreadsheetimport.OpenDocumentImporterLogger.m.column_for_name_null;
import static org.apache.commons.lang3.Validate.notNull;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.data.dataimport.Column;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link OpenDocumentImporter}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Singleton
final class OpenDocumentImporterLogger extends AbstractLogger {

    enum m {

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
     * Sets the context of the logger to {@link OpenDocumentImporter}.
     */
    public OpenDocumentImporterLogger() {
        super(OpenDocumentImporter.class);
    }

    void checkColumnForName(Column column, String name) {
        notNull(column, column_for_name_null.toString(), name);
    }

}
