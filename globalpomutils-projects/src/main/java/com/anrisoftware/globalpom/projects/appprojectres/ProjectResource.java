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
