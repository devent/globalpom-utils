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
package com.anrisoftware.globalpom.projects.appprojectres;

/**
 * Factory to create the default project resource.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface DefaultProjectResourceFactory {

    /**
     * Create a new default project resource.
     *
     * @return the {@link DefaultProjectResource}.
     */
    DefaultProjectResource create();

    /**
     * Create the default project resource with the attributes from the
     * specified resource.
     *
     * @param resource
     *            the {@link ProjectResource}.
     *
     * @return the {@link DefaultProjectResource}.
     */
    DefaultProjectResource create(ProjectResource resource);
}
