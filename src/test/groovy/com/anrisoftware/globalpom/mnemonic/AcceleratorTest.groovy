package com.anrisoftware.globalpom.mnemonic

import static java.awt.event.KeyEvent.*
import static javax.swing.KeyStroke.*

import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice

/**
 * @see Accelerator
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class AcceleratorTest {

	@Test
	void "accelerator from string"() {
		Accelerator accelerator
		data.each {
			accelerator = factory.create(it.string)
			assert accelerator.accelerator == it.code
		}
	}

	static AcceleratorFactory factory;

	static data = [
		[string: "a", code: getKeyStroke(VK_A, 0)],
		[string: "VK_A,ALT_DOWN_MASK,CTRL_DOWN_MASK", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK)],
		[string: "a,ALT_DOWN_MASK,CTRL_DOWN_MASK", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK)],
	]

	@BeforeClass
	static void createFactory() {
		factory = Guice.createInjector(new MnemonicModule()).getInstance(AcceleratorFactory)
	}
}
