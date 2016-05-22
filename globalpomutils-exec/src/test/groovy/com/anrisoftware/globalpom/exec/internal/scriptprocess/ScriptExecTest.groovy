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

import groovy.util.logging.Slf4j

import org.joda.time.Duration
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory
import com.anrisoftware.globalpom.threads.external.core.Threads
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory
import com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule
import com.anrisoftware.resources.templates.external.TemplateResource
import com.anrisoftware.resources.templates.external.Templates
import com.anrisoftware.resources.templates.external.TemplatesFactory
import com.anrisoftware.resources.templates.internal.maps.TemplatesDefaultMapsModule
import com.anrisoftware.resources.templates.internal.templates.TemplatesResourcesModule
import com.anrisoftware.resources.templates.internal.worker.STDefaultPropertiesModule
import com.anrisoftware.resources.templates.internal.worker.STWorkerModule
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ScriptExec
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
@Slf4j
class ScriptExecTest {

    @Test
    void "exec script"() {
        def scriptExec = scriptExecFactory.create(
                log: log, text: "foo", this, threads, echoScriptTemplate, "echo")()
    }

    @Test(expected = ScriptExecException)
    void "exec script timeout"() {
        def scriptExec = scriptExecFactory.create(
                log: log, text: "foo", sleep: 5, timeout: Duration.standardSeconds(1),
                this, threads, echoScriptTemplate, "echoTimeout")()
    }

    static Injector injector

    static ScriptExecFactory scriptExecFactory

    static PropertiesThreadsFactory threadsFactory

    static TestThreadsPropertiesProvider threadsPoolProvider

    static TemplateResource echoScriptTemplate

    Threads threads

    @BeforeClass
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

    @Before
    void createThreadsPool() {
        threads = threadsFactory.create();
        threads.setProperties threadsPoolProvider.get()
        threads.setName("script");
    }
}
