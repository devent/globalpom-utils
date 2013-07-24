package com.anrisoftware.globalpom.charset

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.commons.codec.Charsets
import org.junit.Test

/**
 * @see SerializableCharset
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
class SerializableCharsetTest {

	@Test
	void "serialize"() {
		def charset = Charsets.UTF_8
		def scharset = SerializableCharset.decorate(charset)
		SerializableCharset scharsetB = reserialize scharset
		assert scharsetB.charset == charset
	}
}
