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
package com.anrisoftware.globalpom.threads.properties.internal;

import static java.lang.String.format;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import jakarta.inject.Inject;

import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Fixed thread pool properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
class FixedThreadingProperties extends DefaultThreadingProperties {

    /**
     * Property name of number of maximum threads.
     */
    public static final String MAX_THREADS_PROPERTY = "max_threads";

    private final FixedThreadingPropertiesLogger log;

    /**
     * @see FixedThreadingPropertiesFactory#create(ContextProperties, String)
     */
    @Inject
    FixedThreadingProperties(FixedThreadingPropertiesLogger log, @Assisted ContextProperties p, @Assisted String name) {
        super(p, name);
        this.log = log;
    }

    /**
     * Returns the maximum number of threads for the pool.
     *
     * @return the maximum number of threads.
     *
     * @throws NullPointerException if no maximum number of threads property was
     *                              found.
     *
     * @see #MAX_THREADS_PROPERTY
     */
    public int getMaxThreads() {
        Number value = getProperties().getNumberProperty(format(KEY_TEMPLATE, getName(), MAX_THREADS_PROPERTY));
        log.checkMaxThreads(this, value);
        return value.intValue();
    }

    /**
     * Returns the maximum number of threads for the pool.
     *
     * @param defaultValue the default maximum number of threads.
     *
     * @return the maximum number of threads or the default value.
     *
     * @see #MAX_THREADS_PROPERTY
     */
    public int getMaxThreads(int defaultValue) {
        Number value = getProperties().getNumberProperty(format(KEY_TEMPLATE, getName(), MAX_THREADS_PROPERTY),
                defaultValue);
        return value.intValue();
    }

    /**
     * Create the fixed executor service.
     *
     * @param factory    optional the {@link ThreadFactory}.
     *
     * @param maxThreads maximum number of threads in the pool.
     *
     * @return the fixed {@link ExecutorService}.
     */
    public ExecutorService createExecutorService(ThreadFactory factory, int maxThreads) {
        return factory == null ? newFixedThreadPool(maxThreads) : newFixedThreadPool(maxThreads, factory);
    }
}
