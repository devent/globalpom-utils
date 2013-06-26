/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;

import com.anrisoftware.propertiesutils.ContextProperties;
import com.google.inject.assistedinject.Assisted;

/**
 * Single thread pool properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@SuppressWarnings("serial")
class SingleThreadingProperties extends ThreadingProperties {

	/**
	 * @see SingleThreadingPropertiesFactory#create(ContextProperties, String)
	 */
	@Inject
	SingleThreadingProperties(@Assisted ContextProperties p,
			@Assisted String name) {
		super(p, name);
	}

	/**
	 * Create the single executor service.
	 * 
	 * @param factory
	 *            optional the {@link ThreadFactory}.
	 * 
	 * @return the single {@link ExecutorService}.
	 */
	public ExecutorService createExecutorService(ThreadFactory factory) {
		return factory == null ? newSingleThreadExecutor()
				: newSingleThreadExecutor(factory);
	}
}
