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

import static org.apache.commons.lang3.Validate.notNull;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link FixedThreadingProperties}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
class FixedThreadingPropertiesLogger extends AbstractLogger {

	private static final String MAXIMUM_NUMBER_NULL = "No maximum number of threads property found.";

	/**
	 * Create logger for {@link FixedThreadingProperties}.
	 */
	public FixedThreadingPropertiesLogger() {
		super(FixedThreadingProperties.class);
	}

	void checkMaxThreads(FixedThreadingProperties p, Number value) {
		notNull(value, MAXIMUM_NUMBER_NULL);
	}
}
