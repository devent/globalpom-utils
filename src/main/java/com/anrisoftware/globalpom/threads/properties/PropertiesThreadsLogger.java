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
