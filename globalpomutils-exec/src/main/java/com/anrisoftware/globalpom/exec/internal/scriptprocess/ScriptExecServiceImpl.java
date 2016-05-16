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
            }
        }).injectMembers(this);
    }

}
