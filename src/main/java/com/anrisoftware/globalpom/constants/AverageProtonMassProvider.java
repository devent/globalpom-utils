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
 * Provides the value of the proton mass {@code  mp=1.672621777e-27 kg} that
 * calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class AverageProtonMassProvider implements Provider<Value> {

	private static final double m = 1.672621777e-27;

	private static final int significant = 2;

	private static final double uncertainty = 0.000000074e-27;

	private static final int decimal = 9;

	@Inject
	private AverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(m, significant, uncertainty, decimal);
	}

}
