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
		install(new FactoryModuleBuilder().implement(MatrixData.class,
				MatrixData.class).build(MatrixDataFactory.class));
		install(new FactoryModuleBuilder().implement(MatrixDataCsvImport.class,
				MatrixDataCsvImport.class).build(
				MatrixDataCsvImportFactory.class));
		install(new FactoryModuleBuilder().implement(DefaultDataBean.class,
				DefaultDataBean.class).build(DefaultDataBeanFactory.class));
	}

}
