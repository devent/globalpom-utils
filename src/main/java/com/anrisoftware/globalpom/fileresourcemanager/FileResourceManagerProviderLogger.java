/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of forecast-suite-main. All rights reserved.
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
