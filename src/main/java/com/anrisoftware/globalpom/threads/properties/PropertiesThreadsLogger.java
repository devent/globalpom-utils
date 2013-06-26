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

import static org.apache.commons.lang3.Validate.notBlank;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.threads.api.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.api.ThreadsException;

/**
 * Logging messages for {@link PropertiesThreads}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
class PropertiesThreadsLogger extends AbstractLogger {

	private static final String NAME_NULL = "The name must be not null or empty.";
	private static final String POLICY = "policy";
	private static final String INVALID_POLICY_MESSAGE = "Invalid thread pool policy {}.";
	private static final String INVALID_POLICY = "Invalid thread pool policy";

	/**
	 * Create logger for {@link PropertiesThreads}.
	 */
	public PropertiesThreadsLogger() {
		super(PropertiesThreads.class);
	}

	ThreadsException invalidPolicy(PropertiesThreads threads,
			ThreadingPolicy policy) {
		return logException(
				new ThreadsException(INVALID_POLICY).addContext(POLICY, policy),
				INVALID_POLICY_MESSAGE, policy);
	}

	void checkName(PropertiesThreads threads, String name) {
		notBlank(name, NAME_NULL);
	}
}
