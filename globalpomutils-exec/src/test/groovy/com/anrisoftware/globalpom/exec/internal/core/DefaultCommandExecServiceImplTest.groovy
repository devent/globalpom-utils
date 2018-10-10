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
package com.anrisoftware.globalpom.exec.internal.core

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.util.concurrent.Future

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.exec.external.command.CommandLineService
import com.anrisoftware.globalpom.exec.external.core.CommandExec
import com.anrisoftware.globalpom.exec.external.core.CommandExecService
import com.anrisoftware.globalpom.exec.external.core.ProcessTask
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLine
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandInputServiceImpl
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeCommandOutputServiceImpl
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory

import groovy.util.logging.Slf4j

/**
 * @see DefaultCommandExecServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class DefaultCommandExecServiceImplTest {

    @Test
    void "read output after task is done"() {
        def threads = propertiesThreadsService.create()
        threads.setProperties properties
        threads.setName "cached"
        DefaultCommandLine line = commandLineService.create("echo").add("-n").add("Text")
        CommandExec exec = commandExecService.create()
        exec.setThreads threads
        Future task = exec.exec line
        ProcessTask process = task.get()
        log.info "Output: ``{}''", process.getOut()
        log.info "Error: ``{}''", process.getErr()
        assert process.getExitValue() == 0
        assert process.getOut() == "Text"
        assert process.getErr() == ""
    }

    final OsgiContext context = new OsgiContext()

    PropertiesThreadsService propertiesThreadsService

    CommandLineService commandLineService

    CommandExecService commandExecService

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        context.registerInjectActivateService(new PipeCommandInputServiceImpl(), null)
        context.registerInjectActivateService(new PipeCommandOutputServiceImpl(), null)
        this.propertiesThreadsService = context.registerInjectActivateService(new PropertiesThreadsServiceImpl(), null)
        this.commandLineService = context.registerInjectActivateService(new DefaultCommandLineServiceImpl(), null)
        this.commandExecService = context.registerInjectActivateService(new DefaultCommandExecServiceImpl(), null)
    }

    static properties

    static threadsProperties = DefaultCommandExecTest.class.getResource("/threads_test.properties")

    @BeforeAll
    static void createFactory() {
        toStringStyle
        this.properties = new ContextPropertiesFactory('com.anrisoftware.globalpom.threads.properties.internal').fromResource(threadsProperties)
    }
}
