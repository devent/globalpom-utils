/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create value that calculates error propagation using simpler
 * average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface AverageValueFactory extends ValueFactory {

	/**
	 * @see ValueFactory#create(double, int, double, int, ValueFactory)
	 */
	Value create(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal);
}
