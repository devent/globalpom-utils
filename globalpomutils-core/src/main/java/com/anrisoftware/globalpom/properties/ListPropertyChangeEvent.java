/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.properties;

import java.beans.PropertyChangeEvent;

/**
 * Event when a list property have been changed.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public abstract class ListPropertyChangeEvent extends PropertyChangeEvent {

	private final int index0;

	private final int index1;

	/**
	 * @see PropertyChangeEvent#PropertyChangeEvent(Object, String, Object,
	 *      Object)
	 *
	 * @param index0
	 *            the first index where the list was changed.
	 *
	 * @param index1
	 *            the last index where the list was changed.
	 */
	public ListPropertyChangeEvent(Object source, String propertyName,
			int index0, int index1, Object oldValue, Object newValue) {
		super(source, propertyName, oldValue, newValue);
		this.index0 = index0;
		this.index1 = index1;
	}

	/**
	 * Returns the first index where the list was changed.
	 *
	 * @return the first index.
	 */
	public int getIndex0() {
		return index0;
	}

	/**
	 * Returns the last index where the list was changed.
	 *
	 * @return the last index.
	 */
	public int getIndex1() {
		return index1;
	}

	/**
	 * Returns the type of list change event.
	 *
	 * @return the {@link ListPropertyChange}.
	 */
	public abstract ListPropertyChange getType();
}
