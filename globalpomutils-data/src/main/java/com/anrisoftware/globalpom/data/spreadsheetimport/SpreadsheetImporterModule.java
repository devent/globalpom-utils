/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
