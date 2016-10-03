/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
