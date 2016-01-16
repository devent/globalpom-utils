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
