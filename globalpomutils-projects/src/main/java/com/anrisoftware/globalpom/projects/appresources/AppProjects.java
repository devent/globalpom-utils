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
