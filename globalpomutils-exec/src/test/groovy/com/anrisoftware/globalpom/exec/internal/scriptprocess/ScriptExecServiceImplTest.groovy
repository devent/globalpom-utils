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
package com.anrisoftware.globalpom.exec.internal.scriptprocess

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecService
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineServiceImpl
import com.anrisoftware.globalpom.exec.internal.core.DefaultCommandExecServiceImpl
import com.anrisoftware.globalpom.exec.internal.logoutputs.DebugLogCommandOutputServiceImpl
import com.anrisoftware.globalpom.exec.internal.logoutputs.ErrorLogCommandOutputServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandInputServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandOutputServiceImpl
import com.anrisoftware.globalpom.exec.internal.script.ScriptCommandExecServiceImpl
import com.anrisoftware.globalpom.exec.internal.script.ScriptCommandLineServiceImpl
import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsServiceImpl
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.anrisoftware.resources.templates.external.TemplateResource
import com.anrisoftware.resources.templates.external.Templates
import com.anrisoftware.resources.templates.external.TemplatesService
import com.anrisoftware.resources.templates.internal.maps.TemplatesBundlesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.maps.TemplatesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.templates.TemplatesServiceImpl
import com.anrisoftware.resources.templates.internal.worker.STDefaultPropertiesServiceImpl
import com.anrisoftware.resources.templates.internal.worker.STTemplateWorkerServiceImpl

/**
 * @see ScriptExecServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class ScriptExecServiceImplTest {

    @Test
    void "exec script"() {
        def scriptExec = scriptExecService.create(
                log: log, text: "foo", this, threads, echoScriptTemplate, "echo")()
    }

    @Rule
    public final OsgiContext context = new OsgiContext()

    ScriptExecService scriptExecService

    TemplatesService templatesService

    TemplateResource echoScriptTemplate

    PropertiesThreadsService threadsService

    Threads threads

    @Before
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
        this.scriptExecService = context.registerInjectActivateService(new ScriptExecServiceImpl(), null)
        this.templatesService = context.registerInjectActivateService(new TemplatesServiceImpl(), null)
        this.threadsService = context.registerInjectActivateService(new PropertiesThreadsServiceImpl(), null)
        this.echoScriptTemplate = loadTemplates()
    }

    static properties

    static threadsProperties = ScriptExecServiceImplTest.class.getResource("test_threads.properties")

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.properties = new ContextPropertiesFactory('com.anrisoftware.globalpom.threads.properties.internal').fromResource(threadsProperties)
    }

    TemplateResource loadTemplates() {
        Templates templates = templatesService.create("ScriptExecTest")
        return templates.getResource("echo_script")
    }

    @Before
    void createThreadsPool() {
        threads = threadsService.create()
        threads.setProperties properties
        threads.setName("script")
    }
}
