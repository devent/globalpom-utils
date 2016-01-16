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
