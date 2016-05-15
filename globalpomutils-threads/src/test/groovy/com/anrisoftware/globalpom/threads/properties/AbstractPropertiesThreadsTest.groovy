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
package com.anrisoftware.globalpom.threads.properties

import groovy.util.logging.Slf4j

import java.beans.PropertyChangeListener
import java.util.concurrent.TimeUnit

import com.anrisoftware.globalpom.threads.external.core.Threads

/**
 *
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 3.1
 */
@Slf4j
abstract class AbstractPropertiesThreadsTest {

    void "cached pool"() {
        def threads = createThreads()
        def properties = createThreadsProperties()
        def task = createTask()
        threads.setProperties properties
        threads.setName "cached"
        def future = threads.submit task
        threads.awaitTermination(1, TimeUnit.SECONDS)
    }

    void "fixed pool"() {
        def threads = createThreads()
        def properties = createThreadsProperties()
        def task = createTask()
        threads.setProperties properties
        threads.setName "fixed"
        def future = threads.submit task
        threads.awaitTermination(1, TimeUnit.SECONDS)
    }

    void "single pool"() {
        def threads = createThreads()
        def properties = createThreadsProperties()
        def task = createTask()
        threads.setProperties properties
        threads.setName "single"
        def future = threads.submit task
        threads.awaitTermination(1, TimeUnit.SECONDS)
    }

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

        def threads = createThreads()
        def properties = createThreadsProperties()
        threads.setProperties properties
        threads.setName "single"
        def future = threads.submit task, listener
        threads.awaitTermination(1, TimeUnit.SECONDS)

        assert listenerRun == false
    }

    abstract Threads createThreads()

    abstract Properties createThreadsProperties()

    abstract createTask()

    static URL propertiesResource = AbstractPropertiesThreadsTest.class.getResource("/threads_test.properties")
}
