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
package com.anrisoftware.globalpom.exec.internal.core;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Providers.of;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandExecService;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputService;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputService;
import com.google.inject.AbstractModule;

/**
 * Command executer service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(CommandExecService.class)
public class DefaultCommandExecServiceImpl implements CommandExecService {

    @Inject
    private CommandExecFactory factory;

    @Reference
    private PipeCommandOutputService commandOutputService;

    @Reference
    private PipeCommandInputService commandInputService;

    @Override
    public CommandExec create() {
        return factory.create();
    }

    @Activate
    protected void start() {
        createInjector(new DefaultProcessModule(), new AbstractModule() {

            @Override
            protected void configure() {
                bind(PipeCommandOutputFactory.class)
                        .toProvider(of(commandOutputService));
                bind(PipeCommandInputFactory.class)
                        .toProvider(of(commandInputService));
            }
        }).injectMembers(this);
    }

}
