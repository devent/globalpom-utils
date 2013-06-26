/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
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
