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
