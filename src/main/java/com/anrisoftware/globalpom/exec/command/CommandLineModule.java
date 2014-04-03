package com.anrisoftware.globalpom.exec.command;

import com.google.inject.AbstractModule;
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

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandLine.class,
                CommandLine.class).build(CommandLineFactory.class));
    }

}
