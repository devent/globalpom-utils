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
package com.anrisoftware.globalpom.format.degree;

/**
 * Creates the angular degree format.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface DegreeSexagesimalFormatFactory {

	/**
	 * Creates angular degree format.
	 * 
	 * @return the {@link DegreeSexagesimalFormat}.
	 */
	DegreeSexagesimalFormat create();

	/**
	 * Creates angular degree format.
	 * 
	 * @param decimal
	 *            the least significant decimal.
	 * 
	 * @return the {@link DegreeSexagesimalFormat}.
	 */
	DegreeSexagesimalFormat create(int decimal);
}
