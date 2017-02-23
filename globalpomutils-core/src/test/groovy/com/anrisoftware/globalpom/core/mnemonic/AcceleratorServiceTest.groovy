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
package com.anrisoftware.globalpom.core.mnemonic

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.core.mnemonic.Accelerator
import com.anrisoftware.globalpom.core.mnemonic.AcceleratorService
import com.anrisoftware.globalpom.core.mnemonic.AcceleratorServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class AcceleratorServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext()

    @Test
    void "accelerator from string"() {
        Accelerator accelerator
        new tests_accelerator_string().run().each { d ->
            if (d.ex != null) {
                shouldFailWith(d.ex) {
                    accelerator = service.create(d.string)
                    accelerator.accelerator
                }
            } else {
                accelerator = service.create(d.string)
                assert accelerator.accelerator == d.code
            }
        }
    }

    AcceleratorService service

    @Before
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new AcceleratorServiceImpl(), null)
    }
}
