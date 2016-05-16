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
package com.anrisoftware.globalpom.exec.internal.script

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import java.util.concurrent.Future

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

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
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.anrisoftware.resources.templates.external.TemplatesService
import com.anrisoftware.resources.templates.internal.maps.TemplatesBundlesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.maps.TemplatesDefaultMapServiceImpl
import com.anrisoftware.resources.templates.internal.templates.TemplatesServiceImpl
import com.anrisoftware.resources.templates.internal.worker.STDefaultPropertiesServiceImpl
import com.anrisoftware.resources.templates.internal.worker.STTemplateWorkerServiceImpl

/**
 * @see ScriptCommandExecServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
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

    @Before
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

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.properties = new ContextPropertiesFactory('com.anrisoftware.globalpom.threads.properties.internal').fromResource(threadsProperties)
    }
}
