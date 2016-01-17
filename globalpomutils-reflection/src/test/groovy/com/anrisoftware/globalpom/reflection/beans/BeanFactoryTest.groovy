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
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.reflection.utils.ParentBean
import com.google.inject.Injector

/**
 * Tests for {@link BeanFactoryImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanFactoryTest extends BeanUtils {

	@Test
	void "create bean"() {
		def value = factory.create(ParentBean)
		assert value != null
	}

	@Test
	void "create bean from type name"() {
		def value = factory.create("${ParentBean.class.name}")
		assert value != null
	}

	static Injector injector

	static BeanFactory factory

	ParentBean bean

	@Before
	void setupBean() {
		bean = new ParentBean()
	}

	@BeforeClass
	static void setupFactory() {
		injector = createInjector()
		factory = injector.getInstance BeanFactory
	}
}
