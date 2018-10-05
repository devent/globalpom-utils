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
package com.anrisoftware.globalpom.exec.internal.core

import static com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLine.*
import static com.anrisoftware.globalpom.exec.internal.core.DefaultCommandExec.*
import static com.anrisoftware.globalpom.exec.internal.logoutputs.AbstractLogCommandOutput.*
import static com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsImpl.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import java.beans.PropertyChangeListener
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

import javax.inject.Inject

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.external.core.CommandExec
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory
import com.anrisoftware.globalpom.exec.external.core.InvalidExitCodeException
import com.anrisoftware.globalpom.exec.external.core.ProcessTask
import com.anrisoftware.globalpom.exec.external.core.StartCommandException
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputFactory
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLine
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsImpl
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see DefaultCommandLine
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
@Slf4j
class DefaultCommandExecTest {

    @Test
    void "unknown command"() {
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create("unkxxx")
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        shouldFailWithCause(StartCommandException) {
            ProcessTask process = task.get()
        }
    }

    @Test
    void "read output after task is done"() {
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create("echo").add("-n").add("Text")
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        log.info "Output: ``{}''", process.getOut()
        log.info "Error: ``{}''", process.getErr()
        assert process.getExitValue() == 0
        assert process.getOut() == "Text"
        assert process.getErr() == ""
    }

    @Test
    void "read output after task is done, use factory methods"() {
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = createCommandLine("echo").add("-n").add("Text")
        CommandExec exec = createCommandExec()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        log.info "Output: ``{}''", process.getOut()
        log.info "Error: ``{}''", process.getErr()
        assert process.getExitValue() == 0
        assert process.getOut() == "Text"
        assert process.getErr() == ""
    }

