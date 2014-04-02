package com.anrisoftware.globalpom.exec

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import java.beans.PropertyChangeListener
import java.util.concurrent.Future

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.exec.api.CommandExec
import com.anrisoftware.globalpom.exec.api.CommandExecException
import com.anrisoftware.globalpom.exec.api.CommandExecFactory
import com.anrisoftware.globalpom.exec.api.ProcessTask
import com.anrisoftware.globalpom.exec.command.CommandLine
import com.anrisoftware.globalpom.exec.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.command.CommandLineModule
import com.anrisoftware.globalpom.exec.core.DefaultProcessModule
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandInput
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandInputFactory
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandOutputFactory
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeOutputsModule
import com.anrisoftware.globalpom.threads.properties.PropertiesThreads
import com.anrisoftware.globalpom.threads.properties.PropertiesThreadsModule
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see CommandLine
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Slf4j
class DefaultCommandExecTest {

    @Test
    void "unknown command"() {
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create("unkxxx")
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        shouldFailWithCause(CommandExecException) {
            ProcessTask process = task.get()
        }
    }

    @Test
    void "read output after task is done"() {
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create("echo").add("-n").add("Text")
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
    void "return exit code"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        assert process.getExitValue() == 1
    }

    @Test
    void "check valid exit code"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add(1)
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
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add(2)
        CommandExec exec = commandExecFactory.create()
        exec.setExitCode(1)
        exec.setThreads threads
        Future task = exec.exec line
        shouldFailWithCause(CommandExecException) { task.get() }
    }

    @Test
    void "check exit codes"() {
        def file = createCommand "command.sh", exitCodeCommand, tmp
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add(2)
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
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()
        exec.setExitCode(0, 2)
        exec.setThreads threads
        Future task = exec.exec line
        shouldFailWithCause(CommandExecException) { task.get() }
    }

    @Test
    void "read output in parallel"() {
        def file = createCommand "output.sh", outputCommand, tmp
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add("Text")
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
    void "read error in parallel"() {
        def file = createCommand "output.sh", outputErrorCommand, tmp
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add("Text")
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
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create(file).add(1)
        CommandExec exec = commandExecFactory.create()

        def sink = new PipedInputStream()
        def outputBuffer = new PipedOutputStream(sink)
        def listener = {
            log.info "Task property changed: {}", it
            outputBuffer.close()
        } as PropertyChangeListener

        exec.setCommandInput PipeCommandInput.fromString(injector, "Text\nText\nText\n")
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

    static Injector injector

    static CommandLineFactory commandLineFactory

    static CommandExecFactory commandExecFactory

    static PipeCommandOutputFactory pipeCommandOutputFactory

    static PipeCommandInputFactory pipeCommandInputFactory

    static threadsProperties = DefaultCommandExecTest.class.getResource("/threads_test.properties")

    static outputCommand = DefaultCommandExecTest.class.getResource("output_command.txt")

    static outputErrorCommand = DefaultCommandExecTest.class.getResource("output_error_command.txt")

    static exitCodeCommand = DefaultCommandExecTest.class.getResource("exitcode_command.txt")

    static readInputCommand = DefaultCommandExecTest.class.getResource("read_command.txt")

    static properties

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @BeforeClass
    static void createFactory() {
        toStringStyle
        injector = Guice.createInjector(
                new CommandLineModule(), new DefaultProcessModule(),
                new PipeOutputsModule(),
                new PropertiesThreadsModule())
        commandLineFactory = injector.getInstance CommandLineFactory
        commandExecFactory = injector.getInstance CommandExecFactory
        pipeCommandOutputFactory = injector.getInstance PipeCommandOutputFactory
        pipeCommandInputFactory = injector.getInstance PipeCommandInputFactory
        properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(threadsProperties)
    }

    static File createCommand(String name, URL resource, def tmp) {
        File file = tmp.newFile name
        FileUtils.write file, IOUtils.toString(resource)
        file.setExecutable true
        return file
    }
}
