/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.projects.appproject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @see DefaultProjectFactory
 * @see ProjectSaverFactory
 * @see ProjectLoaderFactory
 * @see DefaultDataResourceFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class AppProjectModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(DefaultProject.class,
                DefaultProject.class).build(DefaultProjectFactory.class));
        install(new FactoryModuleBuilder().implement(ProjectSaver.class,
                ProjectSaver.class).build(ProjectSaverFactory.class));
        install(new FactoryModuleBuilder().implement(ProjectLoader.class,
                ProjectLoader.class).build(ProjectLoaderFactory.class));
    }
}
