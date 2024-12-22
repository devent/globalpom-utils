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
package com.anrisoftware.globalpom.projects.utils

import groovy.transform.CompileStatic

import jakarta.inject.Inject

import com.anrisoftware.globalpom.fileresourcemanager.FileResourceManagerModule
import com.anrisoftware.globalpom.projects.appproject.AppProjectModule
import com.anrisoftware.globalpom.projects.appproject.DefaultProjectFactory
import com.anrisoftware.globalpom.projects.appprojectres.AppProjectResourcesModule
import com.anrisoftware.globalpom.projects.appprojectres.DefaultProjectResourceFactory
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Test dependencies.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.16
 */
@CompileStatic
class Dependencies {

    static final Injector injector = Guice.createInjector(
    new AppProjectModule(),
    new AppProjectResourcesModule(),
    new FileResourceManagerModule(),
    )

    @Inject
    DefaultProjectFactory projectFactory

    @Inject
    DefaultProjectResourceFactory projectResourceFactory
}
