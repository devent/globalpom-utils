/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.threads.properties

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsImpl
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

/**
 * @see PropertiesThreads
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Slf4j
class PropertiesThreadsTest extends AbstractPropertiesThreadsTest {

    @Test
    void "cached pool"() {
        super."cached pool"()
    }

    @Test
    void "fixed pool"() {
        super."fixed pool"()
    }

    @Test
    void "single pool"() {
        super."single pool"()
    }

    @Test
    void "single pool, task exception"() {
        super."single pool, task exception"()
    }

    PropertiesThreadsImpl threads

    Threads createThreads() {
        injector.getInstance PropertiesThreadsImpl
    }

    Properties createThreadsProperties() {
        properties
    }

    def createTask() {
        return { Thread.sleep 500 }
    }

    static Injector injector

    static Properties properties

    @BeforeAll
    static void setupThreads() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new PropertiesThreadsModule())
        properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(propertiesResource)
    }
}
