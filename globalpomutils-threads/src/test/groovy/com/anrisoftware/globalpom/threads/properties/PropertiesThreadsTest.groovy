/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-threads.
 *
 * globalpomutils-threads is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-threads is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-threads. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties;

import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsImpl
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.google.inject.Guice
import com.google.inject.Injector

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

    @BeforeClass
    static void setupThreads() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new PropertiesThreadsModule())
        properties = new ContextPropertiesFactory(PropertiesThreadsImpl).fromResource(propertiesResource)
    }
}
