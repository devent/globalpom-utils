/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
