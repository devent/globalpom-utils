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
