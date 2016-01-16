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
package com.anrisoftware.globalpom.projects.appproject;

import static com.anrisoftware.globalpom.projects.appresources.AppProperty.SELECTED_PROJECT_PROPERTY;

import java.net.URI;

import javax.inject.Inject;

import com.anrisoftware.globalpom.projects.appresources.AbstractAppResources;
import com.anrisoftware.globalpom.projects.appresources.AppProjects;

/**
 * Loads the last project.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class LoadLastProject {

    @Inject
    private ProjectLoaderFactory projectLoaderFactory;

    private AbstractAppResources appResources;

    /**
     * Set the application resources. It is used to set the selected project.
     *
     * @param appResources
     *            the {@link AbstractAppResources}.
     */
    public void setAppResources(AbstractAppResources appResources) {
        this.appResources = appResources;
    }

    public Project loadLastProject(URI location) throws ProjectLoadException,
            LoadLastProjectException {
        ProjectLoader loader = projectLoaderFactory.create();
        Project project = loader.loadCompressedProject(location);
        AppProjects projects = appResources.getProjects();
        project.setModified(false);
        projects.addElement(project);
        appResources.setProperty(SELECTED_PROJECT_PROPERTY, project);
        return project;
    }

}
