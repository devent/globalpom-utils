package com.anrisoftware.globalpom.exec.core;

import com.anrisoftware.globalpom.exec.api.CommandExec;
import com.anrisoftware.globalpom.exec.api.CommandExecFactory;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.google.inject.AbstractModule;
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

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandExec.class,
                DefaultCommandExec.class).build(CommandExecFactory.class));
        install(new FactoryModuleBuilder().implement(ProcessTask.class,
                DefaultProcessTask.class)
                .build(DefaultProcessTaskFactory.class));
    }

}
