/*
 * Copyright 2014 Erwin Müller <erwin.mueller@deventm.org>
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

import static java.util.concurrent.Executors.newCachedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Cached thread pool properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
class CachedThreadingProperties extends ThreadingProperties {

	/**
	 * @see CachedThreadingPropertiesFactory#create(ContextProperties, String)
	 */
	@Inject
	CachedThreadingProperties(@Assisted ContextProperties p,
			@Assisted String name) {
		super(p, name);
	}

	/**
	 * Create the cached executor service.
	 * 
	 * @param factory
	 *            optional the {@link ThreadFactory}.
	 * 
	 * @return the cached {@link ExecutorService}.
	 */
	public ExecutorService createExecutorService(ThreadFactory factory) {
		return factory == null ? newCachedThreadPool()
				: newCachedThreadPool(factory);
	}
}
