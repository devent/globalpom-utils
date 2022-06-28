/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory;
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule;
import com.anrisoftware.globalpom.exec.internal.core.DefaultProcessModule;
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the script command factory.
 *
 * @see ScriptCommandExecFactory
 * @see ScriptCommandLineFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class ScriptCommandModule extends AbstractModule {

    /**
     * Returns the script command factory.
     *
     * @return the {@link ScriptCommandExecFactory}.
     */
    public static ScriptCommandExecFactory getScriptCommandExecFactory() {
        return instance.injector.getInstance(ScriptCommandExecFactory.class);
    }

    /**
     * Returns the script command line factory.
     *
     * @return the {@link ScriptCommandLineFactory}.
     */
    public static ScriptCommandLineFactory getScriptCommandLineFactory() {
        return instance.injector.getInstance(ScriptCommandLineFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice.createInjector(
                new ScriptCommandModule(), new DefaultCommandLineModule(),
                new DefaultProcessModule(), new PipeOutputsModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandExec.class,
                ScriptCommandExec.class).build(ScriptCommandExecFactory.class));
        install(new FactoryModuleBuilder().implement(CommandLine.class,
                ScriptCommandLine.class).build(ScriptCommandLineFactory.class));
    }

}
