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

package com.anrisoftware.globalpom.threads.properties.internal;

/*-
 * #%L
 * Global POM Utilities :: Threads
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
