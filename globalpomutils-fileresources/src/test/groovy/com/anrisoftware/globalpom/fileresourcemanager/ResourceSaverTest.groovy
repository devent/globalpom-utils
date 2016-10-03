/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
