/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
