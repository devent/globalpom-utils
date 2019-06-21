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

package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExec;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExecFactory;
import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineModule;
import com.anrisoftware.globalpom.exec.internal.core.DefaultProcessModule;
import com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule;
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule;
import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommandsModule;
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
            install(new RunCommandsModule());
        }

    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(ScriptExec.class, ScriptExecImpl.class)
                .build(ScriptExecFactory.class));
    }

}
