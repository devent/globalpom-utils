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
package com.anrisoftware.globalpom.projects.appprojectres;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;
import java.util.UUID;

/**
 * Project resource that is saved to a separate location.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface ProjectResource extends ModifiableResourceBean {

    /**
     * Returns the UUID of the resource.
     *
     * @return the {@link UUID}.
     */
    UUID getId();

    /**
     * Sets the resource name.
     *
     * @param name
     *            the {@link String} name.
     */
    void setName(String name);

    /**
     * Returns the resource name.
     *
     * @return the {@link String} name.
     */
    String getName();

    /**
     * Sets the resource location.
     *
     * @param location
     *            the {@link URI} location.
     */
    void setLocation(URI location);

    /**
     * Returns the resource location.
     *
     * @return the {@link URI} location.
     */
    URI getLocation();

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
     * @see ProjectResourceProperty
     */
    @Override
    void addPropertyChangeListener(PropertyChangeListener l);

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     * @see ProjectResourceProperty
     */
    @Override
    void addPropertyChangeListener(Object name, PropertyChangeListener l);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
     * @see ProjectResourceProperty
     */
    @Override
    void removePropertyChangeListener(PropertyChangeListener l);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     * @see ProjectResourceProperty
     */
    @Override
    void removePropertyChangeListener(Object name, PropertyChangeListener l);
}
