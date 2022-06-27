/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import com.anrisoftware.propertiesutils.ContextProperties;

/**
 * Factory to create the threading properties from a properties file.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 3.1
 */
interface DefaultThreadingPropertiesFactory {

    /**
     * Creates the threading properties.
     *
     * @param properties the {@link ContextProperties} properties that were loaded.
     *
     * @param name       the {@link String} name of the thread pool.
     *
     * @return the {@link ThreadingProperties}.
     */
    ThreadingProperties create(ContextProperties properties, String name);
}
