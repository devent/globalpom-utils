package com.anrisoftware.globalpom.mnemonic

import static java.awt.event.KeyEvent.*

import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice

/**
 * @see Mnemonic
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class MnemonicTest {

	@Test
	void "mnemonic from string"() {
		Mnemonic mnemonic
		data.each {
			mnemonic = factory.create(it.string)
			assert mnemonic.mnemonic == it.code
			assert mnemonic.mnemonicIndex == it.index
		}
	}

	static MnemonicFactory factory;

	static data = [
		[string: "a", code: VK_A, index: -1],
		[string: "a,5", code: VK_A, index: 5],
		[string: "VK_A", code: VK_A, index: -1],
	]

	@BeforeClass
	static void createFactory() {
		factory = Guice.createInjector(new MnemonicModule()).getInstance(MnemonicFactory)
	}
}
