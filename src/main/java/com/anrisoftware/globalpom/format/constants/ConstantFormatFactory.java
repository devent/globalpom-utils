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
package com.anrisoftware.globalpom.format.constants;

import java.text.Format;
import java.text.NumberFormat;

import com.anrisoftware.globalpom.constants.ConstantFactory;

/**
 * Factory to create a new constant format.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ConstantFormatFactory {

	/**
	 * Create physical constant format that uses the default decimal format to
	 * format the value.
	 * 
	 * @param constantFactory
	 *            the {@link ConstantFactory} physical constants factory.
	 * 
	 * @param valueFormat
	 *            the {@link Format} to format the value.
	 * 
	 * @return the {@link ConstantFormat}.
	 */
	ConstantFormat create(ConstantFactory constantFactory, Format valueFormat);

	/**
	 * Create physical constant format with the specified number format to
	 * format the value.
	 * 
	 * @param constantFactory
	 *            the {@link ConstantFactory} physical constants factory.
	 * 
	 * @param valueFormat
	 *            the {@link Format} to format the value.
	 * 
	 * @param format
	 *            the {@link NumberFormat}.
	 * 
	 * @return the {@link ConstantFormat}.
	 */
	ConstantFormat create(ConstantFactory constantFactory, Format valueFormat,
			NumberFormat format);
}
