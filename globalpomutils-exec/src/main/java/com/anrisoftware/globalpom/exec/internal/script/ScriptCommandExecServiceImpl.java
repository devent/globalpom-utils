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
package com.anrisoftware.globalpom.exec.internal.script;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Providers.of;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory;
import com.anrisoftware.globalpom.exec.external.command.CommandLineService;
import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecService;
import com.google.inject.AbstractModule;

/**
 * Script exec service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(ScriptCommandExecService.class)
public class ScriptCommandExecServiceImpl implements ScriptCommandExecService {

    @Inject
    private ScriptCommandExecFactory factory;

    @Reference
    private CommandLineService commandLineService;

    @Override
    public CommandExec create(CommandExecFactory execFactory) {
        return factory.create(execFactory);
    }

    @Activate
    protected void start() {
        createInjector(new ScriptCommandModule(), new AbstractModule() {

            @Override
            protected void configure() {
                bind(CommandLineFactory.class).toProvider(
                        of(commandLineService));
            }
        }).injectMembers(this);
    }

}
