/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.mnemonic

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static java.awt.event.KeyEvent.*
import static javax.swing.KeyStroke.*

import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice
import com.google.inject.ProvisionException
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
        data.each { d ->
            if (d.ex != null) {
                shouldFailWith(d.ex) {
                    accelerator = factory.create(d.string)
                    accelerator.accelerator
                }
            } else {
                accelerator = factory.create(d.string)
                assert accelerator.accelerator == d.code
            }
        }
    }

    static AcceleratorFactory factory

    static data = [
        [string: "a", code: getKeyStroke(VK_A, 0), ex: null],
        [string: "VK_A,ALT_DOWN_MASK,CTRL_DOWN_MASK", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
        [string: "vk_a,alt_down_mask,ctrl_down_mask", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
        [string: "a,ALT_DOWN_MASK,CTRL_DOWN_MASK", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
        [string: "a,alt_down_mask,ctrl_down_mask", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
        [string: "", code: null, valid: false, ex: null],
        [string: "SOME", code: null, valid: false, ex: IllegalArgumentException],
        [string: null, code: null, valid: false, ex: ProvisionException],
        [string: "a,SOME", code: null, valid: true, ex: IllegalArgumentException],
        [string: "alt shift X", code: getKeyStroke(VK_X, ALT_MASK | SHIFT_MASK), ex: null],
    ]

    @BeforeClass
    static void createFactory() {
        factory = Guice.createInjector(new MnemonicModule()).getInstance(AcceleratorFactory)
    }
}
