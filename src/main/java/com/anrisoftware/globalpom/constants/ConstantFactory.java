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
package com.anrisoftware.globalpom.constants;

import javax.measure.unit.Unit;

import com.anrisoftware.globalpom.measurement.Value;

/**
 * Factory to create physical constant.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ConstantFactory {

	/**
	 * Creates the physical constant with the specified value and unit.
	 * 
	 * @param value
	 *            the {@link Value}.
	 * 
	 * @param unit
	 *            the {@link Unit}.
	 * 
	 * @return the {@link Constant}.
	 */
	@SuppressWarnings("rawtypes")
	Constant create(Value value, Unit<?> unit);
}
