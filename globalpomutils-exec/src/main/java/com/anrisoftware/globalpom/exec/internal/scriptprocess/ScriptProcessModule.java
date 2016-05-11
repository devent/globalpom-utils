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

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory;
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule;
import com.anrisoftware.globalpom.exec.internal.core.DefaultProcessModule;
import com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule;
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule;
import com.anrisoftware.globalpom.exec.internal.script.ScriptCommandModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the script exec factory.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class ScriptProcessModule extends AbstractModule {

    /**
     * Installs needed command exec modules.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 2.3
     */
    public static class ScriptProcessDefaultsModule extends AbstractModule {

        @Override
        protected void configure() {
            install(new DefaultCommandLineModule());
            install(new DefaultProcessModule());
            install(new LogOutputsModule());
            install(new PipeOutputsModule());
            install(new ScriptCommandModule());
        }

    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(ScriptExecImpl.class,
                ScriptExecImpl.class).build(ScriptExecFactory.class));
    }

}
