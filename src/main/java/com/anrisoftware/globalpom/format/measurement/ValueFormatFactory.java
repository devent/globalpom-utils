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
package com.anrisoftware.globalpom.format.measurement;

import java.text.NumberFormat;

import com.anrisoftware.globalpom.measurement.ExactValueFactory;
import com.anrisoftware.globalpom.measurement.ValueFactory;

/**
 * Factory to create a new value format.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ValueFormatFactory {

	/**
	 * Create value format that uses the default decimal format to format the
	 * value.
	 * 
	 * @param valueFactory
	 *            the {@link ValueFactory} to create the value.
	 * 
	 * @param exactValueFactory
	 *            the {@link ExactValueFactory} to create the exact value.
	 * 
	 * @return the {@link ValueFormat}.
	 */
	ValueFormat create(ValueFactory valueFactory,
			ExactValueFactory exactValueFactory);

	/**
	 * Create value format with the specified number format to format the value.
	 * 
	 * @param valueFactory
	 *            the {@link ValueFactory} to create the value.
	 * 
	 * @param exactValueFactory
	 *            the {@link ExactValueFactory} to create the exact value.
	 * 
	 * @param format
	 *            the {@link NumberFormat}.
	 * 
	 * @return the {@link ValueFormat}.
	 */
	ValueFormat create(ValueFactory valueFactory,
			ExactValueFactory exactValueFactory, NumberFormat format);
}
