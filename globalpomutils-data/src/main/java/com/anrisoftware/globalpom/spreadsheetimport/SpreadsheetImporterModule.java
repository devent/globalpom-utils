package com.anrisoftware.globalpom.spreadsheetimport;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the spreadsheet import factories.
 *
 * @see DefaultSpreadsheetImportPropertiesFactory
 * @see OpenDocumentImporterFactory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
public class SpreadsheetImporterModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(
                DefaultSpreadsheetImportProperties.class,
                DefaultSpreadsheetImportProperties.class).build(
                DefaultSpreadsheetImportPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(SpreadsheetImporter.class,
                OpenDocumentImporter.class).build(
                OpenDocumentImporterFactory.class));
    }

}
