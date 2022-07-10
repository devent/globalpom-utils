/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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

    /**
     *
     * @param sourceBean the source bean.
     */
    public ListPropertyChangeSupport(Object sourceBean) {
        this(sourceBean, new PropertyChangeSupport(sourceBean));
    }

    /**
     *
     * @param sourceBean the source bean.
     *
     * @param support    the {@link PropertyChangeSupport}
     */
    public ListPropertyChangeSupport(Object sourceBean, PropertyChangeSupport support) {
        this.sourceBean = sourceBean;
        this.support = support;
    }

    /**
     *
     * @param listener the {@link PropertyChangeListener}
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     *
     * @param listener the {@link PropertyChangeListener}
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     *
     * @return the array of {@link PropertyChangeListener}
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return support.getPropertyChangeListeners();
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @param listener     the {@link PropertyChangeListener}.
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @param listener     the {@link PropertyChangeListener}.
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @return the array of {@link PropertyChangeListener}
     */
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return support.getPropertyChangeListeners(propertyName);
    }

    /**
     *
     * @param property the property.
     *
     * @param oldValue the old value.
     *
     * @param newValue the new value.
     */
    public void firePropertyChange(Object property, Object oldValue, Object newValue) {
        support.firePropertyChange(property.toString(), oldValue, newValue);
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @param oldValue     the old value.
     *
     * @param newValue     the new value.
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     *
     * @param property the property.
     *
     * @param oldValue the old value.
     *
     * @param newValue the new value.
     */
    public void firePropertyChange(Object property, int oldValue, int newValue) {
        support.firePropertyChange(property.toString(), oldValue, newValue);
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @param oldValue     the old value.
     *
     * @param newValue     the new value.
     */
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     *
     * @param property the property.
     *
     * @param oldValue the old value.
     *
     * @param newValue the new value.
     */
    public void firePropertyChange(Object property, boolean oldValue, boolean newValue) {
        support.firePropertyChange(property.toString(), oldValue, newValue);
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @param oldValue     the old value.
     *
     * @param newValue     the new value.
     */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     *
     * @param event the {@link PropertyChangeEvent}
     */
    public void firePropertyChange(PropertyChangeEvent event) {
        support.firePropertyChange(event);
    }

    /**
     * @see ListItemAddedEvent#ListItemAddedEvent(Object, String, int, int, Object)
     *
     * @param propertyName the {@link String} property name.
     *
     * @param index0       the start index.
     *
     * @param index1       the end index.
     *
     * @param newValue     the new value.
     */
    public void fireListItemAddedChange(String propertyName, int index0, int index1, Object newValue) {
        firePropertyChange(new ListItemAddedEvent(sourceBean, propertyName, index0, index1, newValue));
    }

    /**
     * @see ListItemAddedEvent#ListItemAddedEvent(Object, String, int, int, Object)
     *
     * @param property the property.
     *
     * @param index0   the start index.
     *
     * @param index1   the end index.
     *
     * @param newValue the new value.
     */
    public void fireListItemAddedChange(Object property, int index0, int index1, Object newValue) {
        firePropertyChange(new ListItemAddedEvent(sourceBean, property.toString(), index0, index1, newValue));
    }

    /**
     * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
     *      Object)
     *
     * @param propertyName the {@link String} property name.
     *
     * @param index0       the start index.
     *
     * @param index1       the end index.
     *
     * @param oldValue     the old value.
     */
    public void fireListItemRemovedChange(String propertyName, int index0, int index1, Object oldValue) {
        firePropertyChange(new ListItemRemovedEvent(sourceBean, propertyName, index0, index1, oldValue));
    }

    /**
     * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
     *      Object)
     *
     * @param property the property.
     *
     * @param index0   the start index.
     *
     * @param index1   the end index.
     *
     * @param oldValue the old value.
     */
    public void fireListItemRemovedChange(Object property, int index0, int index1, Object oldValue) {
        firePropertyChange(new ListItemRemovedEvent(sourceBean, property.toString(), index0, index1, oldValue));
    }

    /**
     * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
     *      Object)
     *
     * @param propertyName the {@link String} property name.
     *
     * @param index0       the start index.
     *
     * @param index1       the end index.
     *
     * @param oldValue     the old value.
     *
     * @param newValue     the new value.
     */
    public void fireListItemChangedChange(String propertyName, int index0, int index1, Object oldValue,
            Object newValue) {
        firePropertyChange(new ListItemChangedEvent(sourceBean, propertyName, index0, index1, oldValue, newValue));
    }

    /**
     * @see ListItemRemovedEvent#ListItemRemovedEvent(Object, String, int, int,
     *      Object)
     *
     * @param property the property.
     *
     * @param index0   the start index.
     *
     * @param index1   the end index.
     *
     * @param oldValue the old value.
     *
     * @param newValue the new value.
     */
    public void fireListItemChangedChange(Object property, int index0, int index1, Object oldValue, Object newValue) {
        firePropertyChange(
                new ListItemChangedEvent(sourceBean, property.toString(), index0, index1, oldValue, newValue));
    }

    /**
     *
     * @param propertyName the {@link String} property name.
     *
     * @return has listeners.
     */
    public boolean hasListeners(String propertyName) {
        return support.hasListeners(propertyName);
    }

    @Override
    public String toString() {
        return support.toString();
    }

}
