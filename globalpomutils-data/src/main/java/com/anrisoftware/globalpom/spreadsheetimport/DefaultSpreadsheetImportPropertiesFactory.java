package com.anrisoftware.globalpom.spreadsheetimport;

/**
 * Factory to create the default spreadsheet import properties.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
public interface DefaultSpreadsheetImportPropertiesFactory {

    /**
     * Creates the default spreadsheet import properties.
     *
     * @return the {@link DefaultSpreadsheetImportProperties}.
     */
    DefaultSpreadsheetImportProperties create();

    /**
     * Creates the default spreadsheet import properties from the specified
     * properties.
     *
     * @param properties
     *            the {@link SpreadsheetImportProperties}.
     *
     * @return the {@link DefaultSpreadsheetImportProperties}.
     */
    DefaultSpreadsheetImportProperties create(
            SpreadsheetImportProperties properties);
}
