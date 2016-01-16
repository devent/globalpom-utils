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

import java.io.File;
import java.util.concurrent.Callable;

import javax.inject.Inject;

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
