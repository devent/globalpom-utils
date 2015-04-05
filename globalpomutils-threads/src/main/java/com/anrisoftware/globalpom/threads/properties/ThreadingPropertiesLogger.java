/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.error_default_ctor;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.error_default_ctor_message;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.illegal_access;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.illegal_access_message;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.instantiation_error;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.instantiation_error_message;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.name_;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.no_default_constructor;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.no_default_constructor_message;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.thread_factory_not_found;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.thread_factory_not_found_message;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.thread_factory_null;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.threading_policy_null;
import static com.anrisoftware.globalpom.threads.properties.ThreadingPropertiesLogger._.type_;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.concurrent.ThreadFactory;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.threads.api.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.api.ThreadsException;

/**
 * Logging messages for {@link ThreadingProperties}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class ThreadingPropertiesLogger extends AbstractLogger {

    enum _ {

        instantiation_error_message("Instantiation error of thread factory {}."),

        instantiation_error("Instantiation error of thread factory"),

        error_default_ctor_message(
                "Error in default constructor of thread factory {}."),

        error_default_ctor("Error in default constructor of thread factory"),

        illegal_access_message(
                "Illegal access to default constructor of thread factory {}."),

        illegal_access(
                "Illegal access to default constructor of thread factory"),

        name_("name"),

        thread_factory_not_found_message("Thread factory {} not found"),

        thread_factory_not_found("Thread factory not found"),

        thread_factory_null("No thread factory found."),

        threading_policy_null("No threading policy found."),

        type_("type"),

        no_default_constructor_message(
                "No default constructor for type {} available."),

        no_default_constructor("No default constructor available");

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
     * Create logger for {@link ThreadingProperties}.
     */
    public ThreadingPropertiesLogger() {
        super(ThreadingProperties.class);
    }

    ThreadsException threadFactoryNotFound(ClassNotFoundException e,
            String value) {
        return logException(
                new ThreadsException(thread_factory_not_found, e).add(name_,
                        value), thread_factory_not_found_message, value);
    }

    ThreadsException noDefaultCtor(NoSuchMethodException e, Class<?> type) {
        return logException(
                new ThreadsException(no_default_constructor, e)
                        .add(type_, type),
                no_default_constructor_message, type);
    }

    ThreadsException illegalAccessCtor(IllegalAccessException e, Class<?> type) {
        return logException(
                new ThreadsException(illegal_access, e).add(type_, type),
                illegal_access_message, type);
    }

    ThreadsException exceptionCtor(Throwable e, Class<?> type) {
        return logException(
                new ThreadsException(error_default_ctor, e).add(type_, type),
                error_default_ctor_message, type);
    }

    ThreadsException instantiationErrorFactory(InstantiationException e,
            Class<?> type) {
        return logException(
                new ThreadsException(instantiation_error, e).add(type_, type),
                instantiation_error_message, type);
    }

    void checkPolicy(ThreadingProperties p, ThreadingPolicy value) {
        notNull(value, threading_policy_null.toString());
    }

    void checkThreadFactory(ThreadingProperties p, ThreadFactory value) {
        notNull(value, thread_factory_null.toString());
    }

}
