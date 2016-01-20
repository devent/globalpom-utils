/*
 * Copyright 2015-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.projects.appproject.ProjectSaverLogger._.error_save;

import java.io.File;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ProjectSaver}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ProjectSaverLogger extends AbstractLogger {

    enum _ {

        error_save("Error save prospection project");

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
     * Sets the context of the logger to {@link ProjectSaver}.
     */
    public ProjectSaverLogger() {
        super(ProjectSaver.class);
    }

    ProjectSaveException errorSaveProject(Exception e, Project project,
            File file) {
        return new ProjectSaveException(error_save, e, project, file);
    }
}
