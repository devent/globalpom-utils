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
package com.anrisoftware.globalpom.projects.appresources;

import java.io.File;
import java.util.concurrent.Callable;

import jakarta.inject.Inject;

import com.anrisoftware.globalpom.fileresourcemanager.ResourceSaver;
import com.anrisoftware.globalpom.fileresourcemanager.ResourceSaverFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Saves the application resources under the user home directory.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class AppResourcesSaver implements Callable<AbstractAppResources> {

    private static final String USER_HOME = System.getProperty("user.home");

    private final AbstractAppResources resources;

    @Inject
    private AppResourcesSaverLogger log;

    private File storeDir;

    @Inject
    private ResourceSaverFactory saverFactory;

    /**
     * @see AppResourcesSaverFactory#create(AbstractAppResources)
     */
    @Inject
    AppResourcesSaver(@Assisted AbstractAppResources resources) {
        this.storeDir = new File(USER_HOME);
        this.resources = resources;
    }

    /**
     * Sets the store directory.
     *
     * @param dir
     *            the store {@link File} directory.
     */
    public void setStoreDir(File dir) {
        this.storeDir = dir;
    }

    @Override
    public AbstractAppResources call() throws Exception {
        ResourceSaver saver = saverFactory.create(storeDir);
        saver.saveResource(resources);
        log.resourcesSaved(storeDir);
        return resources;
    }
}
