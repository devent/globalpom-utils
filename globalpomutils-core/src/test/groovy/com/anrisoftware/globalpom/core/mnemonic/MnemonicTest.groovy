package com.anrisoftware.globalpom.core.mnemonic

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

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

    @BeforeAll
    static void createFactory() {
        factory = Guice.createInjector(new MnemonicModule()).getInstance(MnemonicFactory)
    }
}
