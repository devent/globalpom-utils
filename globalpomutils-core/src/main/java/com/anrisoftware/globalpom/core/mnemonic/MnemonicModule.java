/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.mnemonic;

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
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

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the mnemonic factory.
 *
 * @see Mnemonic
 * @see MnemonicFactory
 * @see Accelerator
 * @see AcceleratorFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class MnemonicModule extends AbstractModule {

    public static Injector getInjector() {
        return InjectorInstance.injector;
    }

    public static Injector getMnemonicInjector() {
        return InjectorInstance.injector;
    }

    private static class InjectorInstance {

        static final Injector injector = createInjector(new MnemonicModule());
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(Mnemonic.class,
                Mnemonic.class).build(MnemonicFactory.class));
        install(new FactoryModuleBuilder().implement(Accelerator.class,
                Accelerator.class).build(AcceleratorFactory.class));
    }

}
