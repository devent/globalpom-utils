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
