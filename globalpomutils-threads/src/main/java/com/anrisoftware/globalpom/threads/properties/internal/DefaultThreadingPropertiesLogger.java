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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.concurrent.ThreadFactory;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.threads.external.core.ThreadingPolicy;

/**
 * Logging messages for {@link DefaultThreadingProperties}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class DefaultThreadingPropertiesLogger extends AbstractLogger {

    enum m {

        threadFactoryNull("thread-factory != null for %s"),

        threadingPolicyNull("policy != null for %s");

        private String name;

        private m(String name) {
            this.name = name;
        }

        public String toString(Object... args) {
            return String.format(name, args);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Create logger for {@link DefaultThreadingProperties}.
     */
    public DefaultThreadingPropertiesLogger() {
        super(DefaultThreadingProperties.class);
    }

    void checkThreadFactory(DefaultThreadingProperties p, ThreadFactory value) {
        assertThat(m.threadFactoryNull.toString(p), value, notNullValue());
    }

    void checkThreadingPolicy(DefaultThreadingProperties p, ThreadingPolicy value) {
        assertThat(m.threadingPolicyNull.toString(p), value, notNullValue());
    }
}
