/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-data.
 *
 * globalpomutils-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-data is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-data. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.data;

/**
 * Data property.
 * 
 * @see DataBean
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public enum DataProperty {

	/**
	 * The number of columns property.
	 * 
	 * @see DataBean#reshape(int, int)
	 * @see DataBean#reshape(int, int, boolean)
	 * @see DataBean#setNumCols(int)
	 */
	COLUMNS,

	/**
	 * The number of rows property.
	 * 
	 * @see DataBean#reshape(int, int)
	 * @see DataBean#reshape(int, int, boolean)
	 * @see DataBean#setNumRows(int)
	 */
	ROWS,

	/**
	 * The data property.
	 * 
	 * @see DataBean#set(org.ejml.data.Matrix64F)
	 * @see DataBean#set(int, int, double)
	 */
	DATA
}
