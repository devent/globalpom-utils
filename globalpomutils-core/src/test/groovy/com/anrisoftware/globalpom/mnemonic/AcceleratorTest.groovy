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

    @BeforeClass
    static void createFactory() {
        factory = Guice.createInjector(new MnemonicModule()).getInstance(AcceleratorFactory)
    }
}
