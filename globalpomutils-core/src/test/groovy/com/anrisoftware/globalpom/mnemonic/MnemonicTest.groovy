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
