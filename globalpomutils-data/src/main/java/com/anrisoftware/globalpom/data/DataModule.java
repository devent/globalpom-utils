/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.data;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs matrix data factory.
 * 
 * @see MatrixDataFactory
 * @see MatrixDataCsvImportFactory
 * @see DefaultDataBeanFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class DataModule extends AbstractModule {

	@Override
	protected void configure() {
        install(new FactoryModuleBuilder().implement(Data.class,
                MatrixData.class).build(MatrixDataFactory.class));
		install(new FactoryModuleBuilder().implement(MatrixDataCsvImport.class,
				MatrixDataCsvImport.class).build(
				MatrixDataCsvImportFactory.class));
		install(new FactoryModuleBuilder().implement(DefaultDataBean.class,
				DefaultDataBean.class).build(DefaultDataBeanFactory.class));
	}

}