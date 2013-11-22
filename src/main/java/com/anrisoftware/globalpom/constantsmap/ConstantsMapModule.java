package com.anrisoftware.globalpom.constantsmap;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Physical constants maps module.
 * 
 * @see ConstantsFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class ConstantsMapModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(Constants.class,
				Constants.class).build(ConstantsFactory.class));
	}

}
