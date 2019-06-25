/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
