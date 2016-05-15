/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-threads.
 *
 * globalpomutils-threads is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-threads is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-threads. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties.internal;

import java.util.concurrent.ThreadFactory;

import com.anrisoftware.globalpom.threads.external.core.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.external.core.ThreadsException;

/**
 * Threading properties.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 3.1
 */
interface ThreadingProperties {

    /**
     * Returns the {@link String} name of the thread pool.
     */
    String getName();

    /**
     * Returns the threading policy.
     *
     * @return the {@link ThreadingPolicy}.
     *
     * @throws NullPointerException
     *             if no threading policy was found.
     */
    ThreadingPolicy getPolicy();

    /**
     * Returns the threading policy.
     *
     * @param defaultValue
     *            the default {@link ThreadingPolicy}.
     *
     * @return the {@link ThreadingPolicy} or the default value.
     */
    ThreadingPolicy getPolicy(ThreadingPolicy defaultValue);

    /**
     * Returns the thread factory.
     *
     * @return the {@link ThreadFactory}.
     *
     * @throws ThreadsException
     *             if the thread factory could not be created from the property.
     *
     * @throws NullPointerException
     *             if no thread factory was found.
     */
    ThreadFactory getThreadFactory() throws ThreadsException;

    /**
     * Returns the thread factory.
     *
     * @param defaultValue
     *            the default {@link ThreadFactory}.
     *
     * @return the {@link ThreadFactory} or the default value.
     *
     * @throws ThreadsException
     *             if the thread factory could not be created from the property.
     */
    ThreadFactory getThreadFactory(ThreadFactory defaultValue)
            throws ThreadsException;

}
