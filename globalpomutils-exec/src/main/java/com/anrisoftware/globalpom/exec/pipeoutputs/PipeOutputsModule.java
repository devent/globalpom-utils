/*
 * Copyright 2014-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.pipeoutputs;

import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
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
                PipeCommandOutput.class).build(PipeCommandOutputFactory.class));
        install(new FactoryModuleBuilder().implement(CommandInput.class,
                PipeCommandInput.class).build(PipeCommandInputFactory.class));
    }

}
