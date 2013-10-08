/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.constants;

import static org.apache.commons.math3.util.FastMath.PI;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.ExactAverageValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.Provider;

/**
 * Provides the value of Pi {@code π≈3.141592653589793} that calculates error
 * propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class AveragePiProvider implements Provider<Value> {

	@Inject
	private ExactAverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(PI);
	}

}
