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

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.zip.GZIPInputStream;

import javax.inject.Inject;

/**
 * Loads the prospection project from the specified resource.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ProjectLoader {

    @Inject
    private ProjectLoaderLogger log;

    /**
     * Loads the prospection project from the specified compressed resource.
     *
     * @param resource
     *            the resource {@link URI} path.
     *
     * @returns the loaded {@link Project}.
     *
     * @throws ProjectLoadException
     *             if there was an error loading the project and its resources.
     */
    public Project loadCompressedProject(URI resource)
            throws ProjectLoadException {
        try {
            InputStream stream = resource.toURL().openStream();
            GZIPInputStream zip = new GZIPInputStream(stream);
            ObjectInputStream ostream = new ObjectInputStream(zip);
            Project project = (Project) ostream.readObject();
            return project;
        } catch (Exception e) {
            throw log.errorLoad(e, resource);
        }
    }
}
