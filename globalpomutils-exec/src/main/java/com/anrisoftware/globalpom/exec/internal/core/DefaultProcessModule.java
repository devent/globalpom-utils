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

package com.anrisoftware.globalpom.exec.internal.core;

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

import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the default command exec factory.
 *
 * @see DefaultCommandExecFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultProcessModule extends AbstractModule {

    /**
     * Returns the command exec factory.
     *
     * @return the {@link CommandExecFactory}.
     *
     * @since 2.0
     */
    public static CommandExecFactory getCommandExecFactory() {
        return instance.injector.getInstance(CommandExecFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice.createInjector(
                new DefaultProcessModule(), new PipeOutputsModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandExec.class,
                DefaultCommandExec.class).build(CommandExecFactory.class));
        install(new FactoryModuleBuilder().implement(ProcessTask.class,
                DefaultProcessTask.class)
                .build(DefaultProcessTaskFactory.class));
    }

}
