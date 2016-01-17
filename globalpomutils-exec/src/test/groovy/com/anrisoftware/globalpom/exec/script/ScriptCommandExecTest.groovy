/*
 * Copyright 2014-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-exec.
 *
 * globalpomutils-exec is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-exec is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-exec. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.script

import static com.anrisoftware.globalpom.exec.logoutputs.AbstractLogCommandOutput.*
import static com.anrisoftware.globalpom.exec.script.ScriptCommandExec.*
import static com.anrisoftware.globalpom.exec.script.ScriptCommandLine.*
import static com.anrisoftware.globalpom.threads.properties.PropertiesThreads.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import java.util.concurrent.Future

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.exec.api.CommandExec
import com.anrisoftware.globalpom.exec.api.CommandLine
import com.anrisoftware.globalpom.exec.api.ProcessTask
import com.anrisoftware.globalpom.exec.command.DefaultCommandLineModule
import com.anrisoftware.globalpom.exec.core.DefaultCommandExecFactory
import com.anrisoftware.globalpom.exec.core.DefaultProcessModule
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandInputFactory
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandOutputFactory
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeOutputsModule
import com.anrisoftware.globalpom.threads.properties.PropertiesThreads
import com.anrisoftware.globalpom.threads.properties.PropertiesThreadsModule
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.anrisoftware.resources.templates.api.Templates
import com.anrisoftware.resources.templates.api.TemplatesFactory
import com.anrisoftware.resources.templates.maps.TemplatesDefaultMapsModule
import com.anrisoftware.resources.templates.templates.TemplatesResourcesModule
import com.anrisoftware.resources.templates.worker.STDefaultPropertiesModule
import com.anrisoftware.resources.templates.worker.STWorkerModule
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ScriptCommandExec
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@Slf4j
class ScriptCommandExecTest {

    @Test
    void "template script"() {
        def threads = injector.getInstance PropertiesThreads
        threads.setProperties properties
        threads.setName "cached"
        def template = scriptTemplates.getResource("output_command")
        CommandLine line = commandLineFactory.create("output", template).add("Text")
        CommandExec exec = scriptCommandExecFactory.create(commandExecFactory)
        File scriptFile
        exec.setObserver({ o, arg ->
            scriptFile = o.commandLine.executable
            log.info "Output: ``{}''", o.getOut()
            log.info "Error: ``{}''", o.getErr()
        } as Observer)
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        assert scriptFile.exists() == false
        assert process.getExitValue() == 0
        assert process.getOut() == "Text"
        assert process.getErr() == ""
    }

    @Test
    void "template script, use factory methods"() {
        def threads = createPropertiesThreads()
        threads.setProperties properties
        threads.setName "cached"
        def template = scriptTemplates.getResource("output_command")
        CommandLine line = createScriptCommandLine("output", template).add("Text")
        CommandExec exec = createScriptCommandExec(commandExecFactory)
        exec.setObserver({ o, arg ->
            log.info "Output: ``{}''", o.getOut()
            log.info "Error: ``{}''", o.getErr()
        } as Observer)
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        assert process.getExitValue() == 0
        assert process.getOut() == "Text"
        assert process.getErr() == ""
    }

    @Test
    void "read output in parallel, as info log"() {
        def threads = createPropertiesThreads()
        threads.setProperties properties
        threads.setName "cached"
        def template = scriptTemplates.getResource("output_lines_command")
        CommandLine line = createScriptCommandLine("output", template).add("Text")
        CommandExec exec = createScriptCommandExec(commandExecFactory)
        exec.setThreads threads
        exec.setCommandOutput createInfoLogCommandOutput(log, line)
        Future task = exec.exec line
        task.get()
    }

    static Injector injector

    static ScriptCommandLineFactory commandLineFactory

    static ScriptCommandExecFactory scriptCommandExecFactory

    static DefaultCommandExecFactory commandExecFactory

    static PipeCommandOutputFactory pipeCommandOutputFactory

    static PipeCommandInputFactory pipeCommandInputFactory

    static threadsProperties = ScriptCommandExecTest.class.getResource("/threads_test.properties")

    static TemplatesFactory templatesFactory

    static Templates scriptTemplates

    static properties

    @BeforeClass
    static void createFactory() {
        toStringStyle
        injector = Guice.createInjector(
                new ScriptCommandModule(),
                new DefaultCommandLineModule(),
                new DefaultProcessModule(),
                new PipeOutputsModule(),
                new PropertiesThreadsModule(),
                new TemplatesResourcesModule(),
                new TemplatesDefaultMapsModule(),
                new STWorkerModule(),
                new STDefaultPropertiesModule())
        commandLineFactory = injector.getInstance ScriptCommandLineFactory
        commandExecFactory = injector.getInstance DefaultCommandExecFactory
        scriptCommandExecFactory = injector.getInstance ScriptCommandExecFactory
        pipeCommandOutputFactory = injector.getInstance PipeCommandOutputFactory
        pipeCommandInputFactory = injector.getInstance PipeCommandInputFactory
        properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(threadsProperties)
        templatesFactory = injector.getInstance TemplatesFactory
        scriptTemplates = templatesFactory.create("ScriptTest")
    }
}
