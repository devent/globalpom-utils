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
package com.anrisoftware.globalpom.threads.watchdog

import static java.time.Duration.*
import static org.joda.time.Duration.parse as dparse
import static org.junit.jupiter.api.Assertions.*

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ThreadsWatchdog
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class ThreadsWatchdogTest {

    @Test
    void "submit callable task"() {
        boolean taskRun = false
        def task = {
            Thread.sleep 1000
            taskRun = true
        }
        def future = watchdog.submit(task)

        assertTimeout(ofSeconds(2), { watchdog.waitForTasks() } as Executable)
        assert taskRun == true
    }

    @Test
    void "submit callable task +short timeout"() {
        boolean taskRun = false
        def task = { Thread.sleep 2000; taskRun = true }
        def future = watchdog.submit(task)

        def notFinished
        assertTimeout(ofSeconds(3), { notFinished = watchdog.waitForTasks dparse("PT1S") } as Executable)
        assert notFinished.size() == 1
        assert taskRun == false
    }

    @Test
    void "submit callable task +long timeout"() {
        boolean taskRun = false
        def task = { Thread.sleep 1000; taskRun = true }
        def future = watchdog.submit(task)

        def notFinished
        assertTimeout(ofSeconds(3), { notFinished = watchdog.waitForTasks dparse("PT2S") } as Executable)
        assert notFinished.size() == 0
        assert taskRun == true
    }

    ThreadsWatchdog watchdog

    @BeforeEach
    void setupWatchdog() {
        watchdog = injector.getInstance ThreadsWatchdog
        watchdog.setExecutor service
    }

    static Injector injector

    static ExecutorService service

    @BeforeAll
    static void setupService() {
        injector = Guice.createInjector()
        service = Executors.newCachedThreadPool()
    }
}
