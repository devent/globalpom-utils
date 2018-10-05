package com.anrisoftware.globalpom.core.mnemonic

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

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
        new tests_accelerator_string().run().each { d ->
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

    @BeforeAll
    static void createFactory() {
        factory = Guice.createInjector(new MnemonicModule()).getInstance(AcceleratorFactory)
    }
}