    @Test
    void "return exit code"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        assert process.getExitValue() == 1
    }

    @Test
    void "check valid exit code"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()
        exec.setExitCode(1)
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        assert process.getExitValue() == 1
    }

    @Test
    void "check invalid exit code"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(2)
        CommandExec exec = commandExecFactory.create()
        exec.setExitCode(1)
        exec.setThreads threads
        Future task = exec.exec line
        shouldFailWithCause(InvalidExitCodeException) { task.get() }
    }

    @Test
    void "check exit codes"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(2)
        CommandExec exec = commandExecFactory.create()
        exec.setExitCode(1, 2)
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        assert process.getExitValue() == 2
    }

    @Test
    void "check invalid exit codes"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()
        exec.setExitCode(0, 2)
        exec.setThreads threads
        Future task = exec.exec line
        shouldFailWithCause(InvalidExitCodeException) { task.get() }
    }

    @Test
    void "read output in parallel"() {
        def file = createCommand "output.sh", outputCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add("Text")
        CommandExec exec = commandExecFactory.create()

        def sink = new PipedInputStream()
        def outputBuffer = new PipedOutputStream(sink)
        def listener = {
            log.info "Task property changed: {}", it
            outputBuffer.close()
        } as PropertyChangeListener

        exec.setCommandOutput pipeCommandOutputFactory.create(outputBuffer)
        exec.setThreads threads
        Future task = exec.exec line, listener

        byte[] buff = new byte[4*3]
        int i = 0, b
        while ((b = sink.read()) != -1) {
            log.info "Read {}", b
            buff[i++] = b
        }
        assert new String(buff) == "TextTextText"
    }

    @Test
    void "read output in parallel, as info log"() {
        def file = createCommand "output.sh", outputLinesCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add("Text")
        CommandExec exec = commandExecFactory.create()
        exec.setCommandOutput createInfoLogCommandOutput(log, line)
        exec.setThreads threads
        Future task = exec.exec line
        task.get()
    }

    @Test
    void "read output in parallel, as debug log"() {
        def file = createCommand "output.sh", outputLinesCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add("Text")
        CommandExec exec = commandExecFactory.create()
        exec.setCommandOutput createDebugLogCommandOutput(log, line)
        exec.setThreads threads
        Future task = exec.exec line
        task.get()
    }

    @Test
    void "read output in parallel, as trace log"() {
        def file = createCommand "output.sh", outputLinesCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add("Text")
        CommandExec exec = commandExecFactory.create()
        exec.setCommandOutput createTraceLogCommandOutput(log, line)
        exec.setThreads threads
        Future task = exec.exec line
        task.get()
    }

    @Test
    void "read output in parallel, as error log"() {
        def file = createCommand "output.sh", outputLinesCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add("Text")
        CommandExec exec = commandExecFactory.create()
        exec.setCommandOutput createErrorLogCommandOutput(log, line)
        exec.setThreads threads
        Future task = exec.exec line
        task.get()
    }

    @Test
    void "read error in parallel"() {
        def file = createCommand "output.sh", outputErrorCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add("Text")
        CommandExec exec = commandExecFactory.create()

        def sink = new PipedInputStream()
        def outputBuffer = new PipedOutputStream(sink)
        def listener = {
            log.info "Task property changed: {}", it
            outputBuffer.close()
        } as PropertyChangeListener

        exec.setCommandError pipeCommandOutputFactory.create(outputBuffer)
        exec.setThreads threads
        Future task = exec.exec line, listener

        byte[] buff = new byte[4*3]
        int i = 0, b
        while ((b = sink.read()) != -1) {
            log.info "Read {}", b
            buff[i++] = b
        }
        task.get()
        assert new String(buff) == "TextTextText"
    }

    @Test
    void "read input"() {
        def file = createCommand "command.sh", readInputCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()

        def sink = new PipedInputStream()
        def outputBuffer = new PipedOutputStream(sink)
        def listener = {
            log.info "Task property changed: {}", it
            outputBuffer.close()
        } as PropertyChangeListener

        exec.setCommandInput pipeCommandInputFactory.fromString("Text\nText\nText\n")
        exec.setCommandOutput pipeCommandOutputFactory.create(outputBuffer)
        exec.setThreads threads
        Future task = exec.exec line, listener

        byte[] buff = new byte[4*3]
        int i = 0, b
        while ((b = sink.read()) != -1) {
            log.info "Read {}", b
            buff[i++] = b
        }
        task.get()

        log.info "Read '{}'.", new String(buff)
        assert new String(buff) == "TextTextText"
    }

    @Test
    void "wait command"() {
        def file = createCommand "command.sh", sleepCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process
        process = task.get()
    }

    @Test
    void "wait timeout"() {
        def file = createCommand "command.sh", sleepCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(10)
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process
        shouldFailWith(TimeoutException) {
            process = task.get(2, TimeUnit.SECONDS)
        }
    }

    @Test
    void "wait task"() {
        def file = createCommand "command.sh", sleepCommand, tmp
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineFactory.create(file).add(2)
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process
        process = task.get(3, TimeUnit.SECONDS)
    }

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Inject
    PropertiesThreadsFactory propertiesThreadsFactory

    @Inject
    CommandLineFactory commandLineFactory

    @Inject
    CommandExecFactory commandExecFactory

    @Inject
    PipeCommandOutputFactory pipeCommandOutputFactory

    @Inject
    PipeCommandInputFactory pipeCommandInputFactory

    @BeforeEach
    void setup() {
        injector.injectMembers(this)
    }

    static Injector injector

    static threadsProperties = DefaultCommandExecTest.class.getResource("/threads_test.properties")

    static outputCommand = DefaultCommandExecTest.class.getResource("output_command.txt")

    static outputLinesCommand = DefaultCommandExecTest.class.getResource("output_lines_command.txt")

    static outputErrorCommand = DefaultCommandExecTest.class.getResource("output_error_command.txt")

    static exitCodeCommand = DefaultCommandExecTest.class.getResource("exitcode_command.txt")

    static readInputCommand = DefaultCommandExecTest.class.getResource("read_command.txt")

    static sleepCommand = DefaultCommandExecTest.class.getResource("sleep_command.txt")

    static properties

    @BeforeEachClass
    static void createFactory() {
        toStringStyle
        this.injector = Guice.createInjector(
                new DefaultCommandLineModule(),
                new DefaultProcessModule(),
                new PipeOutputsModule(),
                new PropertiesThreadsModule())
        this.properties = new ContextPropertiesFactory(PropertiesThreadsImpl).fromResource(threadsProperties)
    }

    static File createCommand(String name, URL resource, def tmp) {
        File file = tmp.newFile name
        FileUtils.write file, IOUtils.toString(resource)
        file.setExecutable true
        return file
    }
}
