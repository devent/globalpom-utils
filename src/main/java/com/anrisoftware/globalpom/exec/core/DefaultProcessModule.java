package com.anrisoftware.globalpom.exec.core;

import com.anrisoftware.globalpom.exec.api.CommandExec;
import com.anrisoftware.globalpom.exec.api.CommandExecFactory;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeOutputsModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the default command exec factory.
 * 
 * @see CommandExecFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultProcessModule extends AbstractModule {

    /**
     * Returns the command exec factory.
     * 
     * @return the {@link CommandExecFactory}.
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
