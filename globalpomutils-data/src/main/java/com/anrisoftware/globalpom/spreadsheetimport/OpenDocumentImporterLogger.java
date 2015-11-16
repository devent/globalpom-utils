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
