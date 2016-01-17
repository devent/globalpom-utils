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
package com.anrisoftware.globalpom.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 * Enhancing the vetoable property change support for list property change
 * events.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class ListVetoPropertyChangeSupport {

	private final VetoableChangeSupport support;

	private final Object sourceBean;

	public ListVetoPropertyChangeSupport(Object sourceBean) {
		this(sourceBean, new VetoableChangeSupport(sourceBean));
	}

	public ListVetoPropertyChangeSupport(Object sourceBean,
			VetoableChangeSupport support) {
		this.sourceBean = sourceBean;
		this.support = support;
	}

	public VetoableChangeListener[] getVetoableChangeListeners() {
		return support.getVetoableChangeListeners();
	}

	public VetoableChangeListener[] getVetoableChangeListeners(
			String propertyName) {
		return support.getVetoableChangeListeners(propertyName);
	}

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		support.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		support.removeVetoableChangeListener(listener);
	}

	public void addVetoableChangeListener(String propertyName,
			VetoableChangeListener listener) {
		support.addVetoableChangeListener(propertyName, listener);
	}

	public void removeVetoableChangeListener(String propertyName,
			VetoableChangeListener listener) {
		support.removeVetoableChangeListener(propertyName, listener);
	}

	public void fireVetoableChange(String propertyName, Object oldValue,
			Object newValue) throws PropertyVetoException {
		support.fireVetoableChange(propertyName, oldValue, newValue);
	}

	public void fireVetoableChange(String propertyName, int oldValue,
			int newValue) throws PropertyVetoException {
		support.fireVetoableChange(propertyName, oldValue, newValue);
	}

	public void fireVetoableChange(String propertyName, boolean oldValue,
			boolean newValue) throws PropertyVetoException {
		support.fireVetoableChange(propertyName, oldValue, newValue);
	}

	public void fireVetoableChange(PropertyChangeEvent event)
			throws PropertyVetoException {
		support.fireVetoableChange(event);
	}

	/**
	 * @see ListItemAddedEvent#ListItemAddedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemAddedChange(String propertyName, int index0,
			int index1, Object newValue) throws PropertyVetoException {
		fireVetoableChange(new ListItemAddedEvent(sourceBean, propertyName,
				index0, index1, newValue));
	}

	/**
	 * @see ListItemAddedEvent#ListItemAddedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemAddedChange(Object property, int index0,
			int index1, Object newValue) throws PropertyVetoException {
		fireVetoableChange(new ListItemAddedEvent(sourceBean,
				property.toString(), index0, index1, newValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemRemovedChange(String propertyName, int index0,
			int index1, Object oldValue) throws PropertyVetoException {
		fireVetoableChange(new ListItemRemovedEvent(sourceBean, propertyName,
				index0, index1, oldValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemRemovedChange(Object property, int index0,
			int index1, Object oldValue) throws PropertyVetoException {
		fireVetoableChange(new ListItemRemovedEvent(sourceBean,
				property.toString(), index0, index1, oldValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemChangedChange(String propertyName, int index0,
			int index1, Object oldValue, Object newValue)
			throws PropertyVetoException {
		fireVetoableChange(new ListItemChangedEvent(sourceBean, propertyName,
				index0, index1, oldValue, newValue));
	}

	/**
	 * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
	 *      Object)
	 */
	public void fireListItemChangedChange(Object property, int index0,
			int index1, Object oldValue, Object newValue)
			throws PropertyVetoException {
		fireVetoableChange(new ListItemChangedEvent(sourceBean,
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
