/*-
 * #%L
 * Global POM Utilities :: Exec
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.anrisoftware.globalpom.exec.internal.scriptprocess

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecService
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineServiceImpl
import com.anrisoftware.globalpom.exec.internal.core.DefaultCommandExecServiceImpl
import com.anrisoftware.globalpom.exec.internal.logoutputs.DebugLogCommandOutputServiceImpl
import com.anrisoftware.globalpom.exec.internal.logoutputs.ErrorLogCommandOutputServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandInputServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandOutputServiceImpl
import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommandsServiceImpl
import com.anrisoftware.globalpom.exec.internal.script.ScriptCommandExecServiceImpl
import com.anrisoftware.globalpom.exec.internal.script.ScriptCommandLineServiceImpl
import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsServiceImpl
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.anrisoftware.resources.st.internal.worker.STDefaultPropertiesServiceImpl
import com.anrisoftware.resources.st.internal.worker.STTemplateWorkerServiceImpl
import com.anrisoftware.resources.templates.external.TemplateResource
import com.anrisoftware.resources.templates.external.Templates
import com.anrisoftware.resources.templates.external.TemplatesService
import com.anrisoftware.resources.templates.internal.maps.TemplatesBundlesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.maps.TemplatesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.templates.TemplatesServiceImpl

import groovy.util.logging.Slf4j

/**
 * @see ScriptExecServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class ScriptExecServiceImplTest {

    @Test
    void "exec script"() {
        def scriptExec = scriptExecService.create(
                log: log, text: "foo", this, threads, echoScriptTemplate, "echo")()
    }

    final OsgiContext context = new OsgiContext()

    ScriptExecService scriptExecService

    TemplatesService templatesService

    TemplateResource echoScriptTemplate

    PropertiesThreadsService threadsService

    Threads threads

    @BeforeEach
    void setup() {
        context.registerInjectActivateService(new DefaultCommandLineServiceImpl(), null)
        context.registerInjectActivateService(new PipeCommandInputServiceImpl(), null)
        context.registerInjectActivateService(new PipeCommandOutputServiceImpl(), null)
        context.registerInjectActivateService(new ErrorLogCommandOutputServiceImpl(), null)
        context.registerInjectActivateService(new DebugLogCommandOutputServiceImpl(), null)
        context.registerInjectActivateService(new STDefaultPropertiesServiceImpl(), null)
        context.registerInjectActivateService(new STTemplateWorkerServiceImpl(), null)
        context.registerInjectActivateService(new TemplatesBundlesDefaultMapServiceImpl(), null)
        context.registerInjectActivateService(new TemplatesDefaultMapServiceImpl(), null)
        context.registerInjectActivateService(new ScriptCommandLineServiceImpl(), null)
        context.registerInjectActivateService(new DefaultCommandExecServiceImpl(), null)
        context.registerInjectActivateService(new ScriptCommandExecServiceImpl(), null)
        context.registerInjectActivateService(new RunCommandsServiceImpl(), null)
        this.scriptExecService = context.registerInjectActivateService(new ScriptExecServiceImpl(), null)
        this.templatesService = context.registerInjectActivateService(new TemplatesServiceImpl(), null)
        this.threadsService = context.registerInjectActivateService(new PropertiesThreadsServiceImpl(), null)
        this.echoScriptTemplate = loadTemplates()
        createThreadsPool()
    }

    static properties

    static threadsProperties = ScriptExecServiceImplTest.class.getResource("test_threads.properties")

    @BeforeAll
    static void createFactory() {
        toStringStyle
        this.properties = new ContextPropertiesFactory('com.anrisoftware.globalpom.threads.properties.internal').fromResource(threadsProperties)
    }

    TemplateResource loadTemplates() {
        Templates templates = templatesService.create("ScriptExecTest")
        return templates.getResource("echo_script")
    }

    void createThreadsPool() {
        threads = threadsService.create()
        threads.setProperties properties
        threads.setName("script")
    }
}
