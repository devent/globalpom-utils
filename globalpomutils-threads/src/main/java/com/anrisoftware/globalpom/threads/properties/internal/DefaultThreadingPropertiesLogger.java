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
