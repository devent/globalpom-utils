/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.projects.appproject;

import static com.anrisoftware.globalpom.projects.appprojectres.ProjectResourceProperty.LOCATION_PROPERTY;
import static com.anrisoftware.globalpom.projects.appprojectres.ProjectResourceProperty.MODIFIED_PROPERTY;
import static com.anrisoftware.globalpom.projects.appprojectres.ProjectResourceProperty.NAME_PROPERTY;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URI;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.projects.appprojectres.ProjectResource;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * The project is identified by the UUID.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@XStreamAlias("project")
public class DefaultProject extends Project implements Externalizable {

    private transient PropertyChangeSupport p;

    private final UUID id;

    private String name;

    private boolean modified;

    private URI location;

    /**
     * @see DefaultProjectFactory#create()
     */
    @AssistedInject
    public DefaultProject() {
        this.id = UUID.randomUUID();
        readResolve();
    }

    /**
     * @see DefaultProjectFactory#create(Project)
     */
    @AssistedInject
    DefaultProject(@Assisted Project resource) {
        this.id = UUID.randomUUID();
        this.name = resource.getName();
        this.location = resource.getLocation();
        readResolve();
    }

    private Object readResolve() {
        this.p = new PropertyChangeSupport(this);
        return this;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        p.firePropertyChange(NAME_PROPERTY.name(), oldValue, modified);
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
    public void copyInto(Object[] anArray) {
        super.copyInto(anArray);
        setModified(true);
    }

    @Override
    public void setSize(int newSize) {
        super.setSize(newSize);
        setModified(true);
    }

    @Override
    public void setElementAt(ProjectResource element, int index) {
        super.setElementAt(element, index);
        setModified(true);
    }

    @Override
    public void removeElementAt(int index) {
        super.removeElementAt(index);
        setModified(true);
    }

    @Override
    public void insertElementAt(ProjectResource element, int index) {
        super.insertElementAt(element, index);
        setModified(true);
    }

    @Override
    public void addElement(ProjectResource element) {
        super.addElement(element);
        setModified(true);
    }

    @Override
    public boolean removeElement(Object obj) {
        boolean removed = super.removeElement(obj);
        if (removed) {
            setModified(true);
        }
        return removed;
    }

    @Override
    public void removeAllElements() {
        super.removeAllElements();
        setModified(true);
    }

    @Override
    public ProjectResource set(int index, ProjectResource element) {
        ProjectResource res = super.set(index, element);
        setModified(true);
        return res;
    }

    @Override
    public void add(int index, ProjectResource element) {
        super.add(index, element);
        setModified(true);
    }

    @Override
    public ProjectResource remove(int index) {
        ProjectResource res = super.remove(index);
        setModified(true);
        return res;
    }

    @Override
    public void clear() {
        super.clear();
        setModified(true);
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        setModified(true);
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(location);
        int size = getSize();
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(getElementAt(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        this.name = (String) in.readObject();
        this.location = (URI) in.readObject();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            addElement((ProjectResource) in.readObject());
        }
    }
}
