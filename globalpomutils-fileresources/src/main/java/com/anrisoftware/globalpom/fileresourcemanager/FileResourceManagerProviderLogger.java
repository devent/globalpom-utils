/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-fileresources.
 *
 * globalpomutils-fileresources is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-fileresources is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-fileresources. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.fileresourcemanager;

import java.io.IOException;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link FileResourceManagerProvider}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
@Singleton
class FileResourceManagerProviderLogger extends AbstractLogger {

	private static final String FILE_RESOURCE_MESSAGE = "File resource message: {}.";
	private static final String ERROR_CREATE_DIR = "Error create work directory";
	private static final String ERROR_CREATE_DIR1 = "Error create work directory.";

	/**
	 * Creates a logger for {@link FileResourceManagerProvider}.
	 */
	public FileResourceManagerProviderLogger() {
		super(FileResourceManagerProvider.class);
	}

	void logFileResourceMessage(String string) {
		log.debug(FILE_RESOURCE_MESSAGE, string);
	}

	RuntimeException errorCreateWorkDir(IOException e) {
		return logException(new RuntimeException(ERROR_CREATE_DIR),
				ERROR_CREATE_DIR1);
	}

}
