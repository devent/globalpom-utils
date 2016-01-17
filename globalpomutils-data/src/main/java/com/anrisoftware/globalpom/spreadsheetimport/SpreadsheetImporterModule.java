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

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the spreadsheet import factories.
 *
 * @see DefaultSpreadsheetDataTableModelFactory
 * @see DefaultSpreadsheetImportPropertiesFactory
 * @see OpenDocumentImporterFactory
 * @see OpenDocumentExporterFactory
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
        install(new FactoryModuleBuilder().implement(
                DefaultSpreadsheetDataTableModel.class,
                DefaultSpreadsheetDataTableModel.class).build(
                DefaultSpreadsheetDataTableModelFactory.class));
        install(new FactoryModuleBuilder().implement(SpreadsheetExporter.class,
                OpenDocumentExporter.class).build(
                OpenDocumentExporterFactory.class));
    }

}
