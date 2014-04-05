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
package com.anrisoftware.globalpom.exec.command;

import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the command line factory.
 * 
 * @see DefaultCommandLineFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultCommandLineModule extends AbstractModule {

    /**
     * Returns the command line factory.
     * 
     * @return the {@link DefaultCommandLineFactory}.
     */
    public static DefaultCommandLineFactory getCommandLineFactory() {
        return instance.injector.getInstance(DefaultCommandLineFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice
                .createInjector(new DefaultCommandLineModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandLine.class,
                DefaultCommandLine.class).build(DefaultCommandLineFactory.class));
    }

}
