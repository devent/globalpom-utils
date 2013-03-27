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

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError
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
class AnnotationAccessTest extends AnnotationUtils {

	@Test
	void "read annotation value"() {
		def access = factory.create BeanAnnotation, field
		def value = access.getValue()
		assertStringContent value, "Annotation Value"
	}

	@Test
	void "read annotation title value"() {
		def name = "title"
		def access = factory.create BeanAnnotation, field
		def value = access.getValue(name)
		assertStringContent value, "Annotation Title"
	}

	@Test
	void "read undefined annotation element"() {
		def name = "not defined"
		def access = factory.create BeanAnnotation, field
		shouldFailWith(ReflectionError) {
			def value = access.getValue(name)
		}
	}

	static Injector injector

	static AnnotationAccessFactory factory

	static ParentBean bean

	static Field field

	@BeforeClass
	static void setupFactory() {
		injector = createInjector()
		factory = createFactory(injector)
		bean = new ParentBean()
		field = FieldUtils.getField Bean, "annotatedField", true
	}
}
