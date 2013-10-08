/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.AverageValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.Provider;

/**
 * Provides the value of the electron mass {@code  me=9.10938291e-31 kg} that
 * calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class AverageElectronMassProvider implements Provider<Value> {

	private static final double m = 9.10938291e-31;

	private static final int significant = 2;

	private static final double uncertainty = 0.00000040e-31;

	private static final int decimal = 8;

	@Inject
	private AverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(m, significant, uncertainty, decimal);
	}

}
