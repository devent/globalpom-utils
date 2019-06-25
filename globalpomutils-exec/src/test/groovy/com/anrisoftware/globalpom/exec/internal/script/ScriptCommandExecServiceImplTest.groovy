/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.util.concurrent.Future

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.Rule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.exec.external.core.CommandExec
import com.anrisoftware.globalpom.exec.external.core.CommandExecService
import com.anrisoftware.globalpom.exec.external.core.CommandLine
import com.anrisoftware.globalpom.exec.external.core.ProcessTask
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecService
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineService
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineServiceImpl
import com.anrisoftware.globalpom.exec.internal.core.DefaultCommandExecServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandInputServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandOutputServiceImpl
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.anrisoftware.resources.st.internal.worker.STDefaultPropertiesServiceImpl
import com.anrisoftware.resources.st.internal.worker.STTemplateWorkerServiceImpl
import com.anrisoftware.resources.templates.external.TemplatesService
import com.anrisoftware.resources.templates.internal.maps.TemplatesBundlesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.maps.TemplatesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.templates.TemplatesServiceImpl

import groovy.util.logging.Slf4j

/**
 * @see ScriptCommandExecServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class ScriptCommandExecServiceImplTest {

    @Test
    void "template script"() {
        def threads = propertiesThreadsService.create()
        threads.setProperties properties
        threads.setName "cached"
        def template = templatesService.create('ScriptTest').getResource("output_command")
        CommandLine line = commandLineService.create("output", template).add("Text")
        CommandExec exec = scriptCommandExecService.create(commandExecService)
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

    @Rule
    public final OsgiContext context = new OsgiContext()

    PropertiesThreadsService propertiesThreadsService

    ScriptCommandLineService commandLineService

    CommandExecService commandExecService

    ScriptCommandExecService scriptCommandExecService

    TemplatesService templatesService

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        context.registerInjectActivateService(new DefaultCommandLineServiceImpl(), null)
        context.registerInjectActivateService(new PipeCommandInputServiceImpl(), null)
        context.registerInjectActivateService(new PipeCommandOutputServiceImpl(), null)
        context.registerInjectActivateService(new STDefaultPropertiesServiceImpl(), null)
        context.registerInjectActivateService(new STTemplateWorkerServiceImpl(), null)
        context.registerInjectActivateService(new TemplatesBundlesDefaultMapServiceImpl(), null)
        context.registerInjectActivateService(new TemplatesDefaultMapServiceImpl(), null)
        this.propertiesThreadsService = context.registerInjectActivateService(new PropertiesThreadsServiceImpl(), null)
        this.commandLineService = context.registerInjectActivateService(new ScriptCommandLineServiceImpl(), null)
        this.commandExecService = context.registerInjectActivateService(new DefaultCommandExecServiceImpl(), null)
        this.scriptCommandExecService = context.registerInjectActivateService(new ScriptCommandExecServiceImpl(), null)
        this.templatesService = context.registerInjectActivateService(new TemplatesServiceImpl(), null)
    }

    static properties

    static threadsProperties = ScriptCommandExecServiceImplTest.class.getResource("/threads_test.properties")

    @BeforeAll
    static void createFactory() {
        toStringStyle
        this.properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(threadsProperties)
    }
}
