/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.ExactAverageValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.Provider;

/**
 * Provides the value of speed of light in vacuum {@code c=299,792,458 ms^(−1)}
 * that calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class AverageSpeedLightProvider implements Provider<Value> {

	private static final double c = 299792458;

	@Inject
	private ExactAverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(c);
	}

}
