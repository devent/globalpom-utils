/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.reflection.beans

import org.junit.Before
import org.junit.Test

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see BeanField
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class BeanFieldTest {

	@Test
	void "field name for field"() {
		def object = foo.class.getDeclaredField("fieldFoo")
		assert beanField.toFieldName(object) == "fieldFoo"
	}

	@Test
	void "field name for method"() {
		def object = foo.class.getDeclaredMethod("getFieldBar")
		assert beanField.toFieldName(object) == "fieldBar"
	}

	static class Foo {

		public Object fieldFoo

		public Object getFieldBar() {
		}
	}

	Foo foo

	BeanField beanField

	@Before
	void createBeanField() {
		beanField = injector.getInstance(BeanField)
		foo = new Foo()
	}

	static Injector injector = Guice.createInjector new BeansModule()
}
