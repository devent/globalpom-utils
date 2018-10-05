package com.anrisoftware.globalpom.core.strings

import org.junit.jupiter.api.Test

/**
 * Test convert to underscore format.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
class ToUnderscoreTest {

    @Test
    void "convert to underscore"() {
        inputs.eachWithIndex { it, i ->
            def output = toUnderscore.convert(it)
            assert expected[i] == output
        }
    }

    static final inputs = ["CamelCase"]

    static final expected = ["camel_case"]

    static final ToUnderscore toUnderscore = new ToUnderscore()
}
