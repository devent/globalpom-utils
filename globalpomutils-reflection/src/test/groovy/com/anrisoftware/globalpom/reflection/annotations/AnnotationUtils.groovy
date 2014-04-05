/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-reflection.
 *
 * globalpomutils-reflection is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-reflection. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.annotations

import com.anrisoftware.globalpom.reflection.beans.BeansModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Create the injector to test annotation access.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationUtils extends TestUtils {

	static Injector createInjector() {
		Guice.createInjector new AnnotationsModule(), new BeansModule()
	}

	static AnnotationAccessFactory createAnnotationAccessFactory(Injector injector) {
		injector.getInstance AnnotationAccessFactory
	}

	static AnnotationSetFilterFactory createAnnotationSetFilterFactory(Injector injector) {
		injector.getInstance AnnotationSetFilterFactory
	}

	static AnnotationDiscoveryFactory createAnnotationDiscoveryFactory(Injector injector) {
		injector.getInstance AnnotationDiscoveryFactory
	}
}
