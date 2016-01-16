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

import static com.anrisoftware.globalpom.projects.appresources.AppProjectsLogger._.project_added;
import static com.anrisoftware.globalpom.projects.appresources.AppProjectsLogger._.project_removed;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.projects.appproject.Project;

/**
 * Logging messages for {@link AppProjects}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
final class AppProjectsLogger extends AbstractLogger {

    enum _ {

        project_added("Added project {} to {}"),

        project_removed("Removed project {} to {}");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Creates a logger for {@link AppProjects}.
     */
    public AppProjectsLogger() {
        super(AppProjects.class);
    }

    void projectAdded(AppProjects projects, Project project) {
        trace(project_added, project, projects);
    }

    void projectRemoved(AppProjects projects, Project project) {
        trace(project_removed, project, projects);
    }

}
