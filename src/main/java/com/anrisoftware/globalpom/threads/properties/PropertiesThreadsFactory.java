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
