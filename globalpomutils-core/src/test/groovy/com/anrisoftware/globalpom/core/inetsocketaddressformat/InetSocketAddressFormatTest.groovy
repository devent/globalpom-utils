package com.anrisoftware.globalpom.core.inetsocketaddressformat

import static com.anrisoftware.globalpom.core.inetsocketaddressformat.InetSocketAddressFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

/**
 * Test parsing and formatting Internet socket address.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class InetSocketAddressFormatTest {

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
        def format = createInetSocketAddressFormat()
        inputs.eachWithIndex { it, int i ->
            def str = format.format outputs[i]
            assertStringContent "${it[0]}", str
        }
    }

    @Test
    void "parse socket address"() {
        def format = createInetSocketAddressFormat()
        inputs.eachWithIndex { it, int i ->
            def address = format.parse it[0]
            assert address == outputs[i]
        }
    }
}
