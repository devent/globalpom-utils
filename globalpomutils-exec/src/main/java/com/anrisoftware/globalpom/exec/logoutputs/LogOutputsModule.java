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
package com.anrisoftware.globalpom.exec.logoutputs;

import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.anrisoftware.globalpom.exec.logoutputs.AbstractLogCommandOutput.DebugLogCommandOutput;
import com.anrisoftware.globalpom.exec.logoutputs.AbstractLogCommandOutput.ErrorLogCommandOutput;
import com.anrisoftware.globalpom.exec.logoutputs.AbstractLogCommandOutput.InfoLogCommandOutput;
import com.anrisoftware.globalpom.exec.logoutputs.AbstractLogCommandOutput.TraceLogCommandOutput;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the pipe factories.
 * 
 * @see InfoLogCommandOutputFactory
 * @see DebugLogCommandOutputFactory
 * @see TraceLogCommandOutputFactory
 * @see ErrorLogCommandOutputFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public class LogOutputsModule extends AbstractModule {

    /**
     * Returns the info level logger output factory.
     * 
     * @return the {@link InfoLogCommandOutputFactory}.
     */
    public static InfoLogCommandOutputFactory getInfoLogCommandOutputFactory() {
        return instance.injector.getInstance(InfoLogCommandOutputFactory.class);
    }

    /**
     * Returns the debug level logger output factory.
     * 
     * @return the {@link DebugLogCommandOutputFactory}.
     */
    public static DebugLogCommandOutputFactory getDebugLogCommandOutputFactory() {
        return instance.injector
                .getInstance(DebugLogCommandOutputFactory.class);
    }

    /**
     * Returns the debug level logger output factory.
     * 
     * @return the {@link ErrorLogCommandOutputFactory}.
     */
    public static ErrorLogCommandOutputFactory getErrorLogCommandOutputFactory() {
        return instance.injector
                .getInstance(ErrorLogCommandOutputFactory.class);
    }

    /**
     * Returns the trace level logger output factory.
     * 
     * @return the {@link TraceLogCommandOutputFactory}.
     */
    public static TraceLogCommandOutputFactory getTraceLogCommandOutputFactory() {
        return instance.injector
                .getInstance(TraceLogCommandOutputFactory.class);
    }

    private static class instance {
        static final Injector injector = Guice
                .createInjector(new LogOutputsModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CommandOutput.class,
                InfoLogCommandOutput.class).build(
                InfoLogCommandOutputFactory.class));
        install(new FactoryModuleBuilder().implement(CommandOutput.class,
                DebugLogCommandOutput.class).build(
                DebugLogCommandOutputFactory.class));
        install(new FactoryModuleBuilder().implement(CommandOutput.class,
                TraceLogCommandOutput.class).build(
                TraceLogCommandOutputFactory.class));
        install(new FactoryModuleBuilder().implement(CommandOutput.class,
                ErrorLogCommandOutput.class).build(
                ErrorLogCommandOutputFactory.class));
    }

}
