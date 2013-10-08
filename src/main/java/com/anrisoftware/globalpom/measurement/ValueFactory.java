/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.measurement;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create a new value with an uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
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
