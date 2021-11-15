/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.beans.PropertyChangeListener
import java.util.concurrent.TimeUnit

import com.anrisoftware.globalpom.threads.external.core.Threads

import groovy.util.logging.Slf4j

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
