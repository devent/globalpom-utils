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
