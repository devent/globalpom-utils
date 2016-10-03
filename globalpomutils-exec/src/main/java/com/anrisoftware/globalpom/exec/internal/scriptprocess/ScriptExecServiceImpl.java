/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Providers.of;

import java.util.Map;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandExecService;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputService;
import com.anrisoftware.globalpom.exec.external.logoutputs.ErrorLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.ErrorLogCommandOutputService;
import com.anrisoftware.globalpom.exec.external.runcommands.RunCommandsFactory;
import com.anrisoftware.globalpom.exec.external.runcommands.RunCommandsService;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecService;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineService;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExec;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecService;
import com.anrisoftware.globalpom.threads.external.core.Threads;
import com.anrisoftware.resources.templates.external.TemplateResource;
import com.google.inject.AbstractModule;

/**
 * Script exec service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(ScriptExecService.class)
public class ScriptExecServiceImpl implements ScriptExecService {

    @Inject
    private ScriptExecFactory factory;

    @Reference
    private DebugLogCommandOutputService debugLogCommandOutputService;

    @Reference
    private ErrorLogCommandOutputService errorLogCommandOutputService;

    @Reference
    private CommandExecService commandExecService;

    @Reference
    private ScriptCommandLineService scriptCommandLineService;

    @Reference
    private ScriptCommandExecService scriptCommandExecService;

    @Reference
    private RunCommandsService runCommandsService;

    @Override
    public ScriptExec create(Map<String, Object> args, Object parent,
            Threads threads, TemplateResource templateResource, String name) {
        return factory.create(args, parent, threads, templateResource, name);
    }

    @Activate
    protected void start() {
        createInjector(new ScriptProcessModule(), new AbstractModule() {

            @Override
            protected void configure() {
                bind(DebugLogCommandOutputFactory.class)
                        .toProvider(of(debugLogCommandOutputService));
                bind(ErrorLogCommandOutputFactory.class)
                        .toProvider(of(errorLogCommandOutputService));
                bind(CommandExecFactory.class)
                        .toProvider(of(commandExecService));
                bind(ScriptCommandLineFactory.class)
                        .toProvider(of(scriptCommandLineService));
                bind(ScriptCommandExecFactory.class)
                        .toProvider(of(scriptCommandExecService));
                bind(RunCommandsFactory.class)
                        .toProvider(of(runCommandsService));
            }
        }).injectMembers(this);
    }

}
