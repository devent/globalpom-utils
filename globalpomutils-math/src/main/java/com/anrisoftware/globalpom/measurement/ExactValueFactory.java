/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.measurement;

/**
 * Factory to create a new exact value.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface ExactValueFactory extends ValueFactory {

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
