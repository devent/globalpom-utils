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

import static org.apache.commons.lang3.Validate.notNull;

import java.util.concurrent.ThreadFactory;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.threads.api.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.api.ThreadsException;

/**
 * Logging messages for {@link ThreadingProperties}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
class ThreadingPropertiesLogger extends AbstractLogger {

	private static final String INSTANTIATION_ERROR_MESSAGE = "Instantiation error of thread factory {}.";
	private static final String INSTANTIATION_ERROR = "Instantiation error of thread factory";
	private static final String ERROR_DEFAULT_CTOR_MESSAGE = "Error in default constructor of thread factory {}.";
	private static final String ERROR_DEFAULT_CTOR = "Error in default constructor of thread factory";
	private static final String ILLEGAL_ACCESS_MESSAGE = "Illegal access to default constructor of thread factory {}.";
	private static final String ILLEGAL_ACCESS = "Illegal access to default constructor of thread factory";
	private static final String NAME = "name";
	private static final String THREAD_FACTORY_NOT_FOUND_MESSAGE = "Thread factory {} not found";
	private static final String THREAD_FACTORY_NOT_FOUND = "Thread factory not found";
	private static final String THREAD_FACTORY_NULL = "No thread factory found.";
	private static final String THREADING_POLICY_NULL = "No threading policy found.";
	private static final String TYPE = "type";
	private static final String NO_DEFAULT_CONSTRUCTOR_MESSAGE = "No default constructor for type {} available.";
	private static final String NO_DEFAULT_CONSTRUCTOR = "No default constructor available";

	/**
	 * Create logger for {@link ThreadingProperties}.
	 */
	public ThreadingPropertiesLogger() {
		super(ThreadingProperties.class);
	}

	ThreadsException threadFactoryNotFound(ClassNotFoundException e,
			String value) {
		return logException(
				new ThreadsException(THREAD_FACTORY_NOT_FOUND, e).addContext(
						NAME, value), THREAD_FACTORY_NOT_FOUND_MESSAGE, value);
	}

	ThreadsException noDefaultCtor(NoSuchMethodException e, Class<?> type) {
		return logException(
				new ThreadsException(NO_DEFAULT_CONSTRUCTOR, e).addContext(
						TYPE, type), NO_DEFAULT_CONSTRUCTOR_MESSAGE, type);
	}

	ThreadsException illegalAccessCtor(IllegalAccessException e, Class<?> type) {
		return logException(
				new ThreadsException(ILLEGAL_ACCESS, e).addContext(TYPE, type),
				ILLEGAL_ACCESS_MESSAGE, type);
	}

	ThreadsException exceptionCtor(Throwable e, Class<?> type) {
		return logException(
				new ThreadsException(ERROR_DEFAULT_CTOR, e).addContext(TYPE,
						type), ERROR_DEFAULT_CTOR_MESSAGE, type);
	}

	ThreadsException instantiationErrorFactory(InstantiationException e,
			Class<?> type) {
		return logException(
				new ThreadsException(INSTANTIATION_ERROR, e).addContext(TYPE,
						type), INSTANTIATION_ERROR_MESSAGE, type);
	}

	void checkPolicy(ThreadingProperties p, ThreadingPolicy value) {
		notNull(value, THREADING_POLICY_NULL);
	}

	void checkThreadFactory(ThreadingProperties p, ThreadFactory value) {
		notNull(value, THREAD_FACTORY_NULL);
	}

}
