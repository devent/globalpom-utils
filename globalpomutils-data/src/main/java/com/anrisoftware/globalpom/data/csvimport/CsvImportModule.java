/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
 * Installs CSV importer factory.
 *
 * @see CsvImporter
 * @see CsvImporterFactory
 * @see MatrixDataCsvImportFactory
 * @see DefaultCsvImportPropertiesFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class CsvImportModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CsvImporter.class,
                CsvImporterImpl.class).build(CsvImporterFactory.class));
        install(new FactoryModuleBuilder().implement(
                DefaultCsvImportProperties.class,
                DefaultCsvImportProperties.class).build(
                DefaultCsvImportPropertiesFactory.class));
        install(new FactoryModuleBuilder().implement(MatrixDataCsvImport.class,
                MatrixDataCsvImport.class).build(
                MatrixDataCsvImportFactory.class));
    }

}
