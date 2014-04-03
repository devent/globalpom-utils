/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.command;

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
public class CommandLineModule extends AbstractModule {

    /**
     * Returns the command line factory.
     * 
     * @return the {@link CommandLineFactory}.
     */
    public static CommandLineFactory getCommandLineFactory() {
        return instance.injector.getInstance(CommandLineFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice
                .createInjector(new CommandLineModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandLine.class,
                CommandLine.class).build(CommandLineFactory.class));
    }

}
