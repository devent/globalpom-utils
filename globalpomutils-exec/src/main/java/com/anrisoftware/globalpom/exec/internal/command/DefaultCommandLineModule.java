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

package com.anrisoftware.globalpom.exec.internal.command;

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the command line factory.
 *
 * @see CommandLineFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultCommandLineModule extends AbstractModule {

    /**
     * Returns the command line factory.
     *
     * @return the {@link CommandLineFactory}.
     */
    public static CommandLineFactory getCommandLineFactory() {
        return instance.injector.getInstance(CommandLineFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice.createInjector(new DefaultCommandLineModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandLine.class, DefaultCommandLine.class)
                .build(CommandLineFactory.class));
    }

}
