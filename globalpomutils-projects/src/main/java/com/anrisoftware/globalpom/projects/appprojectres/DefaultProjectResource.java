/**
 * Copyright © 2016 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.projects.appprojectres;

import static com.anrisoftware.globalpom.projects.appprojectres.ProjectResourceProperty.LOCATION_PROPERTY;
import static com.anrisoftware.globalpom.projects.appprojectres.ProjectResourceProperty.MODIFIED_PROPERTY;
import static com.anrisoftware.globalpom.projects.appprojectres.ProjectResourceProperty.NAME_PROPERTY;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * The project resource is identified by the resource UUID.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
@XStreamAlias("resource")
public class DefaultProjectResource implements ProjectResource, Serializable {

    private transient PropertyChangeSupport p;

    private final UUID id;

    private String name;

    private boolean modified;

    private URI location;

    /**
     * @see DefaultProjectResourceFactory#create()
     */
    @AssistedInject
    DefaultProjectResource() {
        this.id = UUID.randomUUID();
        readResolve();
    }

    /**
     * @see DefaultProjectResourceFactory#create(ProjectResource)
     */
    @AssistedInject
    DefaultProjectResource(@Assisted ProjectResource resource) {
        this.id = UUID.randomUUID();
        this.name = resource.getName();
        readResolve();
    }

    public void setPropertyChangeSupport(PropertyChangeSupport p) {
        this.p = p;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return p;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        p.firePropertyChange(NAME_PROPERTY.name(), oldValue, name);
        if (!StringUtils.equals(oldValue, name)) {
            setModified(true);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setLocation(URI location) {
        URI oldValue = this.location;
        this.location = location;
        p.firePropertyChange(LOCATION_PROPERTY.name(), oldValue, location);
    }

    @Override
    public URI getLocation() {
        return location;
    }

    @Override
    public void setModified(boolean modified) {
        boolean oldValue = this.modified;
        this.modified = modified;
        p.firePropertyChange(MODIFIED_PROPERTY.name(), oldValue, modified);
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        p.addPropertyChangeListener(l);
    }

    @Override
    public void addPropertyChangeListener(Object name, PropertyChangeListener l) {
        p.addPropertyChangeListener(name.toString(), l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        p.removePropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(Object name,
            PropertyChangeListener l) {
        p.removePropertyChangeListener(name.toString(), l);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private Object readResolve() {
        this.p = new PropertyChangeSupport(this);
        return this;
    }

}
