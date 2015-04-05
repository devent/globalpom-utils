/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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

import java.beans.PropertyChangeListener
import java.util.concurrent.TimeUnit

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

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
class PropertiesThreadsTest {

    @Test
    void "cached pool"() {
        threads.setProperties properties
        threads.setName "cached"
        def future = threads.submit task
        threads.awaitTermination(1, TimeUnit.SECONDS)
    }

    @Test
    void "fixed pool"() {
        threads.setProperties properties
        threads.setName "fixed"
        def future = threads.submit task
        threads.awaitTermination(1, TimeUnit.SECONDS)
    }

    @Test
    void "single pool"() {
        threads.setProperties properties
        threads.setName "single"
        def future = threads.submit task
        threads.awaitTermination(1, TimeUnit.SECONDS)
    }

    @Test
    void "single pool, task exception"() {
        def listenerRun = false
        def task = {
            def ex = new IllegalStateException("Test exception")
            log.info "Throing exception: {}", ex.getMessage()
            throw ex
        }
        def listener = [propertyChange: { evt ->
                log.info "Task changed: {}", evt
                listenerRun = true
            }] as PropertyChangeListener

        threads.setProperties properties
        threads.setName "single"
        def future = threads.submit task, listener
        threads.awaitTermination(1, TimeUnit.SECONDS)

        assert listenerRun == false
    }

    PropertiesThreads threads

    def task

    boolean taskCalled

    @Before
    void createThreads() {
        threads = injector.getInstance PropertiesThreads
        task = { Thread.sleep 500 }
    }

    static Injector injector

    static URL propertiesResource

    static Properties properties

    @BeforeClass
    static void setupThreads() {
        injector = Guice.createInjector(new PropertiesThreadsModule())
        propertiesResource = PropertiesThreadsTest.class.getResource("/threads_test.properties")
        properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(propertiesResource)
    }

    static {
        TestUtils.toStringStyle
    }
}
