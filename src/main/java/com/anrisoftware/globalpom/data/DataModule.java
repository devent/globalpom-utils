package com.anrisoftware.globalpom.data;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs matrix data factory.
 * 
 * @see MatrixData
 * @see MatrixDataFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class DataModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(MatrixData.class,
				MatrixData.class).build(MatrixDataFactory.class));
	}

}
