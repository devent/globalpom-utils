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
package com.anrisoftware.globalpom.constantsmap;

import java.text.ParseException;

import javax.inject.Inject;

import com.anrisoftware.globalpom.constants.Constant;
import com.anrisoftware.globalpom.format.constants.ConstantFormat;
import com.google.inject.assistedinject.Assisted;

/**
 * Provides physical constants.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class Constants {

	private final ConstantFormat format;

	@Inject
	private ConstantsResourceProvider resource;

	/**
	 * Sets the constant format to parse physical constants.
	 * 
	 * @param format
	 *            the {@link ConstantFormat}.
	 */
	@Inject
	Constants(@Assisted ConstantFormat format) {
		this.format = format;
	}

	/**
	 * Returns the constant with the specified name.
	 * 
	 * @param name
	 *            the constant name.
	 * 
	 * @return the physical {@link Constant} constant.
	 * 
	 * @throws ParseException
	 *             if there was an error parse the physical constant.
	 */
	public Constant<?> getConstant(String name) throws ParseException {
		return resource.get().getTypedProperty(name, format);
	}
}
