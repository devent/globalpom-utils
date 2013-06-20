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
