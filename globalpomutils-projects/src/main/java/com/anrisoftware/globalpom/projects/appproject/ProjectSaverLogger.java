/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
