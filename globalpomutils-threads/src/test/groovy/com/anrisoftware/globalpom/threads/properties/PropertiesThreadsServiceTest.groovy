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
package com.anrisoftware.globalpom.threads.properties

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsImpl
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory

/**
 * @see PropertiesThreadsServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class PropertiesThreadsServiceTest extends AbstractPropertiesThreadsTest {

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

    Threads createThreads() {
        service.create()
    }

    Properties createThreadsProperties() {
        properties
    }

    def createTask() {
        return { Thread.sleep 500 }
    }

    static Properties properties

    @Rule
    public final OsgiContext context = new OsgiContext()

    @Inject
    PropertiesThreadsService service

    @BeforeEach
    void createFactories() {
        toStringStyle
        this.service = context.registerInjectActivateService(new PropertiesThreadsServiceImpl(), null)
    }

    @BeforeAll
    static void setupThreads() {
        TestUtils.toStringStyle
        properties = new ContextPropertiesFactory(PropertiesThreadsImpl).fromResource(propertiesResource)
    }
}
