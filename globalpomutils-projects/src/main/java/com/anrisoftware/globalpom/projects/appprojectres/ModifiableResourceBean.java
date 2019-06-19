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
