/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.point;

import java.text.NumberFormat;

/**
 * Factory to create a new point format.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
public interface PointFormatFactory {

	/**
	 * Create point format that uses the default decimal format to format the X
	 * and Y coordinates of a point.
	 * 
	 * @return the {@link PointFormat}.
	 */
	PointFormat defaultFormat();

	/**
	 * Create point format with the specified decimal format to format the X and
	 * Y coordinates of a point.
	 * 
	 * @param format
	 *            the {@link NumberFormat}.
	 * 
	 * @return the {@link PointFormat}.
	 */
	PointFormat create(NumberFormat format);
}
