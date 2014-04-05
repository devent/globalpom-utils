/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;

import org.apache.commons.transaction.file.FileResourceManager;
import org.apache.commons.transaction.util.LoggerFacade;
import org.apache.commons.transaction.util.PrintWriterLogger;

import com.google.inject.Provider;

/**
 * Provides the file resource manager for ACID file operations. The store
 * directory must be set before creating the manager.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
public class FileResourceManagerProvider implements
		Provider<FileResourceManager> {

	@Inject
	private FileResourceManagerProviderLogger log;

	private String storeDir;

	private boolean debug;

	/**
	 * Sets debug enabled for the file resource manager.
	 * 
	 * @param debug
	 *            set to {@code true} to enable debug before creating the
	 *            manager.
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Sets the store directory path for the file resource manager.
	 * 
	 * @param path
	 *            the store directory {@link File} path.
	 */
	public void setStoreDir(File path) {
		setStoreDir(path.getAbsolutePath());
	}

	/**
	 * Sets the store directory path for the file resource manager.
	 * 
	 * @param path
	 *            the store directory path.
	 */
	public void setStoreDir(String path) {
		this.storeDir = path;
	}

	@Override
	public FileResourceManager get() {
		String workDir = createTmpDir();
		boolean urlEncodePath = false;
		final ByteArrayOutputStream stream = new ByteArrayOutputStream(1024);
		PrintWriter printWriter = new PrintWriter(stream) {
			@Override
			public void flush() {
				super.flush();
				log.logFileResourceMessage(stream.toString());
			}
		};
		LoggerFacade logger = new PrintWriterLogger(printWriter, "", debug);
		return new FileResourceManager(storeDir, workDir, urlEncodePath, logger);
	}

	private String createTmpDir() {
		try {
			File tmp = File.createTempFile("fileresourcemanager", null);
			tmp.delete();
			tmp.mkdir();
			String workDir = tmp.getAbsolutePath();
			return workDir;
		} catch (IOException e) {
			throw log.errorCreateWorkDir(e);
		}
	}

}
