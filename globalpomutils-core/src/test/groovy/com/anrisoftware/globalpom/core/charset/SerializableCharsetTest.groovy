package com.anrisoftware.globalpom.core.charset

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.nio.charset.StandardCharsets

import org.junit.jupiter.api.Test

/**
 * @see SerializableCharset
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
class SerializableCharsetTest {

    @Test
    void "serialize"() {
        def charset = StandardCharsets.UTF_8
        def scharset = SerializableCharset.decorate(charset)
        SerializableCharset scharsetB = reserialize scharset
        assert scharsetB.charset == charset
    }
}
