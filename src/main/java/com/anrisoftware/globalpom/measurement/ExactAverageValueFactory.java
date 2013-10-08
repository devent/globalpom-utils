/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

/**
 * Factory to create a new exact value that calculates error propagation using
 * simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface ExactAverageValueFactory extends ValueFactory {

	/**
	 * @see #create(double, ValueFactory)
	 */
	Value create(double value);

	/**
	 * Creates a new exact value that calculates error propagation using simpler
	 * average errors.
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param valueFactory
	 *            the {@link ValueFactory} to create values in the mathematical
	 *            operations.
	 * 
	 * @return the exact {@link Value}.
	 */
	Value create(double value, ValueFactory valueFactory);
}
