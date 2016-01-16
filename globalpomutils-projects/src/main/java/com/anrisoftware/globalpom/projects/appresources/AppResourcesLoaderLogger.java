/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
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
