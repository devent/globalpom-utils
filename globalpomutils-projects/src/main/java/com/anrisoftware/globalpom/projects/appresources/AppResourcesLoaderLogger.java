/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.projects.appresources;

import static com.anrisoftware.globalpom.projects.appresources.AppResourcesLoaderLogger._.error_load;
import static com.anrisoftware.globalpom.projects.appresources.AppResourcesLoaderLogger._.file_not_found;

import java.io.File;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.thoughtworks.xstream.XStreamException;

/**
 * Logging messages for {@link AppResourcesLoader}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class AppResourcesLoaderLogger extends AbstractLogger {

	enum _ {

		file_not_found("Application configuration file '{}' not found."),

		error_load("Error load application configuration file '{}'.");

		private String name;

		private _(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	/**
	 * Creates a logger for {@link AppResourcesLoader}.
	 */
	public AppResourcesLoaderLogger() {
		super(AppResourcesLoader.class);
	}

	void fileNotFound(File file) {
		info(file_not_found, file);
	}

	void errorLoad(File file, XStreamException e) {
		logException(e, error_load, file);
	}

}
