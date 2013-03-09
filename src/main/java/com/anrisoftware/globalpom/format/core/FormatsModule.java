package com.anrisoftware.globalpom.format.core;

import com.anrisoftware.globalpom.format.point.PointFormat;
import com.anrisoftware.globalpom.format.point.PointFormatFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the format factories.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
public class FormatsModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(PointFormat.class,
				PointFormat.class).build(PointFormatFactory.class));
	}

}
