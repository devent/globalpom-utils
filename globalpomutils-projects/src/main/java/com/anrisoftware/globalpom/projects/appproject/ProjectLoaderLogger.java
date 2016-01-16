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

import static com.anrisoftware.globalpom.projects.appproject.ProjectLoaderLogger._.error_load;

import java.net.URI;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ProjectLoader}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
final class ProjectLoaderLogger extends AbstractLogger {

    enum _ {

        error_load("Error load project");

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
     * Sets the context of the logger to {@link ProjectLoader}.
     */
    public ProjectLoaderLogger() {
        super(ProjectLoader.class);
    }

    ProjectLoadException errorLoad(Exception e, URI resource) {
        return new ProjectLoadException(error_load, e, resource);
    }
}
