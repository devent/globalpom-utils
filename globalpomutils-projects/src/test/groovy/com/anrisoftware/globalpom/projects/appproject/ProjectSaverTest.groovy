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
package com.anrisoftware.globalpom.projects.appproject

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import java.beans.PropertyChangeListener

import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.projects.utils.Dependencies

/**
 * @see ProjectSaver
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.16
 */
@CompileStatic
@Slf4j
class ProjectSaverTest {

    @Test
    void "save project, load project"() {
        def project = dep.projectFactory.create()
        project.name = "Some Name"

        def saver = Dependencies.injector.getInstance ProjectSaver
        def file = folder.newFile()
        file.delete()
        saver.saveCompressedProject project, file
        assert file.isFile()
        // load
        def loader = Dependencies.injector.getInstance ProjectLoader
        def projectB = loader.loadCompressedProject file.toURI()
        projectB.addPropertyChangeListener { } as PropertyChangeListener
        assert projectB.name == "Some Name"
    }

    @Test
    void "save project, load project, with resources"() {
        def res = dep.projectResourceFactory.create()
        res.name = "res0"

        def project = dep.projectFactory.create()
        project.addElement res

        def saver = Dependencies.injector.getInstance ProjectSaver
        def file = folder.newFile()
        file.delete()
        saver.saveCompressedProject project, file
        assert file.isFile()
        // load
        def loader = Dependencies.injector.getInstance ProjectLoader
        def projectB = loader.loadCompressedProject file.toURI()
        projectB.addPropertyChangeListener { } as PropertyChangeListener
        assert projectB.size == 1
    }

    static Dependencies dep

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @BeforeClass
    static void setupDependencies() {
        this.dep = Dependencies.injector.getInstance Dependencies
    }
}
