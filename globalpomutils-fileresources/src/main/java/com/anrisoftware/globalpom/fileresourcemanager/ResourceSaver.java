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

import static org.apache.commons.transaction.file.ResourceManager.SHUTDOWN_MODE_NORMAL;

import java.io.File;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.transaction.file.FileResourceManager;
import org.apache.commons.transaction.file.ResourceManagerException;
import org.apache.commons.transaction.file.ResourceManagerSystemException;

import com.google.inject.assistedinject.Assisted;

/**
 * Saves resources in a transaction.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
public class ResourceSaver {

	private final File storeDir;

	@Inject
	private ResourceSaverLogger log;

	private FileResourceManager manager;

	/**
	 * @see ResourceSaverFactory#create(File)
	 */
	@Inject
	ResourceSaver(@Assisted File storeDir) {
		this.storeDir = storeDir;
	}

	@Inject
	void FileResourceManagerProvider(FileResourceManagerProvider manager) {
		manager.setStoreDir(storeDir);
		this.manager = manager.get();
	}

	/**
	 * Save the specified resources in the store directory.
	 * 
	 * @param resources
	 *            the {@link Resource} resources.
	 * 
	 * @throws FileResourceException
	 *             if there was an error create the file manager; error saving
	 *             the resources.
	 */
	public void saveResource(Resource... resources)
			throws FileResourceException {
		startManager();
		String id = startTransaction();
		try {
			saveResources(id, resources);
			manager.commitTransaction(id);
		} catch (Throwable e) {
			rollbackTransaction(manager, id);
			stopManager(manager);
			new File(manager.getWorkDir()).delete();
			throw log.errorSave(e, resources, storeDir);
		}
		stopManager(manager);
		new File(manager.getWorkDir()).delete();
	}

	private void startManager() throws FileResourceException {
		try {
			manager.start();
		} catch (ResourceManagerSystemException e) {
			throw log.errorStartManager(e, storeDir);
		}
	}

	private String startTransaction() throws FileResourceException {
		try {
			String id = manager.generatedUniqueTxId();
			manager.startTransaction(id);
			return id;
		} catch (ResourceManagerSystemException e) {
			throw log.errorStartTransaction(e, storeDir);
		} catch (ResourceManagerException e) {
			throw log.errorStartTransaction(e, storeDir);
		}
	}

	private void saveResources(String id, Resource[] resources)
			throws ResourceManagerException, FileResourceException {
		for (Resource resource : resources) {
			saveResource(manager, id, resource);
		}
	}

	private void saveResource(FileResourceManager manager, String id,
			Resource resource) throws ResourceManagerException,
			FileResourceException {
		String projectId = resource.getName();
		OutputStream projectStream = manager.writeResource(id, projectId);
		saveResource(projectStream, resource);
	}

	private void saveResource(OutputStream stream, Resource resource)
			throws FileResourceException {
		try {
			resource.save(stream);
		} catch (Exception e) {
			throw log.errorSaveResource(e, resource, storeDir);
		}
	}

	private void rollbackTransaction(FileResourceManager manager, String id)
			throws FileResourceException {
		try {
			manager.rollbackTransaction(id);
		} catch (ResourceManagerException e) {
			throw log.errorRollbackTransaction(e, id);
		}
	}

	private void stopManager(FileResourceManager manager)
			throws FileResourceException {
		try {
			manager.stop(SHUTDOWN_MODE_NORMAL);
		} catch (ResourceManagerSystemException e) {
			throw log.errorStopManager(e, manager.getStoreDir());
		}
	}

}
