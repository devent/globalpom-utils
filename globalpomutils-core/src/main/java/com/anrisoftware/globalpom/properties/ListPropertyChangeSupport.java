/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Enhancing the property change support for list property change events.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class ListPropertyChangeSupport {

	private final PropertyChangeSupport support;

	private final Object sourceBean;

	public ListPropertyChangeSupport(Object sourceBean) {
		this(sourceBean, new PropertyChangeSupport(sourceBean));
	}

	public ListPropertyChangeSupport(Object sourceBean,
			PropertyChangeSupport support) {
		this.sourceBean = sourceBean;
		this.support = support;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return support.getPropertyChangeListeners();
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners(
			String propertyName) {
		return support.getPropertyChangeListeners(propertyName);
	}

	public void firePropertyChange(Object property, Object oldValue,
			Object newValue) {
		support.firePropertyChange(property.toString(), oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		support.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(Object property, int oldValue, int newValue) {
		support.firePropertyChange(property.toString(), oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, int oldValue,
			int newValue) {
		support.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(Object property, boolean oldValue,
			boolean newValue) {
		support.firePropertyChange(property.toString(), oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, boolean oldValue,
			boolean newValue) {
		support.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(PropertyChangeEvent event) {
		support.firePropertyChange(event);
	}

	/**
	 * @see ListItemAddedEvent#ListItemAddedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemAddedChange(String propertyName, int index0,
			int index1, Object newValue) {
		firePropertyChange(new ListItemAddedEvent(sourceBean, propertyName,
				index0, index1, newValue));
	}

	/**
	 * @see ListItemAddedEvent#ListItemAddedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemAddedChange(Object property, int index0,
			int index1, Object newValue) {
		firePropertyChange(new ListItemAddedEvent(sourceBean,
				property.toString(), index0, index1, newValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemRemovedChange(String propertyName, int index0,
			int index1, Object oldValue) {
		firePropertyChange(new ListItemRemovedEvent(sourceBean, propertyName,
				index0, index1, oldValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemRemovedChange(Object property, int index0,
			int index1, Object oldValue) {
		firePropertyChange(new ListItemRemovedEvent(sourceBean,
				property.toString(), index0, index1, oldValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemChangedChange(String propertyName, int index0,
			int index1, Object oldValue, Object newValue) {
		firePropertyChange(new ListItemChangedEvent(sourceBean, propertyName,
				index0, index1, oldValue, newValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemChangedChange(Object property, int index0,
			int index1, Object oldValue, Object newValue) {
		firePropertyChange(new ListItemChangedEvent(sourceBean,
				property.toString(), index0, index1, oldValue, newValue));
	}

	public boolean hasListeners(String propertyName) {
		return support.hasListeners(propertyName);
	}

	@Override
	public String toString() {
		return support.toString();
	}

}
