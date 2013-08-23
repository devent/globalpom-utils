package com.anrisoftware.globalpom.fileresourcemanager

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ResourceSaver
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class ResourceSaverTest {

	@Test
	void "save resource"() {
		def storedir = tmp.newFolder()
		def saver = factory.create(storedir)
		saver.saveResource(resourceA, resourceB)
		assert new File(storedir, "A").isFile()
		assert new File(storedir, "B").isFile()
	}

	def resourceA

	def resourceB

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder()

	static Injector injector

	static ResourceSaverFactory factory

	@BeforeClass
	static void createSaver() {
		injector = getInjector()
		factory = injector.getInstance ResourceSaverFactory
	}

	@Before
	void createResources() {
		resourceA = new ResourceA()
		resourceB = new ResourceB()
	}

	static Injector getInjector() {
		Guice.createInjector(new FileResourceManagerModule())
	}
}
