/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.dataimport;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs CSV importer factory.
 * 
 * @see CsvImporter
 * @see CsvImporterFactory
 * @see DefaultCsvImportPropertiesFactory
 * @see StringColumnFactory
 * @see TypedColumnFactory
 * @see BooleanColumnFactory
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
        install(new FactoryModuleBuilder().implement(Column.class,
                StringColumn.class).build(StringColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                TypedColumn.class).build(TypedColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                BooleanColumn.class).build(BooleanColumnFactory.class));
    }

}
