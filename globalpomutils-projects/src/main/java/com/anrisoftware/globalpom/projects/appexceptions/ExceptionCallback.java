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
