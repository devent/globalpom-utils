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

import static com.anrisoftware.globalpom.threads.external.core.ThreadingPolicy.parsePolicy;
import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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
public class DefaultThreadingProperties implements ThreadingProperties,
        Serializable {

    private static final String POOL_FACTORY_KEY = "pool_factory";

    private static final String POLICY_KEY = "policy";

    static final String KEY_TEMPLATE = "%s.%s";

    private final ContextProperties properties;

    private final String name;

    /**
     * @see DefaultThreadingPropertiesFactory#create(ContextProperties, String)
     */
    @Inject
    protected DefaultThreadingProperties(@Assisted ContextProperties p,
            @Assisted String name) {
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
        notNull(value, "policy = null");
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
        notNull(value, "thread-factory = null");
        return value;
    }

    @Override
    public ThreadFactory getThreadFactory(ThreadFactory defaultValue)
            throws ThreadsException {
        String value = properties.getProperty(format(KEY_TEMPLATE, name,
                POOL_FACTORY_KEY));
        return StringUtils.isEmpty(value) ? defaultValue
                : createFactory(getFactoryType(value));
    }

    @SuppressWarnings("unchecked")
    private Class<ThreadFactory> getFactoryType(String value)
            throws ThreadsException {
        try {
            return (Class<ThreadFactory>) Class.forName(value);
        } catch (ClassNotFoundException e) {
            throw new ThreadFactoryNotFoundException(e, value);
        }
    }

    private ThreadFactory createFactory(Class<? extends ThreadFactory> type)
            throws ThreadsException {
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
