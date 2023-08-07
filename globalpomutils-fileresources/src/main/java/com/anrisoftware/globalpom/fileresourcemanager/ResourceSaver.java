/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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


import static org.apache.commons.transaction.file.ResourceManager.SHUTDOWN_MODE_NORMAL;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import jakarta.inject.Inject;

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
     * @param resources the {@link Resource} resources.
     *
     * @throws FileResourceException if there was an error create the file manager;
     *                               error saving the resources.
     */
    public void saveResource(Resource... resources) throws FileResourceException {
        startManager();
        String id = startTransaction();
        try {
            saveResources(id, resources);
            manager.commitTransaction(id);
        } catch (Throwable e) {
            rollbackTransaction(manager, id);
            try {
                termManager();
            } catch (IOException e1) {
                throw new SaveResourceException(e, resources, storeDir);
            }
            throw new SaveResourceException(e, resources, storeDir);
        }
        try {
            termManager();
        } catch (IOException e) {
            throw new FileResourceException("", e);
        }
    }

    private void termManager() throws FileResourceException, IOException {
        stopManager(manager);
        Files.delete(new File(manager.getWorkDir()).toPath());
    }

    private void startManager() throws FileResourceException {
        try {
            manager.start();
        } catch (ResourceManagerSystemException e) {
            throw new StartManagerException(e, storeDir);
        }
    }

    private String startTransaction() throws FileResourceException {
        try {
            String id = manager.generatedUniqueTxId();
            manager.startTransaction(id);
            return id;
        } catch (ResourceManagerSystemException e) {
            throw new StartTransactionException(e, storeDir);
        } catch (ResourceManagerException e) {
            throw new StartTransactionException(e, storeDir);
        }
    }

    private void saveResources(String id, Resource[] resources) throws ResourceManagerException, FileResourceException {
        for (Resource resource : resources) {
            saveResource(manager, id, resource);
        }
    }

    private void saveResource(FileResourceManager manager, String id, Resource resource)
            throws ResourceManagerException, FileResourceException {
        String projectId = resource.getName();
        OutputStream projectStream = manager.writeResource(id, projectId);
        saveResource(projectStream, resource);
    }

    private void saveResource(OutputStream stream, Resource resource) throws FileResourceException {
        try {
            resource.save(stream);
        } catch (Exception e) {
            throw new SaveResourceException(e, resource, storeDir);
        }
    }

    private void rollbackTransaction(FileResourceManager manager, String id) throws FileResourceException {
        try {
            manager.rollbackTransaction(id);
        } catch (ResourceManagerException e) {
            throw new RollbackTransactionException(e, id);
        }
    }

    private void stopManager(FileResourceManager manager) throws FileResourceException {
        try {
            manager.stop(SHUTDOWN_MODE_NORMAL);
        } catch (ResourceManagerSystemException e) {
            throw new StopManagerException(e, manager.getStoreDir());
        }
    }

}
