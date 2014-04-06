/*
 * Copyright 2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.script;

import com.anrisoftware.globalpom.exec.api.CommandExec;
import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.globalpom.exec.command.DefaultCommandLineModule;
import com.anrisoftware.globalpom.exec.core.DefaultProcessModule;
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeOutputsModule;
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
 * @since 2.0
 */
public class ScriptProcessModule extends AbstractModule {

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
                new ScriptProcessModule(), new DefaultCommandLineModule(),
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
