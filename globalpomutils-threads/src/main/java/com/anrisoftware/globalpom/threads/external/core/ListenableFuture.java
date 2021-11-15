/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.threads.external.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Future;

/**
 * Informs property change listener when the task is finish.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface ListenableFuture<V> extends Future<V> {

    /**
     * The status of the task property.
     */
    public static final String STATUS_PROPERTY = "status";

    /**
     * Status of the task.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 1.5
     */
    public enum Status {

        /**
         * The task is currently running.
         */
        RUNNING,

        /**
         * The task is finished.
         */
        DONE,

        /**
         * The task is timed out.
         */
        TIMEOUT;
    }

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
     *
     * @param listener the {@link PropertyChangeListener}
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
     *
     * @param listener the {@link PropertyChangeListener}
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     *
     * @param propertyName the {@link String} property name.
     *
     * @param listener     the {@link PropertyChangeListener}
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     *
     * @param propertyName the {@link String} property name.
     *
     * @param listener     the {@link PropertyChangeListener}
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

}
