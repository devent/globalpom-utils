/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.data.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Informs property change listeners of change in the dimension or data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface DataBean extends Data {

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     * @see DataProperty
     *
     * @param property the {@link DataProperty}
     *
     * @param listener the {@link PropertyChangeListener}
     */
    void addPropertyChangeListener(DataProperty property, PropertyChangeListener listener);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     * @see DataProperty
     *
     * @param property the {@link DataProperty}
     *
     * @param listener the {@link PropertyChangeListener}
     */
    void removePropertyChangeListener(DataProperty property, PropertyChangeListener listener);

}
