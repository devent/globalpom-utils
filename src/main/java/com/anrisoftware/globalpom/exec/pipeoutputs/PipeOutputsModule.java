package com.anrisoftware.globalpom.exec.pipeoutputs;

import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the pipe factories.
 * 
 * @see PipeCommandOutputFactory
 * @see PipeCommandInputFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class PipeOutputsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandOutput.class,
                PipeCommandOutput.class).build(PipeCommandOutputFactory.class));
        install(new FactoryModuleBuilder().implement(CommandInput.class,
                PipeCommandInput.class).build(PipeCommandInputFactory.class));
    }

}
