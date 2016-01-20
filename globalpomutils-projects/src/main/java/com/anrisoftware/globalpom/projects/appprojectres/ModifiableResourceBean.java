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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Allows the resource to be marked as modified.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface ModifiableResourceBean {

    /**
     * Sets resource was marked as modified.
     *
     * @param modified
     *            {@code true} if the resource was marked as modified.
     */
    void setModified(boolean modified);

    /**
     * Returns resource was marked as modified.
     *
     * @return {@code true} if the resource was marked as modified.
     */
    boolean isModified();

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
     */
    void addPropertyChangeListener(PropertyChangeListener l);

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     */
    void addPropertyChangeListener(Object name, PropertyChangeListener l);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
     */
    void removePropertyChangeListener(PropertyChangeListener l);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     */
    void removePropertyChangeListener(Object name, PropertyChangeListener l);

}
