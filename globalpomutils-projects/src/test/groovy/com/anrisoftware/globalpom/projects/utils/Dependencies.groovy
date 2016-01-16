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
