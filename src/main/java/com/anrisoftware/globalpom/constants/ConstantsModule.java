package com.anrisoftware.globalpom.constants;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Physical constants module.
 * 
 * @see StandardConstantFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class ConstantsModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(Constant.class,
				StandardConstant.class).build(StandardConstantFactory.class));
	}

}
