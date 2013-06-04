package com.anrisoftware.globalpom.threads.properties;

import java.util.Properties;

import com.anrisoftware.globalpom.threads.api.ThreadsException;

/**
 * Factory to create threads pool based on a properties file.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface PropertiesThreadsFactory {

	/**
	 * Creates the threads.
	 * 
	 * @return the {@link PropertiesThreads}.
	 */
	PropertiesThreads create();

	/**
	 * Creates the threads.
	 * 
	 * @param properties
	 *            the threads pool {@link Properties}.
	 * 
	 * @param name
	 *            the threads pool {@link String} name.
	 * 
	 * @return the {@link PropertiesThreads}.
	 * 
	 * @throws ThreadsException
	 *             if there was any error load the property of the thread pool.
	 */
	PropertiesThreads create(Properties properties, String name)
			throws ThreadsException;
}
