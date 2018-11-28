/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anrisoftware.globalpom.exec.internal.logoutputs;

/*-
 * #%L
 * Global POM Utilities :: Exec
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.ErrorLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.InfoLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.TraceLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.internal.logoutputs.AbstractLogCommandOutput.DebugLogCommandOutput;
import com.anrisoftware.globalpom.exec.internal.logoutputs.AbstractLogCommandOutput.ErrorLogCommandOutput;
import com.anrisoftware.globalpom.exec.internal.logoutputs.AbstractLogCommandOutput.InfoLogCommandOutput;
import com.anrisoftware.globalpom.exec.internal.logoutputs.AbstractLogCommandOutput.TraceLogCommandOutput;
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
