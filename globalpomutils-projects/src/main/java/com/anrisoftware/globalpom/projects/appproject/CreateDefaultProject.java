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

import static com.anrisoftware.globalpom.projects.appresources.AppProperty.SELECTED_PROJECT_PROPERTY;

import java.util.Locale;

import jakarta.inject.Inject;

import com.anrisoftware.globalpom.projects.appresources.AbstractAppResources;
import com.anrisoftware.globalpom.projects.appresources.AppProjects;
import com.anrisoftware.resources.texts.external.Texts;

/**
 * Creates a default project.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class CreateDefaultProject {

    private AbstractAppResources appResources;

    @Inject
    private DefaultProjectFactory projectFactory;

    private String defaultProjectName;

    private Texts texts;

    private Locale locale;

    /**
     * Sets the application resources. It is used to set the selected project.
     *
     * @param appResources
     *            the {@link AbstractAppResources}.
     */
    public void setAppResources(AbstractAppResources appResources) {
        this.appResources = appResources;
    }

    public void setTexts(Texts texts) {
        this.texts = texts;
        updateTexts();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        updateTexts();
    }

    /**
     * Creates a default project.
     *
     * @return the {@link Project}.
     *
     * @throws ProjectCreateException
     *             if there was an error creating the default project.
     */
    public Project createProject() throws ProjectCreateException {
        AppProjects projects = appResources.getProjects();
        Project project = projectFactory.create();
        project.setName(defaultProjectName);
        project.setModified(false);
        projects.addElement(project);
        appResources.setProperty(SELECTED_PROJECT_PROPERTY, project);
        return project;
    }

    private void updateTexts() {
        if (texts == null || locale == null) {
            return;
        }
        this.defaultProjectName = texts.getResource("project_default_name",
                locale).getText();
    }

}
