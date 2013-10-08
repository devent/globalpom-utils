/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Install measured values factories.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class MeasurementModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(Value.class,
				ExactAverageValue.class).build(ExactAverageValueFactory.class));
		install(new FactoryModuleBuilder().implement(Value.class,
				AverageValue.class).build(AverageValueFactory.class));
		install(new FactoryModuleBuilder().implement(Value.class,
				BaseValue.class).build(BaseValueFactory.class));
	}

}
