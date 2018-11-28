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
package com.anrisoftware.globalpom.projects.appexceptions;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Called if an exception occurred on a different thread.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class ExceptionCallback {

    private final Object parent;

    private AppException exception;

    /**
     * Sets the parent thread to be notified if an exception occured.
     *
     * @param parent
     *            the {@link Object} parent.
     */
    public ExceptionCallback(Object parent) {
        notNull(parent);
        this.parent = parent;
    }

    /**
     * Exception occurred on a different thread. Notifies the parent thread.
     *
     * @param e
     *            the {@link AppException}.
     */
    public void exceptionOccurred(AppException e) {
        this.exception = e;
        synchronized (parent) {
            parent.notifyAll();
        }
    }

    /**
     * Returns the exception occurred on a different thread.
     *
     * @return the {@link AppException} or {@code null}.
     */
    public AppException getOccurredException() {
        return exception;
    }

    /**
     * Returns if an exception occurred on a different thread.
     *
     * @return {@code true} if an exception occurred.
     */
    public boolean didExceptionOccurred() {
        return exception != null;
    }
}
