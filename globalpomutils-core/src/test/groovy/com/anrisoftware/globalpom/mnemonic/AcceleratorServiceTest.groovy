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
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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