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
package com.anrisoftware.globalpom.threads.properties;

import static com.anrisoftware.globalpom.threads.properties.PropertiesThreadsLogger._.invalid_policy;
import static com.anrisoftware.globalpom.threads.properties.PropertiesThreadsLogger._.invalid_policy_message;
import static com.anrisoftware.globalpom.threads.properties.PropertiesThreadsLogger._.name_null;
import static com.anrisoftware.globalpom.threads.properties.PropertiesThreadsLogger._.policy_name;
import static com.anrisoftware.globalpom.threads.properties.PropertiesThreadsLogger._.properties_null;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Properties;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.threads.api.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.api.ThreadsException;

/**
 * Logging messages for {@link PropertiesThreads}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class PropertiesThreadsLogger extends AbstractLogger {

    enum _ {

        invalid_policy("Invalid thread pool policy"),

        invalid_policy_message("Invalid thread pool policy {}."),

        policy_name("policy"),

        name_null("name"),

        properties_null("properties");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Create logger for {@link PropertiesThreads}.
     */
    public PropertiesThreadsLogger() {
        super(PropertiesThreads.class);
    }

    ThreadsException invalidPolicy(PropertiesThreads threads,
            ThreadingPolicy policy) {
        return logException(
                new ThreadsException(invalid_policy).add(policy_name, policy),
                invalid_policy_message, policy);
    }

    void checkName(PropertiesThreads threads, String name) {
        notBlank(name, name_null.toString());
    }

    void checkProperties(PropertiesThreads threads, Properties properties) {
        notNull(properties, properties_null.toString());
    }
}
