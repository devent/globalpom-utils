/*
 * Copyright 2015-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.projects.appresources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.fileresourcemanager.Resource;
import com.anrisoftware.globalpom.threads.api.ListenableFuture;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Application resources, saved and loaded from/to the configuration file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
@XStreamAlias("application")
public abstract class AbstractAppResources implements Serializable, Resource,
        Cloneable {

    private static final String VALUE = "value";

    private static final String OLD_VALUE = "old value";

    private static final String PROPERTY = "property";

    private transient Map<String, Object> properties;

    private transient PropertyChangeSupport p;

    @Inject
    private transient Provider<XStream> stream;

    @Inject
    private transient AppProjects projects;

    @Inject
    private transient AppResourcesSaverFactory saverFactory;

    private transient Threads threads;

    private transient boolean storedPropertiesLocked;

    private transient PropertyChangeListener resourcesSavedListener;

    @Inject
    private AppResourcesLogger log;

    @XStreamAlias("properties")
    private Map<String, Object> storedProperties;

    private String appConfigFileName;

    protected AbstractAppResources() {
        readResolve();
    }

    public Object readResolve() {
        this.storedPropertiesLocked = true;
        this.p = new PropertyChangeSupport(this);
        this.properties = new ConcurrentHashMap<String, Object>();
        this.resourcesSavedListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                checkResourcesSaved((ListenableFuture<?>) evt.getSource());
            }

        };
        if (storedProperties == null) {
            this.storedProperties = new ConcurrentHashMap<String, Object>();
        }
        return this;
    }

    /**
     * Sets the application configuration file name, for example
     * {@code ".app.xml"}
     *
     * @param name
     *            the {@link String} name.
     */
    public void setAppConfigFileName(String name) {
        this.appConfigFileName = name;
    }

    @Override
    public String getName() {
        return appConfigFileName;
    }

    /**
     * Sets the threads pool to run the property change listeners events on.
     *
     * @param threads
     *            the {@link Threads}.
     */
    public void setThreads(Threads threads) {
        this.threads = threads;
    }

    /**
     * Lock the application resources so it's not saved on property change.
     *
     * @param locked
     *            set to {@code true} to lock the application resources.
     */
    public void setStoredPropertiesLocked(boolean locked) {
        this.storedPropertiesLocked = locked;
    }

    /**
     * Sets the application property.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param value
     *            the property {@link Object} value.
     */
    public void setProperty(Object property, Object value) {
        setProperty(property, value, true);
    }

    /**
     * Sets the application property.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param value
     *            the property {@link Object} value.
     *
     * @param thread
     *            set to {@code true} to execute on an extra thread.
     */
    public void setProperty(Object property, Object value, boolean thread) {
        Object oldValue = getProperty(property);
        if (value == null) {
            properties.remove(property.toString());
        } else {
            properties.put(property.toString(), value);
        }
        if (thread) {
            firePropertyChangeOnThread(property, oldValue, value);
        } else {
            p.firePropertyChange(property.toString(), oldValue, value);
        }
    }

    /**
     * Sets the application property.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param oldValue
     *            the old {@link Object} value.
     *
     * @param value
     *            the {@link Object} value.
     */
    public void setProperty(Object property, Object oldValue, Object value) {
        setProperty(property, oldValue, value, true);
    }

    /**
     * Sets the application property.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param oldValue
     *            the old {@link Object} value.
     *
     * @param value
     *            the {@link Object} value.
     *
     * @param thread
     *            set to {@code true} to execute on an extra thread.
     */
    public void setProperty(Object property, Object oldValue, Object value,
            boolean thread) {
        if (value == null) {
            properties.remove(property.toString());
        } else {
            properties.put(property.toString(), value);
        }
        if (thread) {
            firePropertyChangeOnThread(property, oldValue, value);
        } else {
            p.firePropertyChange(property.toString(), oldValue, value);
        }
    }

    /**
     * Returns the application property.
     *
     * @param name
     *            the {@link Object} property.
     *
     * @return the application {@link Object} property.
     */
    @SuppressWarnings("unchecked")
    public <T> T getProperty(Object name) {
        return (T) properties.get(name.toString());
    }

    /**
     * Sets the property. The property is stored in the application
     * configuration file.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param value
     *            the property {@link Object} value.
     */
    public void setStoredPropertyOnThread(Object property, Object value) {
        Object oldValue = getStoredProperty(property);
        if (value == null) {
            storedProperties.remove(property.toString());
        } else {
            storedProperties.put(property.toString(), value);
        }
        firePropertyChangeOnThread(property, oldValue, value);
        saveAppResources();
    }

    /**
     * Sets the property. The property is stored in the application
     * configuration file.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param value
     *            the property {@link Object} value.
     */
    public void setStoredProperty(Object property, Object value) {
        Object oldValue = getStoredProperty(property);
        if (value == null) {
            storedProperties.remove(property.toString());
        } else {
            storedProperties.put(property.toString(), value);
        }
        p.firePropertyChange(property.toString(), oldValue, value);
        saveAppResources();
    }

    /**
     * Sets the property. The property is stored in the application
     * configuration file.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param oldValue
     *            the {@link Object} old value.
     *
     * @param value
     *            the property {@link Object} value.
     */
    public void setStoredPropertyOnThread(Object property, Object oldValue,
            Object value) {
        if (value == null) {
            storedProperties.remove(property.toString());
        } else {
            storedProperties.put(property.toString(), value);
        }
        firePropertyChangeOnThread(property, oldValue, value);
        saveAppResources();
    }

    /**
     * Sets the property. The property is stored in the application
     * configuration file.
     * <p>
     * <h2>Property</h2>
     * <p>
     * Informs the property change listeners that a new value for the property
     * was set with property name. The <i>old value</i> is set to the old value
     * or {@code null} and the <i>new value</i> is set to the new value.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param oldValue
     *            the {@link Object} old value.
     *
     * @param value
     *            the property {@link Object} value.
     */
    public void setStoredProperty(Object property, Object oldValue, Object value) {
        if (value == null) {
            storedProperties.remove(property.toString());
        } else {
            storedProperties.put(property.toString(), value);
        }
        p.firePropertyChange(property.toString(), oldValue, value);
        saveAppResources();
    }

    /**
     * Returns the current stored property.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @return the stored property.
     */
    @SuppressWarnings("unchecked")
    public <T> T getStoredProperty(Object property) {
        return (T) storedProperties.get(property.toString());
    }

    /**
     * Returns the current stored property.
     *
     * @param property
     *            the {@link Object} property.
     *
     * @param defaultValue
     *            the default value.
     *
     * @return the stored property or the default value if the property is
     *         {@code null}.
     */
    @SuppressWarnings("unchecked")
    public <T> T getStoredProperty(Object property, T defaultValue) {
        Object value = storedProperties.get(property.toString());
        return value == null ? defaultValue : (T) value;
    }

    /**
     * Returns the application projects.
     *
     * @return the {@link AppProjects}.
     */
    public AppProjects getProjects() {
        return projects;
    }

    @Override
    public void save(OutputStream output) throws Exception {
        try {
            AbstractAppResources clone = (AbstractAppResources) this.clone();
            stream.get().toXML(clone, output);
            output.flush();
        } catch (XStreamException e) {
            throw new IOException(e);
        }
    }

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     */
    public void addPropertyChangeListener(Object property,
            PropertyChangeListener listener) {
        p.addPropertyChangeListener(property.toString(), listener);
    }

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     */
    public void removePropertyChangeListener(Object property,
            PropertyChangeListener listener) {
        p.removePropertyChangeListener(property.toString(), listener);
    }

    /**
     * Sets the exception thrown in the property change thread.
     *
     * @param ex
     *            the {@link PropertyChangeException}.
     */
    protected void setPropertyChangeException(PropertyChangeException ex) {
    }

    private void firePropertyChangeOnThread(final Object property,
            final Object oldValue, final Object value) {
        threads.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    p.firePropertyChange(property.toString(), oldValue, value);
                } catch (Throwable e) {
                    try {
                        log.errorPropertyChange(property, oldValue, value, e);
                    } catch (PropertyChangeException ex) {
                        setPropertyChangeException(ex);
                    }
                }
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this).append(PROPERTY, property)
                        .append(OLD_VALUE, oldValue).append(VALUE, value)
                        .toString();
            }
        });
    }

    private void saveAppResources() {
        if (storedPropertiesLocked) {
            return;
        }
        threads.submit(saverFactory.create(this), resourcesSavedListener);
    }

    private void checkResourcesSaved(ListenableFuture<?> future) {
        try {
            future.get();
        } catch (InterruptedException e) {
            log.errorSaveResources(e);
        } catch (ExecutionException e) {
            log.errorSaveResources(e.getCause());
        }
    }

}
