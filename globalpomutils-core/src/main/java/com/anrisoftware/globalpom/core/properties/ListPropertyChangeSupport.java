/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anrisoftware.globalpom.core.properties;

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
