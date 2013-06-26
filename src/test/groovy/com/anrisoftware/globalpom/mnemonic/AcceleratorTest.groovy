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
