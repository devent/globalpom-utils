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
package com.anrisoftware.globalpom.exec.internal.pipeoutputs;

import com.anrisoftware.globalpom.exec.external.core.CommandInput;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the pipe factories.
 * 
 * @see PipeCommandOutputFactory
 * @see PipeCommandInputFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class PipeOutputsModule extends AbstractModule {

    /**
     * Returns the pipe output factory.
     * 
     * @return the {@link PipeCommandOutputFactory}.
     */
    public static PipeCommandOutputFactory getPipeCommandOutputFactory() {
        return instance.injector.getInstance(PipeCommandOutputFactory.class);
    }

    /**
     * Returns the pipe input factory.
     * 
     * @return the {@link PipeCommandInputFactory}.
     */
    public static PipeCommandInputFactory getPipeCommandInputFactory() {
        return instance.injector.getInstance(PipeCommandInputFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice
                .createInjector(new PipeOutputsModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandOutput.class,
                PipeCommandOutputImpl.class).build(PipeCommandOutputFactory.class));
        install(new FactoryModuleBuilder().implement(CommandInput.class,
                PipeCommandInputImpl.class).build(PipeCommandInputFactory.class));
    }

}
