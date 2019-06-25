/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.exec.internal.script;


import static com.google.inject.Guice.createInjector;
import static com.google.inject.util.Providers.of;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory;
import com.anrisoftware.globalpom.exec.external.command.CommandLineService;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineService;
import com.anrisoftware.resources.templates.external.TemplateResource;
import com.google.inject.AbstractModule;

/**
 * Script command line service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = ScriptCommandLineService.class)
public class ScriptCommandLineServiceImpl implements ScriptCommandLineService {

    @Inject
    private ScriptCommandLineFactory factory;

    @Reference
    private CommandLineService commandLineService;

    @Override
    public CommandLine create(String name, TemplateResource template) {
        return factory.create(name, template);
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
