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
package com.anrisoftware.globalpom.exec.internal.scriptprocess

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.condition.OS.*

import org.joda.time.Duration
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory
import com.anrisoftware.globalpom.exec.internal.threads.TestThreadsPropertiesProvider
import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
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
 * @see ScriptExec
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
@Slf4j
@EnabledOnOs([ LINUX, MAC ])
class ScriptExecTest {

    @Test
    void "exec script"() {
        def scriptExec = scriptExecFactory.create(
                log: log, text: "foo", this, threads, echoScriptTemplate, "echo")()
    }

    @Test
    void "exec script timeout"() {
        assertThrows ScriptExecException, {
            def scriptExec = scriptExecFactory.create(
                    log: log, text: "foo", sleep: 5, timeout: Duration.standardSeconds(1),
                    this, threads, echoScriptTemplate, "echoTimeout")()
        }
    }

    static Injector injector

    static ScriptExecFactory scriptExecFactory

    static PropertiesThreadsFactory threadsFactory

    static TestThreadsPropertiesProvider threadsPoolProvider

    static TemplateResource echoScriptTemplate

    Threads threads

    @BeforeAll
    static void createFactory() {
        injector = Guice.createInjector(
                new ScriptProcessModule(),
                new ScriptProcessModule.ScriptProcessDefaultsModule(),
                new PropertiesThreadsModule(),
                new TemplatesResourcesModule(),
                new TemplatesDefaultMapsModule(),
                new STWorkerModule(),
                new STDefaultPropertiesModule())
        scriptExecFactory = injector.getInstance ScriptExecFactory
        threadsFactory = injector.getInstance PropertiesThreadsFactory
        threadsPoolProvider = injector.getInstance TestThreadsPropertiesProvider
        loadTemplates()
    }

    static void loadTemplates() {
        Templates templates = injector.getInstance(TemplatesFactory).create("ScriptExecTest")
        echoScriptTemplate = templates.getResource "echo_script"
    }

    @BeforeEach
    void createThreadsPool() {
        threads = threadsFactory.create()
        threads.setProperties threadsPoolProvider.get()
        threads.setName("script")
    }
}
