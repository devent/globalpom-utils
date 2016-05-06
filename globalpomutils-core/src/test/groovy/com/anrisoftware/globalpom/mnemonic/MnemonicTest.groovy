/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
        new tests_mnemonic().run().each { d ->
            if (d.ex != null) {
                shouldFailWith(d.ex) {
                    mnemonic = factory.create(d.string)
                    assert mnemonic.isValid() == d.valid
                    mnemonic.mnemonic
                }
            } else {
                mnemonic = factory.create(d.string)
                assert mnemonic.isValid() == d.valid
                assert mnemonic.mnemonic == d.code
                assert mnemonic.mnemonicIndex == d.index
            }
        }
    }

    static MnemonicFactory factory

    @BeforeClass
    static void createFactory() {
        factory = Guice.createInjector(new MnemonicModule()).getInstance(MnemonicFactory)
    }
}
