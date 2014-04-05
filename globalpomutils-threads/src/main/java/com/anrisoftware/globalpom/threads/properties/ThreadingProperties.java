/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.threads.api.ThreadingPolicy.parsePolicy;
import static java.lang.String.format;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import com.anrisoftware.globalpom.threads.api.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.api.ThreadsException;
import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Loads threading properties from a properties file.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
public class ThreadingProperties extends Properties {

	private static final String POOL_FACTORY_KEY = "pool_factory";

	private static final String POLICY_KEY = "policy";

	static final String KEY_TEMPLATE = "%s.%s";

	private ThreadingPropertiesLogger log;

	private final ContextProperties properties;

	private final String name;

	/**
	 * @see ThreadingPropertiesFactory#create(ContextProperties, String)
	 */
	@Inject
	protected ThreadingProperties(@Assisted ContextProperties p,
			@Assisted String name) {
		this.properties = p;
		this.name = name;
	}

	@Inject
	void setThreadingPropertiesLogger(ThreadingPropertiesLogger log) {
		this.log = log;
	}

	protected ContextProperties getProperties() {
		return properties;
	}

	public String getName() {
		return name;
	}

	/**
	 * Returns the threading policy.
	 * 
	 * @return the {@link ThreadingPolicy}.
	 * 
	 * @throws NullPointerException
	 *             if no threading policy was found.
	 */
	public ThreadingPolicy getPolicy() {
		ThreadingPolicy value = getPolicy(null);
		log.checkPolicy(this, value);
		return value;
	}

	/**
	 * Returns the threading policy.
	 * 
	 * @param defaultValue
	 *            the default {@link ThreadingPolicy}.
	 * 
	 * @return the {@link ThreadingPolicy} or the default value.
	 */
	public ThreadingPolicy getPolicy(ThreadingPolicy defaultValue) {
		String property = format(KEY_TEMPLATE, name, POLICY_KEY);
		String value = properties.getProperty(property);
		return StringUtils.isEmpty(value) ? defaultValue : parsePolicy(value);
	}

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
	public ThreadFactory getThreadFactory() throws ThreadsException {
		ThreadFactory value = getThreadFactory(null);
		log.checkThreadFactory(this, value);
		return value;
	}

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
			throw log.threadFactoryNotFound(e, value);
		}
	}

	private ThreadFactory createFactory(Class<? extends ThreadFactory> type)
			throws ThreadsException {
		try {
			return ConstructorUtils.invokeConstructor(type);
		} catch (NoSuchMethodException e) {
			throw log.noDefaultCtor(e, type);
		} catch (IllegalAccessException e) {
			throw log.illegalAccessCtor(e, type);
		} catch (InvocationTargetException e) {
			throw log.exceptionCtor(e.getCause(), type);
		} catch (InstantiationException e) {
			throw log.instantiationErrorFactory(e, type);
		}
	}
}
