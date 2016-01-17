/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-fileresources.
 *
 * globalpomutils-fileresources is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-fileresources is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-fileresources. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.fileresourcemanager

import org.apache.commons.io.FileUtils
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ResourceSaver
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class ResourceSaverTest {

    @Test
    void "save resource"() {
        def storedir = tmp.newFolder()
        def fileA = new File(storedir, "A")
        def fileB = new File(storedir, "B")
        def saver = factory.create(storedir)
        saver.saveResource(resourceA, resourceB)
        assert fileA.isFile()
        assert FileUtils.readFileToString(fileA) == "A\n"
        assert fileB.isFile()
        assert FileUtils.readFileToString(fileB) == "B\n"
    }

    def resourceA

    def resourceB

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder()

    static Injector injector

    static ResourceSaverFactory factory

    @BeforeClass
    static void createSaver() {
        injector = getInjector()
        factory = injector.getInstance ResourceSaverFactory
    }

    @Before
    void createResources() {
        resourceA = new ResourceA()
        resourceB = new ResourceB()
    }

    static Injector getInjector() {
        Guice.createInjector(new FileResourceManagerModule())
    }
}
