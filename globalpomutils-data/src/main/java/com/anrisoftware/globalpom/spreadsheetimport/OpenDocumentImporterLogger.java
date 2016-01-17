/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.spreadsheetimport;

import static com.anrisoftware.globalpom.spreadsheetimport.OpenDocumentImporterLogger._.column_for_name_null;
import static org.apache.commons.lang3.Validate.notNull;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.dataimport.Column;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link OpenDocumentImporter}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Singleton
final class OpenDocumentImporterLogger extends AbstractLogger {

    enum _ {

        column_for_name_null("No column found for '%s'.");

        private String name;

        private _(String name) {
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
