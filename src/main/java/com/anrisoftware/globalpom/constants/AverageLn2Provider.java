/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.constants;

import static org.apache.commons.math3.util.FastMath.log;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.ExactAverageValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.Provider;

/**
 * Provides the value of log(2) {@code log(2)≈0.693147180559945} that calculates
 * error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class AverageLn2Provider implements Provider<Value> {

	@Inject
	private ExactAverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(log(2.0));
	}

}
