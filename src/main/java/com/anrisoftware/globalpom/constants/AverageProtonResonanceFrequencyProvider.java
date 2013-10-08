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
 * Provides the value of the proton resonance frequency
 * {@code τ=1.42548625098e24 Hz} that calculates error propagation using simpler
 * average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class AverageProtonResonanceFrequencyProvider implements Provider<Value> {

	private static final double t = 1.42548625098e24;

	private static final int significant = 5;

	private static final double uncertainty = 0.00000011404e24;

	private static final int decimal = 11;

	@Inject
	private AverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(t, significant, uncertainty, decimal);
	}

}
