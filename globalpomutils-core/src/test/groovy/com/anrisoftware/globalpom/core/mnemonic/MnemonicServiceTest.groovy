/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.core.mnemonic

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.utils.TestUtils

import groovy.util.logging.Slf4j

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class MnemonicServiceTest {

    final OsgiContext context = new OsgiContext()

    @Test
    void "mnemonic from string"() {
        Mnemonic mnemonic
        new tests_mnemonic().run().each { d ->
            if (d.ex != null) {
                shouldFailWith(d.ex) {
                    mnemonic = service.create(d.string)
                    assert mnemonic.isValid() == d.valid
                    mnemonic.mnemonic
                }
            } else {
                mnemonic = service.create(d.string)
                assert mnemonic.isValid() == d.valid
                assert mnemonic.mnemonic == d.code
                assert mnemonic.mnemonicIndex == d.index
            }
        }
    }

    MnemonicService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new MnemonicServiceImpl(), null)
    }
}
