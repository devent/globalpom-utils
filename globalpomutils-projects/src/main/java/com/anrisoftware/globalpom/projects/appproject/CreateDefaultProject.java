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

import static com.anrisoftware.globalpom.projects.appresources.AppProperty.SELECTED_PROJECT_PROPERTY;

import java.util.Locale;

import javax.inject.Inject;

import com.anrisoftware.globalpom.projects.appresources.AbstractAppResources;
import com.anrisoftware.globalpom.projects.appresources.AppProjects;
import com.anrisoftware.resources.texts.api.Texts;

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
