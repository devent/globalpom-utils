package com.anrisoftware.globalpom.exec.internal.script;

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

import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Providers.of;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
@Component(service = ScriptCommandExecService.class)
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
                bind(CommandLineFactory.class).toProvider(of(commandLineService));
            }
        }).injectMembers(this);
    }

}
