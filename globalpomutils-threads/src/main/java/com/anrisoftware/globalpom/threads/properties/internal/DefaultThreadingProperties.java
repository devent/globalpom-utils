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

import static com.anrisoftware.globalpom.threads.external.core.ThreadingPolicy.parsePolicy;
import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadFactory;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import com.anrisoftware.globalpom.threads.external.core.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.external.core.ThreadsException;
import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Loads threading properties from a properties file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@SuppressWarnings("serial")
public class DefaultThreadingProperties implements ThreadingProperties, Serializable {

    private static final String POOL_FACTORY_KEY = "pool_factory";

    private static final String POLICY_KEY = "policy";

    static final String KEY_TEMPLATE = "%s.%s";

    private final ContextProperties properties;

    private final String name;

    @Inject
    private transient DefaultThreadingPropertiesLogger log;

    /**
     * @see DefaultThreadingPropertiesFactory#create(ContextProperties, String)
     *
     * @param p    the {@link ContextProperties}
     *
     * @param name the {@link String} name.
     */
    @Inject
    protected DefaultThreadingProperties(@Assisted ContextProperties p, @Assisted String name) {
        this.properties = p;
        this.name = name;
    }

    protected ContextProperties getProperties() {
        return properties;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ThreadingPolicy getPolicy() {
        ThreadingPolicy value = getPolicy(null);
        log.checkThreadingPolicy(this, value);
        return value;
    }

    @Override
    public ThreadingPolicy getPolicy(ThreadingPolicy defaultValue) {
        String property = format(KEY_TEMPLATE, name, POLICY_KEY);
        String value = properties.getProperty(property);
        return StringUtils.isEmpty(value) ? defaultValue : parsePolicy(value);
    }

    @Override
    public ThreadFactory getThreadFactory() throws ThreadsException {
        ThreadFactory value = getThreadFactory(null);
        log.checkThreadFactory(this, value);
        return value;
    }

    @Override
    public ThreadFactory getThreadFactory(ThreadFactory defaultValue) throws ThreadsException {
        String value = properties.getProperty(format(KEY_TEMPLATE, name, POOL_FACTORY_KEY));
        return StringUtils.isEmpty(value) ? defaultValue : createFactory(getFactoryType(value));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @SuppressWarnings("unchecked")
    private Class<ThreadFactory> getFactoryType(String value) throws ThreadsException {
        try {
            return (Class<ThreadFactory>) Class.forName(value);
        } catch (ClassNotFoundException e) {
            throw new ThreadFactoryNotFoundException(e, value);
        }
    }

    private ThreadFactory createFactory(Class<? extends ThreadFactory> type) throws ThreadsException {
        try {
            return ConstructorUtils.invokeConstructor(type);
        } catch (NoSuchMethodException e) {
            throw new CreateThreadFactoryException(e, type);
        } catch (IllegalAccessException e) {
            throw new CreateThreadFactoryException(e, type);
        } catch (InvocationTargetException e) {
            throw new CreateThreadFactoryException(e, type);
        } catch (InstantiationException e) {
            throw new CreateThreadFactoryException(e, type);
        }
    }
}
