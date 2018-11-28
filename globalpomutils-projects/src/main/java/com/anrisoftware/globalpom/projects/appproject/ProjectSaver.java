/**
 * Copyright © 2016 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.inject.Inject;

import com.anrisoftware.globalpom.fileresourcemanager.FileResourceException;
import com.anrisoftware.globalpom.fileresourcemanager.Resource;
import com.anrisoftware.globalpom.fileresourcemanager.ResourceSaver;
import com.anrisoftware.globalpom.fileresourcemanager.ResourceSaverFactory;
import com.anrisoftware.globalpom.projects.appprojectres.ModifiableResourceBean;

/**
 * Saves the specified calculator project.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ProjectSaver {

    @Inject
    private ProjectSaverLogger log;

    @Inject
    private ResourceSaverFactory saverFactory;

    /**
     * Saves the specified project compressed.
     *
     * @param project
     *            the {@link Project}.
     *
     * @param file
     *            the {@link File}.
     *
     * @throws ProjectSaveException
     *             if there was an error saving the project and its resources.
     */
    public void saveCompressedProject(Project project, File file)
            throws ProjectSaveException {
        ResourceSaver saver = saverFactory.create(file.getParentFile());
        try {
            doSaveCompressedProject(saver, project, file);
            ((ModifiableResourceBean) project).setModified(false);
        } catch (FileResourceException e) {
            throw log.errorSaveProject(e, project, file);
        }
    }

    private void doSaveCompressedProject(ResourceSaver saver,
            final Project project, File file) throws FileResourceException {
        final String name = file.getName();
        saver.saveResource(new Resource() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void save(OutputStream stream) throws Exception {
                GZIPOutputStream zip = new GZIPOutputStream(stream);
                ObjectOutputStream object = new ObjectOutputStream(zip);
                object.writeObject(project);
                object.flush();
                zip.close();
            }
        });
    }
}
