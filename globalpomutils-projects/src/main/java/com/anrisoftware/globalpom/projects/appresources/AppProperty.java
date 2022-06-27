/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

/**
 * Application property that is not stored.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public enum AppProperty {

    /**
     * The current selected project property.
     * <p>
     * The current project can be set by a) the application is creating a new
     * unnamed project on application start; b) the user creates a new project;
     * c) the user opens a project; d) the user selects a project in the
     * projects window.
     */
    SELECTED_PROJECT_PROPERTY,

    /**
     * The current selected resource property.
     */
    SELECTED_RESOURCE_PROPERTY,

}
