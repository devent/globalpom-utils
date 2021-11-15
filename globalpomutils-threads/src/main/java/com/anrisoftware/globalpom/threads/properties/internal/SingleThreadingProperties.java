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
package com.anrisoftware.globalpom.threads.properties.internal;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Single thread pool properties.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
class SingleThreadingProperties extends DefaultThreadingProperties {

    /**
     * @see SingleThreadingPropertiesFactory#create(ContextProperties, String)
     */
    @Inject
    SingleThreadingProperties(@Assisted ContextProperties p, @Assisted String name) {
        super(p, name);
    }

    /**
     * Create the single executor service.
     *
     * @param factory optional the {@link ThreadFactory}.
     *
     * @return the single {@link ExecutorService}.
     */
    public ExecutorService createExecutorService(ThreadFactory factory) {
        return factory == null ? newSingleThreadExecutor() : newSingleThreadExecutor(factory);
    }
}
