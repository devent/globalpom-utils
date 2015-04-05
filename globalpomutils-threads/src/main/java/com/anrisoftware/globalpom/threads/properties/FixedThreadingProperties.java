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

import static java.lang.String.format;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Fixed thread pool properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
class FixedThreadingProperties extends ThreadingProperties {

	/**
	 * Property name of number of maximum threads.
	 */
	public static final String MAX_THREADS_PROPERTY = "max_threads";

	private final FixedThreadingPropertiesLogger log;

	/**
	 * @see FixedThreadingPropertiesFactory#create(ContextProperties, String)
	 */
	@Inject
	FixedThreadingProperties(FixedThreadingPropertiesLogger logger,
			@Assisted ContextProperties p, @Assisted String name) {
		super(p, name);
		this.log = logger;
	}

	/**
	 * Returns the maximum number of threads for the pool.
	 * 
	 * @return the maximum number of threads.
	 * 
	 * @throws NullPointerException
	 *             if no maximum number of threads property was found.
	 * 
	 * @see #MAX_THREADS_PROPERTY
	 */
	public int getMaxThreads() {
		Number value = getProperties().getNumberProperty(
				format(KEY_TEMPLATE, getName(), MAX_THREADS_PROPERTY));
		log.checkMaxThreads(this, value);
		return value.intValue();
	}

	/**
	 * Returns the maximum number of threads for the pool.
	 * 
	 * @param defaultValue
	 *            the default maximum number of threads.
	 * 
	 * @return the maximum number of threads or the default value.
	 * 
	 * @see #MAX_THREADS_PROPERTY
	 */
	public int getMaxThreads(int defaultValue) {
		Number value = getProperties().getNumberProperty(
				format(KEY_TEMPLATE, getName(), MAX_THREADS_PROPERTY),
				defaultValue);
		return value.intValue();
	}

	/**
	 * Create the fixed executor service.
	 * 
	 * @param factory
	 *            optional the {@link ThreadFactory}.
	 * 
	 * @param maxThreads
	 *            maximum number of threads in the pool.
	 * 
	 * @return the fixed {@link ExecutorService}.
	 */
	public ExecutorService createExecutorService(ThreadFactory factory,
			int maxThreads) {
		return factory == null ? newFixedThreadPool(maxThreads)
				: newFixedThreadPool(maxThreads, factory);
	}
}
