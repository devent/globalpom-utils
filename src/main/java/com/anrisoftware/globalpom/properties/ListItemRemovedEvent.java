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
package com.anrisoftware.globalpom.properties;

/**
 * Event when a list item was removed.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class ListItemRemovedEvent extends ListPropertyChangeEvent {

	/**
	 * @see ListPropertyChangeEvent#ListPropertyChangeEvent(Object, String, int,
	 *      int, Object, Object)
	 */
	public ListItemRemovedEvent(Object source, String propertyName, int index0,
			int index1, Object oldValue) {
		super(source, propertyName, index0, index1, oldValue, null);
	}

	@Override
	public ListPropertyChange getType() {
		return ListPropertyChange.REMOVED;
	}
}
