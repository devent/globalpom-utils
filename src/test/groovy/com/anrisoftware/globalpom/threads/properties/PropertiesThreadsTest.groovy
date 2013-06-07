package com.anrisoftware.globalpom.threads.properties;

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.propertiesutils.ContextPropertiesFactory
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see PropertiesThreads
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class PropertiesThreadsTest {

	@Test
	void "cached pool"() {
		threads.setProperties properties
		threads.setName "cached"
		def future = threads.submit task
	}

	@Test
	void "fixed pool"() {
		threads.setProperties properties
		threads.setName "fixed"
		def future = threads.submit task
	}

	@Test
	void "single pool"() {
		threads.setProperties properties
		threads.setName "single"
		def future = threads.submit task
	}

	PropertiesThreads threads

	def task

	boolean taskCalled

	@Before
	void createThreads() {
		threads = injector.getInstance PropertiesThreads
		task = { Thread.sleep 5000 }
	}

	static Injector injector

	static URL propertiesResource

	static Properties properties

	@BeforeClass
	static void setupThreads() {
		injector = Guice.createInjector(new PropertiesThreadsModule())
		propertiesResource = PropertiesThreadsTest.class.getResource("/threads_test.properties")
		properties = new ContextPropertiesFactory(PropertiesThreads).fromResource(propertiesResource)
	}

	static {
		TestUtils.toStringStyle
	}
}