package com.anrisoftware.globalpom.dataimport;

/**
 * Factory to create default CSV importer properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface DefaultCsvImportPropertiesFactory {

	/**
	 * Creates the default CSV importer properties.
	 * 
	 * @return the {@link DefaultCsvImportProperties}.
	 */
	DefaultCsvImportProperties create();

	/**
	 * Creates the default CSV importer properties which values are copied from
	 * the specified properties.
	 * 
	 * @param properties
	 *            the {@link CsvImportProperties}.
	 * 
	 * @return the {@link DefaultCsvImportProperties}.
	 */
	DefaultCsvImportProperties create(CsvImportProperties properties);
}
