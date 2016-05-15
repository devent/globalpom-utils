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
package com.anrisoftware.globalpom.threads.external.core;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

import java.util.concurrent.Executors;

/**
 * Different threading pool policies.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 * 
 * @see Executors
 */
public enum ThreadingPolicy {

	/**
	 * Cached thread pool.
	 * 
	 * @see Executors#newCachedThreadPool()
	 * @see Executors#newCachedThreadPool(java.util.concurrent.ThreadFactory)
	 */
	CACHED,

	/**
	 * Fixed thread pool.
	 * 
	 * @see Executors#newFixedThreadPool(int)
	 * @see Executors#newFixedThreadPool(int,
	 *      java.util.concurrent.ThreadFactory)
	 */
	FIXED,

	/**
	 * Single thread pool.
	 * 
	 * @see Executors#newSingleThreadExecutor()
	 * @see Executors#newSingleThreadExecutor(java.util.concurrent.ThreadFactory)
	 */
	SINGLE;

	private static final String NO_POLICY_FOUND = "No policy '%s' found.";
	private static final String POLICY_NAME_NULL = "Policy name cannot be null or empty.";

	/**
	 * Returns the policy with the given name.
	 * 
	 * @param name
	 *            the policy {@link String} name.
	 * 
	 * @return the {@link ThreadingPolicy}.
	 * 
	 * @throws NullPointerException
	 *             if the specified name is {@code null}.
	 * 
	 * @throws IllegalArgumentException
	 *             if the specified name is empty; if no threading policy could
	 *             be found.
	 */
	public static ThreadingPolicy parsePolicy(String name) {
		name = name.trim();
		notEmpty(name, POLICY_NAME_NULL);
		ThreadingPolicy policy = findPolicy(name);
		isTrue(policy != null, NO_POLICY_FOUND, name);
		return policy;
	}

	private static ThreadingPolicy findPolicy(String value) {
		for (ThreadingPolicy policy : values()) {
			if (value.equalsIgnoreCase(policy.name())) {
				return policy;
			}
		}
		return null;
	}
}
