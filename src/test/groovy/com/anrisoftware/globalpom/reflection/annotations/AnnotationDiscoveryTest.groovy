/*
 * Copyright 2012 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of prefdialog-reflection.
 * 
 * prefdialog-reflection is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * prefdialog-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with prefdialog-reflection. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.annotations

import java.lang.reflect.Field

import org.apache.commons.lang3.reflect.FieldUtils
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.reflection.utils.Bean
import com.anrisoftware.globalpom.reflection.utils.BeanAnnotation
import com.anrisoftware.globalpom.reflection.utils.ParentBean
import com.google.inject.Injector

/**
 * Tests for {@link AnnotationAccessImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationDiscoveryTest extends AnnotationUtils {

	@Test
	void "find annotations"() {
		int foundCounter = 0
		def filter = filterFactory.create([BeanAnnotation])
		def a = factory.create bean.bean, filter
		a.addListener([memberFound: { foundCounter++ }]as AnnotationListener)
		def result = a.call()
		assert result.size() == 3
		assert foundCounter == 3
	}

	static Injector injector

	static AnnotationDiscoveryFactory factory

	static AnnotationSetFilterFactory filterFactory

	static ParentBean bean

	static Field field

	@BeforeClass
	static void setupFactory() {
		injector = createInjector()
		factory = createAnnotationDiscoveryFactory(injector)
		filterFactory = createAnnotationSetFilterFactory(injector)
		bean = new ParentBean()
		field = FieldUtils.getField Bean, "annotatedField", true
	}
}
