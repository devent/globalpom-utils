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
package com.anrisoftware.globalpom.projects.appresources;

import javax.swing.DefaultListModel;

import com.anrisoftware.globalpom.projects.appproject.Project;
import com.anrisoftware.globalpom.projects.appprojectres.ProjectResource;

/**
 * Stored the currently know projects.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class AppProjects extends DefaultListModel<Project> {

    /**
     * Find the project for the specified resource.
     *
     * @param resource
     *            the {@link ProjectResource} resource.
     *
     * @return the {@link Project} or {@code null}.
     */
    public Project findProject(ProjectResource resource) {
        for (int i = 0; i < getSize(); i++) {
            Project project = getElementAt(i);
            if (project.indexOf(resource) != -1) {
                return project;
            }
        }
        return null;
    }

}
