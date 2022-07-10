/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.core

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputFactory
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule
import com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule
import com.anrisoftware.globalpom.exec.internal.threads.TestThreadsPropertiesProvider
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

/**
 * @see DefaultCommandExec
 * @author Erwin Müller, {@code <erwin@muellerpublic.de>}
 */
@Slf4j
class DefaultCommandExecTest {

    @Test
    void "exec should start a process"() {
        def commandExec = commandExecFactory.create()
        commandExec.threads = threads
        def commandLine = commandLineFactory.create("echo")
        commandLine.add("hello")
        def task = commandExec.exec(commandLine)
        task.get(10, TimeUnit.SECONDS)
    }

    @Test
    void "exec should start a process output the stdout"() {
        def commandExec = commandExecFactory.create()
        commandExec.threads = threads
        def commandLine = commandLineFactory.create("echo")
        commandLine.add("hello")
        commandExec.commandOutput = debugLogCommandOutputFactory.create(log, commandLine)
        def task = commandExec.exec(commandLine)
        task.get(10, TimeUnit.SECONDS)
    }

    @Test
    void "start process and cancel the task"() {
        def commandExec = commandExecFactory.create()
        commandExec.threads = threads
        def commandLine = commandLineFactory.create("sleep")
        commandLine.add("30")
        commandExec.destroyOnInterrupted = true
        def task = commandExec.exec(commandLine)
        def taskThread = Thread.start {
            Thread.sleep(100)
            task.cancel(true)
        }
        def processTask = null
        def cancelled = false
        try {
            processTask = task.get()
        } catch (CancellationException e) {
            cancelled = true
        }
        assertThat cancelled, is(true)
    }

    static Injector injector

    static CommandExecFactory commandExecFactory

    static CommandLineFactory commandLineFactory

    static PropertiesThreadsFactory propertiesThreadsFactory

    static PropertiesThreads threads

    static DebugLogCommandOutputFactory debugLogCommandOutputFactory

    @BeforeAll
    static void createFactory() {
        toStringStyle
        injector = Guice.createInjector(
                new DefaultProcessModule(),
                new DefaultCommandLineModule(),
                new PipeOutputsModule(),
                new PropertiesThreadsModule(),
                new LogOutputsModule()
                )
        commandExecFactory = injector.getInstance CommandExecFactory
        commandLineFactory = injector.getInstance CommandLineFactory
        propertiesThreadsFactory = injector.getInstance(PropertiesThreadsFactory)
        debugLogCommandOutputFactory = injector.getInstance(DebugLogCommandOutputFactory)
        threads = propertiesThreadsFactory.create()
        threads.setProperties injector.getInstance(TestThreadsPropertiesProvider).get()
        threads.setName "script"
    }
}
