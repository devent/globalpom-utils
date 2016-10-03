/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
