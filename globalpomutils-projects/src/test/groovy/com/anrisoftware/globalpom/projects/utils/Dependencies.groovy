/*
 * Copyright 2015-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.projects.utils

import groovy.transform.CompileStatic

import javax.inject.Inject

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
