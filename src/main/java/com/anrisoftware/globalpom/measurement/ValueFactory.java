/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create a new value with an uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface ValueFactory {

	/**
	 * Creates a new value with an uncertainty.
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param significant
	 *            the significant figures of the value.
	 * 
	 * @param uncertainty
	 *            the uncertainty of the value.
	 * 
	 * @param decimal
	 *            the least significant decimal.
	 * 
	 * @param valueFactory
	 *            the {@link ValueFactory} to create values in the mathematical
	 *            operations.
	 * 
	 * @return the {@link Value}.
	 */
	Value create(@Assisted("value") double value,
			@Assisted("significant") int significant,
			@Assisted("uncertainty") double uncertainty,
			@Assisted("decimal") int decimal,
			@Assisted("valueFactory") ValueFactory valueFactory);
}
