/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.script

import static com.anrisoftware.globalpom.exec.internal.logoutputs.AbstractLogCommandOutput.*
import static com.anrisoftware.globalpom.exec.internal.script.ScriptCommandExec.*
import static com.anrisoftware.globalpom.exec.internal.script.ScriptCommandLine.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.junit.jupiter.api.condition.OS.LINUX
import static org.junit.jupiter.api.condition.OS.MAC

import java.util.concurrent.Future

import jakarta.inject.Inject

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs

import com.anrisoftware.globalpom.exec.external.core.CommandExec
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory
import com.anrisoftware.globalpom.exec.external.core.CommandLine
import com.anrisoftware.globalpom.exec.external.core.ProcessTask
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputFactory
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecFactory
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule
import com.anrisoftware.globalpom.exec.internal.core.DefaultProcessModule
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.anrisoftware.resources.st.internal.worker.STDefaultPropertiesModule
import com.anrisoftware.resources.st.internal.worker.STWorkerModule
import com.anrisoftware.resources.templates.external.TemplateResource
import com.anrisoftware.resources.templates.external.Templates
import com.anrisoftware.resources.templates.external.TemplatesFactory
import com.anrisoftware.resources.templates.internal.maps.TemplatesDefaultMapsModule
import com.anrisoftware.resources.templates.internal.templates.TemplatesResourcesModule
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

/**
 * @see ScriptCommandExec
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@Slf4j
@EnabledOnOs([ LINUX, MAC ])
class ScriptCommandExecTest {

    @Test
    void "template script"() {
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = commandLineFactory.create("output", output_command).add("Text")
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
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = createScriptCommandLine("output", output_command).add("Text")
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
        def threads = propertiesThreadsFactory.create()
        threads.setProperties properties
        threads.setName "cached"
        CommandLine line = createScriptCommandLine("output", output_command).add("Text")
        CommandExec exec = createScriptCommandExec(commandExecFactory)
        exec.setThreads threads
        exec.setCommandOutput createInfoLogCommandOutput(log, line)
        Future task = exec.exec line
        task.get()
    }

    @Inject
    PropertiesThreadsFactory propertiesThreadsFactory

    @Inject
    ScriptCommandLineFactory commandLineFactory

    @Inject
    ScriptCommandExecFactory scriptCommandExecFactory

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

    static threadsProperties = ScriptCommandExecTest.class.getResource("/threads_test.properties")

    static Templates scriptTemplates

    static TemplateResource output_command

    static properties

    @BeforeAll
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
        properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(threadsProperties)
        TemplatesFactory templatesFactory = injector.getInstance TemplatesFactory
        scriptTemplates = templatesFactory.create("ScriptTest")
        output_command = scriptTemplates.getResource("output_command")
    }
}
