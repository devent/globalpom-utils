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
package com.anrisoftware.globalpom.inetsocketaddressformat

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils

/**
 * @see InetSocketAddressFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class InetSocketAddressFormatServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext()

    static inputs = [
        ["127.0.0.1", "127.0.0.1", 0],
        [
            "192.168.0.1:6666",
            "192.168.0.1",
            6666
        ],
        ["localhost", "localhost", 0],
        [
            "localhost:6666",
            "localhost",
            6666
        ],
        [
            "example.com",
            "example.com",
            0
        ],
        [
            "example.com:6666",
            "example.com",
            6666
        ],
    ]

    static outputs = [
        new InetSocketAddress(inputs[0][1], inputs[0][2]),
        new InetSocketAddress(inputs[1][1], inputs[1][2]),
        new InetSocketAddress(inputs[2][1], inputs[2][2]),
        new InetSocketAddress(inputs[3][1], inputs[3][2]),
        new InetSocketAddress(inputs[4][1], inputs[4][2]),
        new InetSocketAddress(inputs[5][1], inputs[5][2]),
    ]

    @Test
    void "format socket address"() {
        def format = service.create()
        inputs.eachWithIndex { it, int i ->
            def str = format.format outputs[i]
            assertStringContent "${it[0]}", str
        }
    }

    @Test
    void "parse socket address"() {
        def format = service.create()
        inputs.eachWithIndex { it, int i ->
            def address = format.parse it[0]
            assert address == outputs[i]
        }
    }

    InetSocketAddressFormatService service

    @Before
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new InetSocketAddressFormatServiceImpl(), null)
    }
}