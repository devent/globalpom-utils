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
