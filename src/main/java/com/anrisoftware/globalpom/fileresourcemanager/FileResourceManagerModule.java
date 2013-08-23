package com.anrisoftware.globalpom.fileresourcemanager;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the resource saver factory.
 * 
 * @see ResourceSaver
 * @see ResourceSaverFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
public class FileResourceManagerModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(ResourceSaver.class,
				ResourceSaver.class).build(ResourceSaverFactory.class));
	}

}
